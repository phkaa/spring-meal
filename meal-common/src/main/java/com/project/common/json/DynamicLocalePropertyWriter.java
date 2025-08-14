package com.project.common.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Map;

/**
 * DynamicLocalePropertyWriter는 Bean의 직렬화 과정에서
 * locale에 따라 동적으로 값을 반환하는 커스터마이징된 BeanPropertyWriter입니다.
 *
 * 주요 기능:
 * 1. 생성 시, locale별 필드(writer)를 Map 형태로 저장
 * 2. serializeAsField() 호출 시 현재 요청 Locale을 확인
 * 3. 해당 Locale에 맞는 BeanPropertyWriter를 선택하여 값을 직렬화
 * 4. 요청 Locale에 맞는 값이 없으면 기본 Locale(KO)을 사용
 *
 * 사용 예:
 *   Map<String, BeanPropertyWriter> langMap = Map.of(
 *       "en", writerEn,
 *       "ko", writerKo
 *   );
 *   BeanPropertyWriter dynamicWriter = new DynamicLocalePropertyWriter("title", langMap);
 *
 * → JSON 직렬화 결과에서 "title" 필드는 요청 Locale에 따라 동적으로 값이 출력됨
 */
public class DynamicLocalePropertyWriter extends BeanPropertyWriter {
    private final String key;
    private final Map<String, BeanPropertyWriter> langMap;

    public DynamicLocalePropertyWriter(String key, Map<String, BeanPropertyWriter> langMap) {
        super(langMap.values().iterator().next());
        this.key = key;
        this.langMap = langMap;
    }

    @Override
    public String getName() {
        return key;
    }

    @Override
    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        Locale locale = LocaleContextHolder.getLocale();
        String lang = locale.getLanguage();

        BeanPropertyWriter writer = langMap.getOrDefault(lang, langMap.get(LocaleType.KO.name().toLowerCase()));

        if (writer != null) {
            Object value = writer.get(bean);

            if (value == null) {
                gen.writeFieldName(key);
                gen.writeNull();
                return;
            }

            gen.writeFieldName(key);

            JsonSerializer<Object> serializer = writer.getSerializer();

            if (serializer == null) {
                serializer = prov.findValueSerializer(value.getClass(), writer);
            }

            serializer.serialize(value, gen, prov);
        }
    }
}
