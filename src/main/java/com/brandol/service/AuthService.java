package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.config.security.JwtProvider;
import com.brandol.config.security.TokenDto;
import com.brandol.converter.AgreementConverter;
import com.brandol.converter.MemberConverter;
import com.brandol.domain.Items;
import com.brandol.domain.Member;
import com.brandol.domain.Term;
import com.brandol.domain.enums.Gender;
import com.brandol.domain.enums.TermType;
import com.brandol.domain.enums.UserStatus;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.request.AuthRequestDto;
import com.brandol.dto.response.AuthResponseDto;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final TermRepository termRepository;
    private final AgreementRepository agreementRepository;
    private final ItemsRepository itemsRepository;
    private final MyItemRepository myItemRepository;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Value("${defaultItemIds.male}")
    private List<Long> maleDefaultItemIds;

    @Value("${defaultItemIds.female}")
    private List<Long> femaleDefaultItemIds;

    @Transactional
    public TokenDto login(AuthRequestDto.KakaoLoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElse(null);

        // 로그인
        if (member != null && member.isSignUp()) { // 해당 이메일로 가입O, 회원가입도 완료된 회원이 있으면 로그인 진행

            if (member.getUserStatus() == UserStatus.INACTIVE) {
                throw new ErrorHandler(ErrorStatus._INACTIVE_MEMBER);
            }

            // 토큰 생성 - ATK
            TokenDto tokenDto = jwtProvider.createToken(member.getEmail(),member.getId(), member.getRole().toString());
            System.out.println("토큰 생성: ");
            System.out.println(tokenDto.getAccessToken());
            System.out.println(tokenDto.getRefreshToken());

            // Login 하면 Redis에 RT:{email} : {refresh token 값}  // key - value 형태로 refresh 토큰 저장
            // opsForValue() : set을 통해 key,value값 저장하고 get(key)통해 value가져올 수 있음.
            // refreshToken.getTokenExpriresTime().getTime() : 리프레시 토큰의 만료시간이 지나면 해당 값 자동 삭제
            return tokenDto;

        } else if (member != null && !member.isSignUp()) { // 이메일로 가입된 회원이 있지만 회원가입이 완료되지 않은 경우
            return null;
        }
        else { // 아예 회원가입이 안되어 있는 경우
            Member newmember = MemberConverter.toMemberWithEmail(request);
            memberRepository.save(newmember);// 이메일로 회원정보 저장
            return null;
        }
    }

    // 회원가입
    @Transactional
    public AuthResponseDto.SignUpDto signUp(AuthRequestDto.SignUpDto request) {
        AuthResponseDto.AgreeTermsDto agreeTermsResponse = agreeTerms(request);
        Member member = setProfile(request);
        wearDefaultAvatarItems(member);// 성별에 따라 아바타 기본 아이템 장착
        memberService.addMemberBrandList(member.getId(), 1L);
        memberService.addMemberBrandList(member.getId(), 2L);
        return MemberConverter.signUpResDto(member.getId());
    }

    // 아바타 기본 아이템을 MyItem에 isWearing = true로 저장
    @Transactional
    public void wearDefaultAvatarItems(Member member) {
        List<Long> defaultItemIds;
        if (member.getGender() == Gender.MALE) {
            defaultItemIds = maleDefaultItemIds;
        } else {
            defaultItemIds = femaleDefaultItemIds;
        }
        List<Items> defaultItems = itemsRepository.findAllById(defaultItemIds);
        for (Items item : defaultItems) {
            MyItem myItem = MyItem.builder()
                    .member(member)
                    .items(item)
                    .isWearing(true)
                    .build();
            myItemRepository.save(myItem);
        }
    }

    // 이용약관 동의
    @Transactional
    public AuthResponseDto.AgreeTermsDto agreeTerms(AuthRequestDto.SignUpDto request) { // email, termsIdList
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        // 필수 약관 동의 체크
        List<Term> mandatoryTerms = termRepository.findByTermType(TermType.MANDATORY);
        for (Term mandatoryTerm : mandatoryTerms) {
            if (!request.getTermsIdList().contains(mandatoryTerm.getId())) {
                throw new ErrorHandler(ErrorStatus._MANDATORY_AGREEMENT_NOT_FOUND);
            }
        }
        // 회원이 동의한 이용약관 저장
        List<Term> termList = request.getTermsIdList().stream()
                .map(termId -> { return termRepository.findById(termId).orElseThrow(() -> new ErrorHandler(ErrorStatus._TERM_NOT_FOUND));// 존재하지 않는 이용약관 id일 때 예외처리
                }).collect(Collectors.toList());

        termList.stream()
                .map(term -> AgreementConverter.toAgreement(member, term))
                .forEach(agreementRepository::save);
        return AgreementConverter.toAgreementResDto(member);
    }

    // 프로필 설정
    @Transactional
    public Member setProfile(AuthRequestDto.SignUpDto request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        if (request.getGender() == Gender.MALE) {
            member.setProfile(request.getNickname(), request.getGender(), request.getAge(), "https://brandol.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B3%B8+%EC%95%84%EB%B0%94%ED%83%80+%EB%82%A8%EC%9E%90.png");
        }
        else {
            member.setProfile(request.getNickname(), request.getGender(), request.getAge(), "https://brandol.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B3%B8+%EC%95%84%EB%B0%94%ED%83%80+%EC%97%AC%EC%9E%90.png");
        }
        return member;
    }

    // 회원 탈퇴
    @Transactional
    public String inactivateMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        System.out.println("탈퇴할 회원id: "+member.getId() + member.getEmail());
        member.inactivate(UserStatus.INACTIVE);
        return "탈퇴 성공";
    }

    // 닉네임 변경
    @Transactional
    public AuthResponseDto.UpdateNickname updateNickname(Long memberId, String nickname) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        member.updateNickname(nickname);
        return MemberConverter.toUpdateNicknameDto(member.getId(), member.getNickname());
    }

    // 회원정보(아바타, 닉네임, 포인트) 조회
    public AuthResponseDto.MemberInfo getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        return MemberConverter.toMemberInfoDto(member);
    }
}