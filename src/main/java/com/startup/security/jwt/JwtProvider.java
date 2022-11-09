package com.startup.security.jwt;

import com.startup.dto.login.TokenDtoImpl;
import com.startup.dto.login.inter.TokenDto;
import com.startup.util.Tuple;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.spi.TupleBuilderTransformer;
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
import java.util.Optional;

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

    private Tuple<String, Date> createTokenInner(Optional<Claims> claims, String userPk, List<String> roles, long validSecond){
        JwtBuilder builder = Jwts.builder();
        claims.ifPresent(builder::setClaims);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + validSecond);

        String token = builder
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return new Tuple(token, expirationDate);
    }
    public TokenDto createToken(String userPk, List<String> roles){
        Claims accessClaims = Jwts.claims().setSubject(userPk);
        accessClaims.put("roles", roles);
        var accessToken = createTokenInner(Optional.of(accessClaims), userPk, roles, accessTokenValidMillisecond);
        var refreshToken = createTokenInner(Optional.empty(), userPk, roles, refreshTokenValidMillisecond);
        return TokenDtoImpl.builder()
                .accessToken(accessToken.getFirst())
                .refreshToken(refreshToken.getFirst())
                .expirationDate(accessToken.getSecond()) //
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
