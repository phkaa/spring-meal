# @JsonLocale 사용 가이드

## 개요

`@JsonLocale`은 JSON 직렬화 시 다국어 메시지를 자동으로 처리하기 위한 커스텀 어노테이션입니다. 이 어노테이션을 필드에 적용하면, 해당 필드는 요청 시 `Accept-Language` 헤더에 따라 자동으로 번역된 값을 반환합니다.

## 사용 예시

```java
public class MealResponse {
    @JsonLocale(key = "title", locale = LocaleType.KO)
    private String koTitle;

    @JsonLocale(key = "title", locale = LocaleType.EN)
    private String enTitle;
}
```

## 설정 방법

`@JsonLocale`을 사용하려면 Jackson의 커스텀 SerializerModifier를 등록해야 합니다. Spring Boot에서는 아래와 같이 설정할 수 있습니다.

```java
@Configuration
public class JacksonConfig {

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
```

- `LocaleAwareSerializerModifier`는 `@JsonLocale`이 붙은 필드를 찾아서 다국어 직렬화 로직을 적용합니다.
- `Accept-Language` 헤더에 따라 KO/EN 등 Locale에 맞는 값을 반환하도록 처리됩니다.

## 장점

- **다국어 지원**: 클라이언트의 언어 설정에 따라 자동으로 메시지를 번역하여 반환합니다.
- **유지보수 용이**: 메시지 번역 로직을 중앙에서 관리할 수 있어 유지보수가 용이합니다.
- **유연성**: 다양한 Locale에 대한 지원을 추가하기 쉽습니다.

