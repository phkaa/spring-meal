package com.project.domain.json.dto;

import com.project.common.json.JsonLocale;
import com.project.common.json.LocaleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JsonLocaleDto {
    private String content;
    
    @JsonLocale(key = "title", locale = LocaleType.KO)
    private String koTitle;

    @JsonLocale(key = "title", locale = LocaleType.EN)
    private String enTitle;
}
