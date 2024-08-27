package com.mall.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.exception.ErrorCode;
import com.mall.exception.UnauthorizedException;
import com.mall.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

public class JwtCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Preflight요청은 체크하지 않음
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String path = request.getRequestURI();
        //api/member/ 경로의 호출은 체크하지 않음
        if (path.startsWith("/api/member/")) {
            return true;
        }
        //이미지 조회 경로는 체크하지 않음
        if (path.startsWith("/api/products/view/")) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        try {
            String accessToken = authHeader.substring(7);
            Map<String, Object> claims = JwtUtil.validateToken(accessToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }
    }
}
