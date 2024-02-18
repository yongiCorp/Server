package com.brandol.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider implements InitializingBean {

    private final PrincipalDetailsService principalDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    private static final String AUTHORITIES_KEY = "role";
    private final long accessTokenValidTime = 1000L * 60 * 60 * 48; // 48시간
    private final long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 14; // 14일

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    // 토큰 생성
    // atk, rtk 각각 생성으로 변경하기
    // @Transactional
    public TokenDto createToken(String email, Long id, String authorities) {
        System.out.println("createToken() 실행");
        Date now = new Date();
        Date atkExpiration = new Date(now.getTime() + accessTokenValidTime); // 액세스 토큰 만료 시간
        Date rtkExpiration = new Date(now.getTime() + refreshTokenValidTime); // 리프레쉬 토큰 만료 시간

        String accessToken = Jwts.builder()
                .claim("email", email) // email
                .claim("id", id) // member_id
                .claim(AUTHORITIES_KEY, authorities) // role
                .setSubject("atk") // access token
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(atkExpiration) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // HS256알고리즘과 key로 Signature 생성
                .compact();

        String refreshToken = Jwts.builder()
                .claim("email", email)
                .setSubject("rtk") // refresh token
                .setExpiration(rtkExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new TokenDto(accessToken, refreshToken);
    }

    // HTTP Request 헤더로부터 토큰 추출
    public String resolveToken(HttpServletRequest httpServletRequest) {
        System.out.println("Provider의 resolveToken() 실행");
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰에서 정보 추출
    // 토큰의 claims 추출
    public Claims getClaims(String token) {
        System.out.println("getClaims() 실행");
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 인증 객체 UsernamePasswordAuthenticationToken 리턴
    public Authentication getAuthentication(String token) {

        // 이메일 -> id로 변경
        String memberId = getClaims(token).get("id").toString();
        UserDetails userDetails = principalDetailsService.loadUserByUsername(memberId); // 이메일 -> id로 변경
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    // Filter에서 accessToken 검증을 위해 사용
    public boolean validateAccessToken(String accessToken) {
        try {
            /*if (redisService.getValues(accessToken) != null // NPE 방지
                    && redisService.getValues(accessToken).equals("logout")) { // 로그아웃 했을 경우
                return false;
            }*/
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
            return true;
        } catch (ExpiredJwtException e) { // accessToken 만료여도 일단 true, refreshToken 확인 후 재발급
            log.info("만료된 토큰");
            throw new JwtException("Expired Token Exception");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰");
            throw new JwtException("Invalid Token Exception");
        }catch (SecurityException | MalformedJwtException e) {
            log.info("잘린 토큰");
            throw new JwtException("Invalid Token Exception");
        }  catch (NullPointerException e) {
            log.info("토큰 없음");
            throw new JwtException("Invalid Token Exception");
        }  catch (Exception e) {
            return false;
        }
    }

}
