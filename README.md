# 🍽️ spring-meal

**spring-meal**은 Spring Boot 기반의 **멀티 모듈 프로젝트**로,  
다른 프로젝트에서 재사용 가능한 **공통 모듈**과 **API 테스트 환경**을 제공합니다.  
개발 중 유용하게 쓰일 수 있는 기능, 유틸리티, 공통 설정 등을 모아  
필요할 때 꺼내 쓸 수 있도록 관리하는 것을 목표로 합니다.

---

## 📂 프로젝트 구조

```
spring-meal
├── meal-api      # API 테스트 모듈 (Controller, 샘플 API 등)
└── meal-common   # 공통 모듈 (유틸, DTO, 공통 설정 등)
└── meal-domain   # API Domain 테스트 모듈 (비즈니스 로직, 공통 설정 등)
```

---

## 🛠 기술 스택

- **Java 17**
- **Spring Boot 3.x**
- **Gradle**
- **Junit 5**

---

## ✨ 공통 기능 목록
### - [Json Locale 직렬화](./meal-common/readme_jsonlocale.md)


---

## 📌 모듈 설명

### 1. meal-common
- 여러 프로젝트에서 재사용 가능한 코드와 설정 모음
- 주요 내용:
  - **유틸 클래스** (문자열, 날짜, 파일 처리 등)
  - **공통 DTO**
  - **상수/Enum**
  - **예외 처리 클래스**
  - **공통 설정(Config)**

> 빌드시 실행 가능한 JAR이 아닌 **라이브러리 JAR**로 패키징됩니다.

---

### 2. meal-domain
- `meal-common` 모듈을 사용하여 비즈니스 로직을 구현하는 모듈
- 주요 내용:
  - **api 와 연동하여 비즈니스 로직 구성** 

> 빌드시 실행 가능한 JAR이 아닌 **라이브러리 JAR**로 패키징됩니다.

---

### 3. meal-api
- `meal-common` 모듈을 사용하여 API를 구현하고 테스트하는 모듈
- 샘플 Controller, Service, 예외 처리 구조를 포함
- 다른 프로젝트에 적용하기 전 기능 검증 및 예제 제공

> 빌드시 실행 가능한 **Spring Boot Application JAR**로 패키징됩니다.

---

## 🚀 실행 방법

### 전체 빌드
```bash
./gradlew clean build
```

### API 서버 실행
```bash
./gradlew :meal-api:bootRun
```
실행 후: [http://localhost:8080](http://localhost:8080)

---

## 📦 빌드 결과물

- `meal-common`:  
  - `build/libs/meal-common-<version>-SNAPSHOT-plain.jar` → **라이브러리 JAR**
- `meal-domain`:
  - `build/libs/meal-domain-<version>-SNAPSHOT-plain.jar` → **라이브러리 JAR**
- `meal-api`:  
  - `build/libs/meal-api-<version>-SNAPSHOT.jar` → **실행 가능 JAR** (`java -jar` 가능)

---

## 💡 사용 예시

다른 프로젝트에서 `meal-common` 모듈을 재사용하려면:

```groovy
dependencies {
    implementation files('/path/to/meal-common-<version>.jar')
}
```

또는 Maven/Gradle 저장소에 배포 후:

```groovy
dependencies {
    implementation 'com.project:meal-common:1.0.0'
}
```

---

## 📝 라이선스
MIT License  
자유롭게 수정 및 사용 가능하나, 출처를 표기해 주시면 감사하겠습니다.
