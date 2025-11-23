# Personality Test Backend (Kotlin + Spring + MySQL)

간단 회원가입/로그인 + JWT 인증 + 성향 저장/조회  
Spring Boot 3 + Kotlin + DDD 구조 + MySQL 기반 백엔드

---

## 프로젝트 개요

이 프로젝트는 **사용자 인증 + 성격 테스트 결과 저장**을 위한 최소 백엔드(MVP)입니다.  
React 프론트엔드와 연동되며, JWT 기반 인증 구조를 사용합니다.

## flowchart TD

%% ───────────────────────────────
%% Styled Nodes
%% ───────────────────────────────
classDef page fill:#F7F9FC,stroke:#CBD5E1,stroke-width:1px,color:#1E293B,rx:8px,ry:8px;
classDef action fill:#EEF2FF,stroke:#6366F1,stroke-width:1px,color:#1E1B4B,rx:8px,ry:8px;
classDef result fill:#ECFDF5,stroke:#34D399,stroke-width:1px,color:#065F46,rx:8px,ry:8px;
classDef error fill:#FEF2F2,stroke:#F87171,color:#7F1D1D,rx:8px,ry:8px;


%% ───────────────────────────────
%% Flow
%% ───────────────────────────────
A[사용자 웹 접속]:::page --> B[회원가입 페이지]:::page
B -->|회원가입 완료| C[로그인 페이지]:::page

C -->|이메일/비밀번호 입력| D[로그인 요청]:::action
D -->|JWT 발급| E[성격 테스트 시작]:::page

E --> F[MBTI 질문 진행]:::action
F --> G[질문 모두 응답 완료]:::action

G --> H[MBTI 결과 계산]:::result
H --> I[결과 페이지 표시]:::page

I -->|결과 저장 클릭| J[JWT 포함하여 서버 저장 요청]:::action
J --> K[User.personalityJson 업데이트]:::result

K --> L[저장 완료 메시지]:::result --> M[메인/마이페이지 이동]:::page

C -->|비로그인 상태로 접근| X[권한 없음 → 로그인 이동]:::error

M --> N[내 성향 보기]:::page
N -->|서버 요청| O[저장된 성향 JSON 반환]:::result
O --> P[MBTI 결과 화면 표시]:::page


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

- [x] POST /api/auth/signup
- [x] SignupRequest(email, password) DTO 생성
- [x] User 엔티티(email, password) 저장
- [x] 비밀번호 bcrypt 암호화
- [x] 이메일 중복 체크
- [x] 성공 시 200 OK

### 1-2. 로그인 (Login)

- [x] POST /api/auth/login
- [x] LoginRequest DTO 생성
- [x] 이메일 기반 사용자 조회
- [x] bcrypt 비밀번호 검증
- [x] 실패 시 INVALID_LOGIN 에러 반환
- [x] JWT 토큰 발급
- [x] TokenResponse(token) 반환

---

## 2. Security (JWT 인증)

### 2-1. JWT Provider

- [x] HS256 기반 secret key
- [x] userId를 payload에 저장
- [x] 만료시간 7일
- [x] createToken(userId) 구현

### 2-2. JWT Filter

- [x] Authorization: Bearer {token} 파싱
- [x] JWT 검증 후 userId 추출
- [x] UserPrincipal 생성
- [x] SecurityContextHolder에 인증 저장

### 2-3. SecurityConfig

- [x] /api/auth/** → permitAll
- [x] /api/users/** → 인증 필요
- [x] 세션 STATELESS
- [x] CORS 허용

---

## 3. User (성향 저장 & 조회)

### 3-1. 성향 저장 (Save Personality)

- [x] POST /api/users/personality
- [x] JWT 인증 필요
- [x] PersonalityRequest(personalityJson) DTO 정의
- [x] user.personalityJson 필드 업데이트
- [x] 저장 후 200 OK

### 3-2. 성향 조회 (Get Personality)

- [x] GET /api/users/personality
- [x] JWT 인증 필요
- [x] userId 기반 조회
- [x] personalityJson 반환

---

## 4. Domain Model

### User Entity

- [x] id: Long  
- [x] email: String (unique)  
- [x] password: String (암호화 저장)  
- [x] personalityJson: TEXT  

### ✔ UserRepository

- [x] existsByEmail()
- [x] findByEmail()
- [x] findById()

---

## 🚨 5. Global Error Handling

### ✔ ErrorCode Enum

- [x] EMAIL_ALREADY_EXISTS
- [x] INVALID_LOGIN
- [x] USER_NOT_FOUND
- [x] UNAUTHORIZED
- [x] INTERNAL_SERVER_ERROR

### ✔ CustomException

- [x] ErrorCode 기반 공통 예외 처리

---
