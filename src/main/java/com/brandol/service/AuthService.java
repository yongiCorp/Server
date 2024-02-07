package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.config.security.JwtProvider;
import com.brandol.config.security.TokenDto;
import com.brandol.converter.AgreementConverter;
import com.brandol.converter.MemberConverter;
import com.brandol.domain.Member;
import com.brandol.domain.Term;
import com.brandol.domain.enums.Gender;
import com.brandol.domain.enums.TermType;
import com.brandol.dto.request.AuthRequestDto;
import com.brandol.dto.response.AuthResponseDto;
import com.brandol.repository.AgreementRepository;
import com.brandol.repository.MemberRepository;
import com.brandol.repository.TermRepository;
import lombok.RequiredArgsConstructor;
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
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenDto login(AuthRequestDto.KakaoLoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElse(null);

        // 로그인
        if (member != null && member.isSignUp()) { // 해당 이메일로 가입O, 회원가입도 완료된 회원이 있으면 로그인 진행

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
        return MemberConverter.signUpResDto(member.getId());
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
            member.setProfile(request.getNickname(), request.getGender(), request.getAge(), "https://brandol.s3.ap-northeast-2.amazonaws.com/%EB%B8%8C%EB%9E%9C%EB%8F%8C+%EB%B0%B0%EA%B2%BD%ED%99%94%EB%A9%B4.png");
        }
        else {
            member.setProfile(request.getNickname(), request.getGender(), request.getAge(), "https://brandol.s3.ap-northeast-2.amazonaws.com/%EB%B8%8C%EB%9E%9C%EB%93%9C+%ED%94%84%EB%A1%9C%ED%95%84.png");
        }
        return member;
    }
}