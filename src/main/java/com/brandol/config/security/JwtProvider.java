package com.brandol.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class JwtProvider implements InitializingBean {

    private final PrincipalDetailsService principalDetailsService;
    private final RedisService redisService;

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    private static final String AUTHORITIES_KEY = "role";
    private final long accessTokenValidTime = 1000L * 60 * 60; // 1시간
    private final long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 14; // 14일

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
    }

    // 토큰 생성
    @Transactional
    public TokenDto createToken(String email, Long id, String authorities){

        Date now = new Date();
        Date atkExpiration = new Date(now.getTime() + accessTokenValidTime); // 액세스 토큰 만료 시간
        Date rtkExpiration = new Date(now.getTime() + refreshTokenValidTime); // 리프레쉬 토큰 만료 시간

        String accessToken = Jwts.builder()
                .claim("email", email) // email
                .claim("id", id) // member_id
                .claim(AUTHORITIES_KEY, authorities) // role
                //.setSubject("access-token") // tokenType을 만들어둘지?
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(atkExpiration) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // HS256알고리즘과 key로 Signature 생성
                .compact();

        String refreshToken = Jwts.builder()
                .claim("email", email)
                .setExpiration(rtkExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new TokenDto(accessToken, refreshToken);
    }

    // HTTP Request 헤더로부터 토큰 추출
    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰에서 정보 추출
    // 토큰의 claims 추출
    public Claims getClaims(String token) {
        System.out.println("getClaims() 실행" );
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 인증 객체 UsernamePasswordAuthenticationToken 리턴
    public Authentication getAuthentication(String token) {
        System.out.println("getAuthentication() 실행" );
        String email = getClaims(token).get("email").toString();
        UserDetails userDetails = principalDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public long getTokenExpirationTime(String token) {
        return getClaims(token).getExpiration().getTime();
    }

    // Filter에서 accessToken 검증을 위해 사용
    // 유효기간만  제외하고 정상인 토큰일 경우, 유저 모르게 Atk을 재발급하고 로그인 연장시키면서 다시 요청을 처리하도록하기위함
    // 프론트 처리 필요: 재발급 api에 접근한 후, 응답에 따라 원래 처리할 요청 or 로그인으로 리다이렉트 되게끔 하기
    public boolean validateAccessToken(String accessToken) {
        try {
            if (redisService.getValues(accessToken) != null // NPE 방지
                    && redisService.getValues(accessToken).equals("logout")) { // 로그아웃 했을 경우
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
            return true;
        } catch(ExpiredJwtException e) { // accessToken 만료여도 일단 true, > refreshToken 확인?
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // refreshToken 검증
    public boolean validateRefreshToken(String refreshToken) {
        try {
            if (redisService.getValues(refreshToken).equals("delete")) { // 회원 탈퇴했을 경우
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.");
        } catch (NullPointerException e){
            log.error("JWT Token is empty.");
        }
        return false;
    }

    // 재발급 검증 API에서 사용(accessToken 유효기간 만료여부만 확인 - 만료 시 true, 아니면 false)
    public boolean validateAccessTokenOnlyExpired(String accessToken) {
        try {
            return getClaims(accessToken)
                    .getExpiration()
                    .before(new Date()); // 토큰 만료 시간이 현재보다 이전이면 true(만료)
        } catch(ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}