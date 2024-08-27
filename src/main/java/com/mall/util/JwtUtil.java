package com.mall.util;

import com.mall.exception.ErrorCode;
import com.mall.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;


public class JwtUtil {
    private static String key = "1234567890123456789012345678901234567890";

    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey secretKey = null;

        try {
            secretKey = Keys.hmacShaKeyFor(JwtUtil.key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        String jwtStr = Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(secretKey)
                .compact();
        return jwtStr;
    }

    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(JwtUtil.key.getBytes(StandardCharsets.UTF_8));
            claim = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJwt(token)
                    .getBody();
        } catch (MalformedJwtException malformedJwtException) {
            throw new RuntimeException(malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            throw new UnauthorizedException(ErrorCode.EXPIRED_TOKEN);
        } catch (InvalidClaimException invalidClaimException) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return claim;
    }
}
