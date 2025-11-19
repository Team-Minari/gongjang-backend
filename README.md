# 공장 (Gongjang) - 공유 장바구니 플랫폼 Backend

## 1. 프로젝트 소개
다수의 사용자가 실시간으로 장바구니를 공유하고 협업하는 이커머스 플랫폼 '공장'의 백엔드 서버입니다.

## 2. 기술 스택
- **Java**: 21
- **Framework**: Spring Boot 3.x
- **Database**: MySQL 8.0
- **Build Tool**: Gradle

## 3. 시작 가이드 (Getting Started)

### 필수 요구사항
- Java 21 이상 설치
- MySQL 설치 및 `gongjang` 데이터베이스 생성 (`CREATE DATABASE gongjang;`)

### 실행 방법
1. 프로젝트 클론: `git clone [레포지토리 주소]`
2. `application.properties`에서 DB 계정 정보 수정
3. 프로젝트 루트에서 `./gradlew bootRun` 실행