package com.project.common.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JsonLocale 애노테이션은 Bean 필드를 직렬화할 때
 * locale별로 다른 값을 동적으로 매핑하기 위해 사용됩니다.
 *
 * 사용 예:
 *
 * @JsonLocale(key = "title", locale = LocaleType.EN)
 * private String titleEn;
 *
 * key: 동일한 logical key로 그룹핑하여 DynamicLocalePropertyWriter에서 처리
 * locale: 해당 필드가 속하는 Locale (기본값은 LocaleType.KO)
 *
 * BeanSerializerModifier와 함께 사용하면 직렬화 시 요청 Locale에 맞는 값으로 변환됨
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonLocale {
    String key();
    LocaleType locale() default LocaleType.KO;
}
