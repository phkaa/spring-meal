# CommonResponse 사용 가이드

## 개요

`CommonResponse<T>`는 Spring Boot 컨트롤러에서 API 응답을 일관되게 감싸서 반환하기 위한 제네릭 DTO입니다.

- `message` : 응답 메시지 (성공/실패 안내, 에러 메시지 등)
- `data` : 실제 반환할 데이터 객체 (제네릭 타입)

이 클래스를 사용하면 모든 컨트롤러 응답 형식을 통일할 수 있어 유지보수와 테스트가 쉬워집니다.

## 사용 예시

### 1. 성공 응답 반환

```java
@GetMapping("/locale")
public CommonResponse<JsonLocaleDto> getJsonLocal() {
    return CommonResponse.success(jsonApplication.getJsonLocale());
}
```

- 메시지 없이 데이터만 반환
- 내부적으로 `Optional.ofNullable`로 null 메시지 처리

```java
@GetMapping("/hello")
public CommonResponse<String> hello() {
    return CommonResponse.successMessage("Hello World!");
}
```

- 메시지만 반환하고 데이터는 없는 경우

### 2. 실패 응답 반환

```java
@GetMapping("/error")
public CommonResponse<Void> errorExample() {
    return CommonResponse.fail("처리 중 오류 발생", null);
}
```

- 실패 코드와 메시지를 함께 전달 가능

## 장점

1. **일관된 API 응답 형식**

   - 모든 컨트롤러에서 동일한 `message` + `data` 구조로 응답
   - 프론트엔드에서 처리 로직 단순화

2. **제네릭 타입 지원**

   - 다양한 데이터 타입(`T`)을 자유롭게 반환 가능
   - DTO, 문자열, 리스트 등 모두 사용 가능

3. **편리한 정적 메서드 제공**

   - `success()`, `successMessage()`, `fail()` 등 메서드로 짧고 직관적인 코드 작성 가능

4. **Optional 기반 null 처리**

   - 메시지가 null이어도 자동으로 빈 문자열 처리 → NPE 방지

