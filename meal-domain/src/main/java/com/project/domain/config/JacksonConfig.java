package com.project.domain.config;

import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.project.common.json.LocaleAwareSerializerModifier;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    /**
     * LocaleAwareSerializerModifier를 Jackson ObjectMapper에 적용하는 커스터마이저
     * - 모든 Bean 직렬화 시 @JsonLocale 애노테이션 기반 필드를 동적 locale 필드로 변환
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer localeModifierCustomizer() {
        return builder -> builder.postConfigurer(objectMapper -> {
            SerializerFactory existingFactory = objectMapper.getSerializerFactory();

            objectMapper.setSerializerFactory(
                    existingFactory.withSerializerModifier(new LocaleAwareSerializerModifier())
            );
        });
    }

}
