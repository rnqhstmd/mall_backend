package com.mall.config;

import com.mall.security.filter.JwtCheckFilter;
import com.mall.security.handler.CustomAccessDeniedHandler;
import com.mall.security.handler.LoginApiFailHandler;
import com.mall.security.handler.LoginApiSuccessHandler;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {
    // 보안 관련 요청 가로채고 처리하는 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("---------security config---------");
        httpSecurity.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        httpSecurity.sessionManagement(sessionConfig ->
                sessionConfig.sessionCreationPolicy(SessionCreationPolicy.NEVER));
        httpSecurity.csrf(config -> config.disable());

        httpSecurity.formLogin(config -> {
            config.loginPage("/api/member/login");
            config.successHandler(new LoginApiSuccessHandler());
            config.failureHandler(new LoginApiFailHandler());
        });

        httpSecurity.exceptionHandling(config -> config.accessDeniedHandler(new CustomAccessDeniedHandler()));

        httpSecurity.addFilterBefore(new JwtCheckFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 체크
        return httpSecurity.build();
    }

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowCredentials(Boolean.TRUE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
