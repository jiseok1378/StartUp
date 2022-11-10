package com.startup.security.jwt;

import com.auth0.jwt.JWT;
import com.startup.dto.login.TokenDtoImpl;
import com.startup.dto.login.inter.TokenDto;
import com.startup.dto.user.UserDto;
import com.startup.entity.User;
import com.startup.util.Tuple;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    /**
     * 토큰 검증에 대한 결과를 주는 enum 클래스
     */
    public enum TokenValidation {
        ACCESS_TOKEN_VALID_AND_REFRESH_TOKEN_VALID,
        ACCESS_TOKEN_VALID_AND_REFRESH_TOKEN_INVALID,
        ACCESS_TOKEN_INVALID_AND_REFRESH_TOKEN_VALID,
        ACCESS_TOKEN_INVALID_AND_REFRESH_TOKEN_INVALID;

        public static TokenValidation getValidation(Date accessToken, Date refreshToken){
            Date now = new Date();
            boolean isValidAcc = now.before(accessToken);
            boolean isValidRef = now.before(refreshToken);
            if(!isValidAcc && !isValidRef) return ACCESS_TOKEN_INVALID_AND_REFRESH_TOKEN_INVALID;
            else if(isValidAcc && !isValidRef) return ACCESS_TOKEN_VALID_AND_REFRESH_TOKEN_INVALID;
            else if(!isValidAcc && isValidRef) return ACCESS_TOKEN_INVALID_AND_REFRESH_TOKEN_VALID;
            else return ACCESS_TOKEN_VALID_AND_REFRESH_TOKEN_VALID;
        }
    }


    @Value("spring.jwt.secret")
    private String secretKey;

    public final static String TOKEN_HEADER_KEY = "X-AUTH-TOKEN";
    private final static long accessTokenValidMillisecond = 2000L;
            //60 * 60 * 1000L; // 1시간
    private final static long refreshTokenValidMillisecond = 1000L;
            //60 * 60 * 1000L * 24; // 24시간 (하루)

    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private Tuple<String, Date> createTokenInner(Optional<Claims> claims, long validSecond){
        JwtBuilder builder = Jwts.builder();
        claims.ifPresent(builder::setClaims);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + validSecond);

        String token = builder
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return new Tuple<>(token, expirationDate);
    }
    public Tuple<String, Date> createAccessToken(String userPk, List<String> roles){
        Claims accessClaims = Jwts.claims().setSubject(userPk);
        accessClaims.put("roles", roles);
        return createTokenInner(Optional.of(accessClaims), accessTokenValidMillisecond);
    }
    public Tuple<String, Date> createRefreshToken(){
        return createTokenInner(Optional.empty(), refreshTokenValidMillisecond);
    }
    public TokenDto createAccessAndRefreshToken(String userPk, List<String> roles){
        var accessToken = createAccessToken(userPk, roles);
        var refreshToken = createRefreshToken();
        return TokenDtoImpl.builder()
                .accessToken(accessToken.getFirst())
                .refreshToken(refreshToken.getFirst())
                .expirationDate(accessToken.getSecond())
                .build();
    }
    private Date getExpireDate(String token) throws ExpiredJwtException{
        return JWT.decode(token).getExpiresAt(); // 토큰이 만료되면 parseClaimsJws 함수에서 예외를 던지므로 JWT 라이브러리로 대체
    }

    @Transactional
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(TOKEN_HEADER_KEY);
    }

    public boolean validationToken(String accessToken){
        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getExpiration();
        return !expiration.before(new Date());
    }
    public TokenValidation getValidationTokenWithRefresh(String accessToken, String refreshToken) throws ExpiredJwtException{
//        try {
        Date accessExpiration = getExpireDate(accessToken);
        Date refreshExpiration = getExpireDate(refreshToken);
        return TokenValidation.getValidation(accessExpiration, refreshExpiration);
//        }catch (Exception e){
//            e.printStackTrace();
//            return TokenValidation.ACCESS_TOKEN_INVALID_AND_REFRESH_TOKEN_INVALID;
//        }
    }

    /**
     * access 토큰과 refresh 토큰을 검증하여 아직 유효한 정보면 access Token을 재발급 또는 그대로 리턴한다.
     * @param accessToken
     * @return Access와 Refresh가 둘다 유효하지 않다면 null을 리턴한다.
     */
    @Transactional
    public Optional<TokenDto> checkValidationAndReissue(User user, String accessToken){
        TokenValidation validation = getValidationTokenWithRefresh(accessToken, user.getRefreshToken());
        TokenDtoImpl token = null;
        System.out.println(user.getUserId() + "에 대한 토큰 유효성 검증");
        switch (validation){
            case ACCESS_TOKEN_INVALID_AND_REFRESH_TOKEN_INVALID:
                System.out.println("만료된 토큰입니다. 재로그인해주세요.");
                break;
            case ACCESS_TOKEN_VALID_AND_REFRESH_TOKEN_VALID:
                System.out.println("유효한 토큰입니다.");
                token = TokenDtoImpl.builder()
                        .accessToken(accessToken)
                        .expirationDate(this.getExpireDate(accessToken))
                        .refreshToken(user.getRefreshToken())
                        .build();
                break;
            case ACCESS_TOKEN_INVALID_AND_REFRESH_TOKEN_VALID:
                System.out.println("Access 토큰만 재발급합니다.");
                Tuple<String, Date> newAccessToken = this.createAccessToken(user.getUserId(), user.getRoles());
                token = TokenDtoImpl.builder()
                        .accessToken(newAccessToken.getFirst())
                        .refreshToken(user.getRefreshToken())
                        .expirationDate(newAccessToken.getSecond())
                        .build();
                break;
            case ACCESS_TOKEN_VALID_AND_REFRESH_TOKEN_INVALID:
                System.out.println("Refresh 토큰만 재발급합니다.");
                Tuple<String,Date> newRefreshToken = this.createRefreshToken();
                token = TokenDtoImpl.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken.getFirst())
                        .expirationDate(this.getExpireDate(accessToken))
                        .build();
                System.out.println("재발급한 토큰 : " + newRefreshToken.getFirst());
                user.setRefreshToken(newRefreshToken.getFirst());
                break;
        }
        return Optional.ofNullable(token);
    }

}
