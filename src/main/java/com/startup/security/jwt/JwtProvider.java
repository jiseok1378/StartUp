package com.startup.security.jwt;

import com.startup.dto.login.TokenDtoImpl;
import com.startup.dto.login.inter.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("spring.jwt.secret")
    private String secretKey;

    private final static long accessTokenValidMillisecond = 60 * 60 * 1000L; // 1시간
    private final static long refreshTokenValidMillisecond = 60 * 60 * 1000L * 24; // 24시간 (하루)

    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private String createTokenInner(String userPk, List<String> roles, long validSecond){
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validSecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public TokenDto createToken(String userPk, List<String> roles){
        String accessToken = createTokenInner(userPk, roles, accessTokenValidMillisecond);
        String refreshToken = createTokenInner(userPk, roles, refreshTokenValidMillisecond);
        return TokenDtoImpl.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Date getExpireDate(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }
    public boolean isAccessTokenValid(String refreshToken, String accessToken){
        Date refreshDate = getExpireDate(refreshToken);
        Date accessDate = getExpireDate(accessToken);
        return refreshDate.before(accessDate); // access token이 refresh token보다 이전이면 true
    }
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }
    public boolean validationToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

}
