# Project-Mini-Survey

## 📝 설문조사 참여 프로그램

개발 언어: ![Java](https://img.shields.io/badge/java-0B243B.svg?style=for-the-badge&logo=java&logoColor=white) 
        ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

### 🔍 미리 보기
- [프로젝트 소개](#프로젝트-소개)
- [요구사항 정의](#요구사항-정의)
- [기능 목록](#기능-목록)
- [기능 차트](#기능-차트)
- [화면 흐름도](#화면-흐름도)
- [클래스 다이어그램](#클래스-다이어그램)
- [테이블 목록](#테이블-목록)
- [테이블 정의서](#테이블-정의서)
- [ERD 다이어그램](#ERD-다이어그램)
- [현재 진행 상황](#현재-진행-상황)
- [추가해야 할 것](#추가해야-할-것)
- [프로젝트를 하며 느낀점](#프로젝트를-하며-느낀점)


### 프로젝트 소개
- Member Only System의 회원이 로그인하고 설문조사하는 프로그램입니다.
- 관리자는 설문조사를 등록할 수 있습니다.

 ---
 
### 요구사항 정의

1. 회원은 아이디, 이름, 비밀번호, 이메일, 성별, 사진, 생년월일, 가입일자로 구성된다.
2. 회원은 비밀번호 변경, 회원정보 수정, 회원정보 조회 및 탈퇴가 가능하다.
3. 관리자는 비밀번호 입력 후 전체 회원목록을 볼 수 있고,  회원정보의 조회 및 수정과 삭제가 가능하다.
4. 설문은 설문번호, 제목, 항목1, 항목2, 항목1의 득표수, 항목2의 득표수, 시작일, 종료일, 등록일, 수정일로 구성된다.
6. 설문의 등록, 수정 , 삭제는 관리자만 가능하다. 
7. 로그인을 한 회원만이 설문에 참여할 수 있다.
7. 회원은 자신이 참여한 설문을 볼 수 있고 , 재투표는 불가능하다.
8. 설문참여는 설문번호, 참여한 아이디, 선택항목, 설문참여일로 구성된다.

 ---


### 기능 목록

1. 회원 관리
   - 사용자 등록: 
   - 사용자 수정
   - 사용자 삭제
   - 사용자 검색

2. 설문조사 관리
   - 상품 등록
   - 상품 수정
   - 상품 삭제
   - 상품 목록 보기
   - 상품 검색

3. 설문참여 관리
   - 주문 생성
   - 주문 수정
   - 주문 취소
   - 주문 조회

 ---
 
### 기능 차트
![image3](https://github.com/user-attachments/assets/bc5d90a8-c215-4628-b2d3-35a2c8f1d0c4)

### 화면 흐름도
![image1](https://github.com/user-attachments/assets/7c62f4bb-7899-4051-9760-293366fa667c)

### 클래스 다이어그램
![image4](https://github.com/user-attachments/assets/a0defc9f-284b-4d9d-9cd6-3a2d3c4aba03)

 ---
 
### 테이블 목록

1. t_member (회원정보 테이블)
   - mid / 사용자 ID
   - mname / 사용자 이름
   - mpw / 비밀번호
   - email / 이메일
   - gender / 성별
   - 사진 / photo
   - 생일 / birth_date
   - 가입일자 / join_date

2. t_survey (설문등록 테이블)
   - survet_no / 설문번호
   - title / 제목
   - one / 항목1
   - two / 항목2
   - one_cnt / 항목1의 득표수
   - two_cnt / 항목2의 득표수
   - start_date / 시작일
   - end_date / 종료일
   - red_date / 등록일
   - mode_date / 수정일
   
3. t_vote (설문조사 테이블)
   - survey_no / 설문번호
   - id / 참여한 아이디
   - one_two / 선택항목
   - vote_date / 설문참여일

   ---
   
### 테이블 정의서

회원정보 테이블
```java
CREATE TABLE `t_member` (
  `mid` varchar(20) NOT NULL,
  `mname` varchar(20) NOT NULL,
  `mpw` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `gender` char(1) DEFAULT NULL,
  `photo` varchar(50) DEFAULT 'default.png',
  `birth_date` date DEFAULT NULL,
  `join_date` date DEFAULT (curdate()),
  PRIMARY KEY (`mid`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `t_member_chk_1` CHECK ((`gender` in (_utf8mb3'M',_utf8mb3'F')))
) 
```

설문등록 테이블
```java
CREATE TABLE `t_survey` (
  `SURVEY_NO` int NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(100) NOT NULL,
  `ONE` varchar(500) NOT NULL,
  `TWO` varchar(500) NOT NULL,
  `ONE_CNT` int DEFAULT '0',
  `TWO_CNT` int DEFAULT '0',
  `START_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `REG_DATE` date NOT NULL,
  `MOD_DATE` date DEFAULT NULL,
  PRIMARY KEY (`SURVEY_NO`)
) 
```

설문참여 테이블
```java
CREATE TABLE `t_vote` (
  `SURVEY_NO` int NOT NULL,
  `ID` varchar(20) NOT NULL,
  `ONE_TWO` int DEFAULT NULL,
  `VOTE_DATE` date DEFAULT NULL,
  KEY `FK_T_MEMBER` (`ID`),
  KEY `FK_T_SURVEY` (`SURVEY_NO`),
  CONSTRAINT `FK_T_MEMBER` FOREIGN KEY (`ID`) REFERENCES `t_member` (`mid`),
  CONSTRAINT `FK_T_SURVEY` FOREIGN KEY (`SURVEY_NO`) REFERENCES `t_survey` (`SURVEY_NO`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
```

 ---
 

### ERD 다이어그램
 
  ![surveyERD](https://github.com/user-attachments/assets/e02abbcd-f281-4e4e-a7da-b77a7bf08176)


 ---
 
  ### 현재 진행 상황

   ---
   

  ### 추가해야 할 것

   ---
   

  ### 프로젝트를 하며 느낀점
