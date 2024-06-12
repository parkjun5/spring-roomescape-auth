package roomescape.apply.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import roomescape.apply.auth.application.exception.IllegalTokenException;
import roomescape.apply.auth.ui.dto.LoginResponse;
import roomescape.apply.member.domain.MemberRoleNames;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class JwtTokenManager {
    public static final String NOT_VALID_TOKEN_ERROR = "잘못된 토큰입니다. 재 로그인 해주세요.";
    private static final long EXPIRATION_TIME = 60;
    private final SecretKey secretKey;

    public JwtTokenManager(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(LoginResponse loginResponse) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plusMinutes(EXPIRATION_TIME);

        return Jwts.builder()
                .subject(loginResponse.email())
                .claim("name", loginResponse.name())
                .claim("role", loginResponse.memberRoleNames())
                .issuedAt(Timestamp.valueOf(now))
                .expiration(Timestamp.valueOf(expiryDate))
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            parseJwt(token);
        } catch (ExpiredJwtException e) {
            throw new IllegalTokenException(NOT_VALID_TOKEN_ERROR, e);
        }
    }

    public String getRoleNameFromToken(String token) {
        MemberRoleNames memberRoleNames = parseJwt(token).get("role", MemberRoleNames.class);
        return memberRoleNames.getJoinedNames();
    }

    private Claims parseJwt(String token) {

        return Jwts.parser()
                .decryptWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
