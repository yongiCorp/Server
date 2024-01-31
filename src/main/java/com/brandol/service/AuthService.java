package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.GeneralException;
import com.brandol.config.security.JwtProvider;
import com.brandol.config.security.TokenDto;
import com.brandol.converter.MemberConverter;
import com.brandol.domain.Member;
import com.brandol.dto.request.AuthResquestDto;
import com.brandol.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    //private final RedisTemplate<String, String> redisTemplate;
    //private final RedisService redisService;

    @Transactional
    public TokenDto login(AuthResquestDto.KakaoLoginRequest request) {
        System.out.println("login함수 실행");
        Optional<Member> optionalMember = memberRepository.findByEmail(request.getEmail());

        System.out.println("이메일로 회원있는지 확인");

        // 로그인
        if (optionalMember.isPresent()) { // 해당 이메일로 가입된 회원이 있으면 로그인 진행

            Member member = optionalMember.get();
            System.out.println("가입되어있는 회원: " + member);

            // 토큰 생성
            TokenDto tokenDto = jwtProvider.createToken(member.getEmail(),member.getId(), member.getRole().toString());
            System.out.println("토큰 생성: ");
            System.out.println(tokenDto.getAccessToken());
            System.out.println(tokenDto.getRefreshToken());

            // Login 하면 Redis에 RT:{email} : {refresh token 값}  // key - value 형태로 refresh 토큰 저장
            // opsForValue() : set을 통해 key,value값 저장하고 get(key)통해 value가져올 수 있음.
            // refreshToken.getTokenExpriresTime().getTime() : 리프레시 토큰의 만료시간이 지나면 해당 값 자동 삭제
            String refreshToken = tokenDto.getRefreshToken();
            //redisService.setValuesWithTimeout("RT:"+member.getEmail(),refreshToken,jwtProvider.getTokenExpirationTime(refreshToken));
            //System.out.println("redis 에 refresh token 저장");
            System.out.println("tokenDto-ATK: " + tokenDto.getAccessToken() + " tokenDto-RTK: " + tokenDto.getRefreshToken());
            return tokenDto;
            
        // 회원가입 - 이용약관
        } else { // 해당 이메일로 가입된 회원이 없으면 회원가입 진행하라는 오류 메시지 전달
            //throw new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND);
            throw new GeneralException(ErrorStatus._MEMBER_NOT_FOUND_SIGNUP); // 이용약관 동의 api 호출
        }
    }

    // 이용약관 동의 api
    // 회원가입(이용약관 동의 + 닉네임 및 회원 정보 설정)
    /*@Transactional
    public AuthDto.termsAgreementRes termsAgreement(AuthDto.termsAgreementReq request) {
        Optional<Member> optionalMember = memberRepository.findByEmail(request.getMemberId());
    }*/

    // 회원정보 설정
    @Transactional
    public TokenDto signup(AuthResquestDto.SignupRequest request) { // 여기서 다시 다 받아옴
        // 가입안된 이메일인지는 로그인에서 확인했기 때문에 이 절차 X
        // Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        Member member = MemberConverter.toMember(request);
        memberRepository.save(member); // 회원정보 저장
        TokenDto tokenDto = jwtProvider.createToken(member.getEmail(),member.getId(), member.getRole().toString());
        String refreshToken = tokenDto.getRefreshToken();
        System.out.println("tokenDto-ATK: " + tokenDto.getAccessToken() + " tokenDto-RTK: " + tokenDto.getRefreshToken());
        //redisService.setValuesWithTimeout("RT:"+member.getEmail(),refreshToken,jwtProvider.getTokenExpirationTime(refreshToken));
        return tokenDto;
    }


    // 닉네임 중복확인 
    // 커스텀 어노테이션 적용 예정
    public Boolean checkNicknameDuplicate(AuthResquestDto.NicknameCheckReq request) {

        if (memberRepository.existsByNickname(request.getNickname())) {
            // throw new ErrorHandler(ErrorStatus._MEMBER_NICKNAME_DUPLICATE); // 닉네임 중복
            throw new GeneralException(ErrorStatus._MEMBER_NICKNAME_DUPLICATE); // 닉네임 중복
        }
        return memberRepository.existsByNickname(request.getNickname());
    }
}
