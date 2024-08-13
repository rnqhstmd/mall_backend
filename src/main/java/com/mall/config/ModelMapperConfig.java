package com.mall.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                // 이름이 같은 필드 간 값 자동 매핑
                .setFieldMatchingEnabled(true)
                // 접근 레벨 설정으로 getter/setter 없어도 필드 매핑 가능
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // LOOSE 전략으로 매핑 시 이름이 같지 않더라도 유연하게 매핑 수행
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
