package com.project.common.json;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * LocaleAwareSerializerModifier는 Jackson 직렬화 과정에서 Bean의 필드를 동적으로 변경하기 위한 커스터마이저입니다.
 *
 * 주요 기능:
 * 1. Bean에 정의된 필드 중 @JsonLocale 애노테이션이 붙은 필드를 찾아 locale별로 그룹화
 * 2. 기존 locale별 필드를 제거하고, DynamicLocalePropertyWriter를 이용해
 *    locale에 따라 값을 동적으로 반환하는 단일 필드로 대체
 *
 * 예시:
 *   @JsonLocale(key="title", locale=Locale.EN)
 *   private String titleEn;
 *
 *   @JsonLocale(key="title", locale=Locale.KO)
 *   private String titleKo;
 *
 *   → 직렬화 결과에서 "title" 필드는 요청 locale에 맞는 값으로 출력됨
 */
public class LocaleAwareSerializerModifier extends BeanSerializerModifier {
    @Override
    public List<BeanPropertyWriter> changeProperties(
        SerializationConfig config,
        BeanDescription beanDesc,
        List<BeanPropertyWriter> beanProperties) {

        Map<String, Map<String, BeanPropertyWriter>> localizedFields = new HashMap<>();

        for (BeanPropertyWriter writer : beanProperties) {
            JsonLocale annotation = writer.getMember().getAnnotation(JsonLocale.class);
            if (annotation != null) {
                localizedFields
                    .computeIfAbsent(annotation.key(), k -> new HashMap<>())
                    .put(annotation.locale().name().toLowerCase(), writer);
            }
        }

        for (Map.Entry<String, Map<String, BeanPropertyWriter>> entry : localizedFields.entrySet()) {
            String key = entry.getKey();
            Map<String, BeanPropertyWriter> langMap = entry.getValue();

            BeanPropertyWriter dynamicWriter = new DynamicLocalePropertyWriter(key, langMap);
            beanProperties.removeAll(langMap.values());
            beanProperties.add(dynamicWriter);
        }

        return beanProperties;
    }
}
