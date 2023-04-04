package com.ddokddak.common.utils;

import com.ddokddak.common.props.AuthProperties;
import com.ddokddak.auth.domain.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {
    private String SECRET_KEY;
    private final String AUTHORITIES_KEY = "role";
    private final AuthProperties authProperties;
    public static final String AUTHORIZATION_HEADER = "Authorization"; //"X-AUTH-TOKEN"
    public static final Long ACCESS_TOKEN_EXPIRE_MS = 1000L * 60 * 60;		// 1hour
    public static final Long REFRESH_TOKEN_EXPIRE_MS = 1000L * 60 * 60 * 24 * 7;	// 1week

    @PostConstruct
    public void init() {
        this.SECRET_KEY = authProperties.getToken().getTokenSecret();
    }

    public String createAccessTokenForDev() {

        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_MS);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject("test")
                .claim("userId", 1)
                .claim(AUTHORITIES_KEY, "ROLE_USER")
                .setIssuer("DoDone")
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public String createAccessToken(Authentication authentication) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_MS);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();
        String userEmail = userPrincipal.getUsername();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userEmail)
                .claim("userId", userId)
                .claim(AUTHORITIES_KEY, role)
                .setIssuer("DoDone")
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_MS);

        String refreshToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setIssuer("DoDone")
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
        saveRefreshToken(authentication, refreshToken);
        return refreshToken;
    }

    // Access Token을 검사하고 얻은 정보로 Authentication 객체 생성
    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(
                        claims.get(AUTHORITIES_KEY)
                                .toString()
                                .split(",")
                        ).map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(((Number) claims.get("userId")).longValue())
                .email(claims.getSubject())
                .password("")
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(userPrincipal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    // Access Token 만료시 갱신때 사용할 정보를 얻기 위해 Claim 리턴
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private void saveRefreshToken(Authentication authentication, String refreshToken) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String email = userPrincipal.getName();
        //userRepository.updateRefreshToken(email, refreshToken);
    }
}
