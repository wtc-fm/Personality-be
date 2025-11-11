# Personality Test Backend (Kotlin + Spring + MySQL)

간단 회원가입/로그인 + JWT 인증 + 성향 저장/조회  
Spring Boot 3 + Kotlin + DDD 구조 + MySQL 기반 백엔드

---

## 프로젝트 개요

이 프로젝트는 **사용자 인증 + 성격 테스트 결과 저장**을 위한 최소 백엔드(MVP)입니다.  
React 프론트엔드와 연동되며, JWT 기반 인증 구조를 사용합니다.

### 기술 스택

- Kotlin 1.9
- Spring Boot 3.x
- Spring Security + JWT
- JPA (Hibernate)
- MySQL 8
- Gradle (Kotlin DSL)
- DDD 구조(domain/application/presentation/global)

---

# 기능 목록 (Features)

## 1. Auth (회원가입 / 로그인)

### 1-1. 회원가입 (Signup)

- [ ] POST /api/auth/signup
- [ ] SignupRequest(email, password) DTO 생성
- [ ] User 엔티티(email, password) 저장
- [ ] 비밀번호 bcrypt 암호화
- [ ] 이메일 중복 체크
- [ ] 성공 시 200 OK

### 1-2. 로그인 (Login)

- [ ] POST /api/auth/login
- [ ] LoginRequest DTO 생성
- [ ] 이메일 기반 사용자 조회
- [ ] bcrypt 비밀번호 검증
- [ ] 실패 시 INVALID_LOGIN 에러 반환
- [ ] JWT 토큰 발급
- [ ] TokenResponse(token) 반환

---

## 2. Security (JWT 인증)

### 2-1. JWT Provider

- [ ] HS256 기반 secret key
- [ ] userId를 payload에 저장
- [ ] 만료시간 7일
- [ ] createToken(userId) 구현

### 2-2. JWT Filter

- [ ] Authorization: Bearer {token} 파싱
- [ ] JWT 검증 후 userId 추출
- [ ] UserPrincipal 생성
- [ ] SecurityContextHolder에 인증 저장

### 2-3. SecurityConfig

- [ ] /api/auth/** → permitAll
- [ ] /api/users/** → 인증 필요
- [ ] 세션 STATELESS
- [ ] CORS 허용

---

## 3. User (성향 저장 & 조회)

### 3-1. 성향 저장 (Save Personality)

- [ ] POST /api/users/personality
- [ ] JWT 인증 필요
- [ ] PersonalityRequest(personalityJson) DTO 정의
- [ ] user.personalityJson 필드 업데이트
- [ ] 저장 후 200 OK

###3-2. 성향 조회 (Get Personality)

- [ ] GET /api/users/personality
- [ ] JWT 인증 필요
- [ ] userId 기반 조회
- [ ] personalityJson 반환

---

## 4. Domain Model

###User Entity

- [ ] id: Long
- [ ] email: String (unique)
- [ ] password: String (암호화 저장)
- [ ] personalityJson: TEXT

### ✔ UserRepository

- [ ] existsByEmail()
- [ ] findByEmail()
- [ ] findById()

---

## 🚨 5. Global Error Handling

### ✔ ErrorCode Enum

- EMAIL_ALREADY_EXISTS
- INVALID_LOGIN
- USER_NOT_FOUND
- UNAUTHORIZED
- INTERNAL_SERVER_ERROR

### ✔ CustomException

- ErrorCode 기반 공통 예외 처리

### ✔ GlobalExceptionHandler

- CustomException → ErrorResponse(JSON)
- 기타 Exception → 500 INTERNAL_SERVER_ERROR

---