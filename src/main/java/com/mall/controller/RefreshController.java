package com.mall.controller;

import com.mall.exception.ErrorCode;
import com.mall.exception.UnauthorizedException;
import com.mall.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refresh")
public class RefreshController {
    @GetMapping
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader,
                                       String refreshToken) {
        if (refreshToken == null) {
            throw new RuntimeException("NULL_REFRASH");
        }
        if (authHeader == null || authHeader.length() < 7) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }
        String accessToken = authHeader.substring(7);

        if (checkExpiredToken(accessToken) == false) {
            return Map.of("accessToken"
                    , accessToken,
                    "refreshToken"
                    , refreshToken);
        }

        Map<String, Object> claims = JwtUtil.validateToken(refreshToken);

        String newAccessToken = JwtUtil.generateToken(claims, 10);
        String newRefreshToken = checkTime((Integer) claims.get("exp")) == true ?
                JwtUtil.generateToken(claims, 60 * 24) : refreshToken;
        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    //시간이 1시간 미만으로 남았다면
    private boolean checkTime(Integer exp) {
        java.util.Date expDate = new java.util.Date((long) exp * (1000));
        long gap = expDate.getTime() - System.currentTimeMillis();
        long leftMin = gap / (1000 * 60);
        return leftMin < 60;
    }

    private boolean checkExpiredToken(String token) {
        try {
            JwtUtil.validateToken(token);
        } catch (Exception ex) {
            if (ex.getMessage().equals("Expired")) {
                return true;
            }
        }
        return false;
    }
}
