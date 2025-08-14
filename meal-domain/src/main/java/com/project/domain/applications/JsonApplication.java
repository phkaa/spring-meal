package com.project.domain.applications;

import com.project.domain.json.dto.JsonLocaleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
public class JsonApplication {
    public JsonLocaleDto getJsonLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        String lang = locale.getLanguage();

        log.info("현재 언어 설정 {}", lang);

        return JsonLocaleDto.builder()
                .content("콘텐츠 입니다.")
                .koTitle("한글 타이틀")
                .enTitle("영문 타이틀")
                .build();
    }
}
