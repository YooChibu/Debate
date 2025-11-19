# 토론 사이트 (Debate) 데이터베이스 설계

토론(debate) 플랫폼 데이터베이스 설계 문서입니다...

## 📋 목차

- [데이터베이스 개요](#데이터베이스-개요)
- [ERD 다이어그램](#erd-다이어그램)
- [테이블 구조](#테이블-구조)
- [인덱스 설계](#인덱스-설계)
- [관계 설정](#관계-설정)

## 데이터베이스 개요

- **데이터베이스 시스템**: MySQL
- **ORM**: JPA (Java Persistence API)
- **문자 인코딩**: UTF-8 (utf8mb4)

## ERD 다이어그램

```
┌─────────────┐         ┌─────────────┐
│    users    │         │    admins   │
├─────────────┤         ├─────────────┤
│ id (PK)     │         │ id (PK)     │
│ email       │         │ admin_id    │
│ password    │         │ password    │
│ nickname    │         │ role        │
│ profile_img │         │ created_at  │
│ status      │         │ status      │
│ created_at  │         └─────────────┘
│ updated_at  │                │
└─────────────┘                │ 1:N
      │                        │
      │ 1:N                    │
      │                        ▼
      │                 ┌─────────────┐
      │                 │   reports   │
      │                 ├─────────────┤
      │                 │ id (PK)     │
      │                 │ reporter_id │
      │                 │ target_type │
      │                 │ target_id   │
      │                 │ reason      │
      │                 │ status      │
      │                 │ processed_by│
      │                 │ created_at  │
      │                 │ processed_at│
      │                 └─────────────┘
      │
      ├─────────────────────────────────────────────────────────────────┐
      │                                                                 │
      ▼                                                                 │
┌─────────────┐                                                         │
│ categories  │                                                         │
├─────────────┤                                                         │
│ id (PK)     │                                                         │
│ name        │                                                         │
│ order_num   │                                                         │
│ created_at  │                                                         │
└─────────────┘                                                         │
      │                                                                 │
      │ 1:N                                                            │
      │                                                                 │
      ▼                                                                 │
┌─────────────┐                                                         │
│    debate     │                                                         │
├─────────────┤                                                         │
│ id (PK)     │                                                         │
│ user_id (FK)│                                                         │
│ category_id │                                                         │
│ title       │                                                         │
│ content     │                                                         │
│ start_date  │                                                         │
│ end_date    │                                                         │
│ status      │                                                         │
│ is_hidden   │                                                         │
│ created_at  │                                                         │
│ updated_at  │                                                         │
└─────────────┘                                                         │
      │                                                                 │
      │ 1:N                                                            │
      │                                                                 │
      ├─────────────────┬─────────────────┬─────────────────┬─────────────────┐
      │                 │                 │                 │                 │
      ▼                 ▼                 ▼                 ▼                 ▼
┌─────────────┐   ┌─────────────┐   ┌─────────────┐   ┌─────────────┐   ┌─────────────┐
│debate_opinion │   │   comments  │   │    likes    │   │  bookmarks  │   │chat_messages│
├─────────────┤   ├─────────────┤   ├─────────────┤   ├─────────────┤   ├─────────────┤
│ id (PK)     │   │ id (PK)     │   │ id (PK)     │   │ id (PK)     │   │ id (PK)     │
│ debate_id (FK)│   │ debate_id (FK)│   │ debate_id (FK)│   │ debate_id (FK)│   │ debate_id (FK)│
│ user_id (FK)│   │ user_id (FK)│   │ user_id (FK)│   │ user_id (FK)│   │ user_id (FK)│
│ side        │   │ parent_id   │   │ created_at  │   │ created_at  │   │ message     │
│ content     │   │ content     │   └─────────────┘   └─────────────┘   │ created_at  │
│ created_at  │   │ is_hidden   │                                       └─────────────┘
│ updated_at  │   │ created_at  │
└─────────────┘   │ updated_at  │
                  └─────────────┘
                          │
                          │ 1:N (self-reference)
                          │
                          ▼
                  ┌─────────────┐
                  │   comments  │
                  │ (대댓글)     │
                  └─────────────┘
```

## 테이블 구조

### 1. users (회원 테이블)

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '회원 고유 ID',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT '이메일 주소',
    password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호',
    nickname VARCHAR(50) NOT NULL COMMENT '닉네임',
    profile_image VARCHAR(500) COMMENT '프로필 이미지 URL',
    bio TEXT COMMENT '자기소개',
    status ENUM('ACTIVE', 'SUSPENDED', 'DELETED') DEFAULT 'ACTIVE' COMMENT '회원 상태 (ACTIVE: 정상, SUSPENDED: 정지, DELETED: 탈퇴)',
    email_verified BOOLEAN DEFAULT FALSE COMMENT '이메일 인증 여부',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    INDEX idx_email (email) COMMENT '이메일 검색 인덱스',
    INDEX idx_status (status) COMMENT '회원 상태 인덱스',
    INDEX idx_created_at (created_at) COMMENT '가입일시 정렬 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원 테이블';
```

**컬럼 설명:**

- `id`: 회원 고유 ID (Primary Key)
- `email`: 이메일 주소 (Unique)
- `password`: 암호화된 비밀번호
- `nickname`: 닉네임
- `profile_image`: 프로필 이미지 URL
- `bio`: 자기소개
- `status`: 회원 상태 (ACTIVE: 정상, SUSPENDED: 정지, DELETED: 탈퇴)
- `email_verified`: 이메일 인증 여부
- `created_at`: 가입일시
- `updated_at`: 수정일시

### 2. admins (관리자 테이블)

```sql
CREATE TABLE admins (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '관리자 고유 ID',
    admin_id VARCHAR(50) NOT NULL UNIQUE COMMENT '관리자 아이디',
    password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호',
    name VARCHAR(50) NOT NULL COMMENT '관리자 이름',
    role ENUM('SUPER_ADMIN', 'ADMIN') DEFAULT 'ADMIN' COMMENT '관리자 권한 (SUPER_ADMIN: 슈퍼 관리자, ADMIN: 일반 관리자)',
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE' COMMENT '관리자 상태 (ACTIVE: 활성, INACTIVE: 비활성)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    INDEX idx_admin_id (admin_id) COMMENT '관리자 아이디 검색 인덱스',
    INDEX idx_role (role) COMMENT '권한별 조회 인덱스',
    INDEX idx_status (status) COMMENT '상태별 조회 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='관리자 테이블';
```

**컬럼 설명:**

- `id`: 관리자 고유 ID (Primary Key)
- `admin_id`: 관리자 아이디 (Unique)
- `password`: 암호화된 비밀번호
- `name`: 관리자 이름
- `role`: 관리자 권한 (SUPER_ADMIN: 슈퍼 관리자, ADMIN: 일반 관리자)
- `status`: 관리자 상태
- `created_at`: 생성일시
- `updated_at`: 수정일시

### 3. categories (카테고리 테이블)

```sql
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '카테고리 고유 ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '카테고리 이름',
    description TEXT COMMENT '카테고리 설명',
    order_num INT DEFAULT 0 COMMENT '표시 순서',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    INDEX idx_order_num (order_num) COMMENT '순서 정렬 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='카테고리 테이블';
```

**컬럼 설명:**

- `id`: 카테고리 고유 ID (Primary Key)
- `name`: 카테고리 이름 (Unique)
- `description`: 카테고리 설명
- `order_num`: 표시 순서
- `created_at`: 생성일시
- `updated_at`: 수정일시

### 4. debate (토론 테이블)

```sql
CREATE TABLE debate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '토론 고유 ID',
    user_id BIGINT NOT NULL COMMENT '작성자 ID',
    category_id BIGINT NOT NULL COMMENT '카테고리 ID',
    title VARCHAR(255) NOT NULL COMMENT '토론 제목',
    content TEXT NOT NULL COMMENT '토론 내용',
    start_date DATETIME NOT NULL COMMENT '토론 시작일시',
    end_date DATETIME NOT NULL COMMENT '토론 종료일시',
    status ENUM('SCHEDULED', 'ACTIVE', 'ENDED') DEFAULT 'SCHEDULED' COMMENT '토론 상태 (SCHEDULED: 예정, ACTIVE: 진행중, ENDED: 종료)',
    is_hidden BOOLEAN DEFAULT FALSE COMMENT '숨김 처리 여부',
    view_count INT DEFAULT 0 COMMENT '조회수',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE COMMENT '작성자 외래키',
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT COMMENT '카테고리 외래키',

    INDEX idx_user_id (user_id) COMMENT '작성자별 조회 인덱스',
    INDEX idx_category_id (category_id) COMMENT '카테고리별 조회 인덱스',
    INDEX idx_status (status) COMMENT '상태별 조회 인덱스',
    INDEX idx_start_date (start_date) COMMENT '시작일시 인덱스',
    INDEX idx_end_date (end_date) COMMENT '종료일시 인덱스',
    INDEX idx_created_at (created_at) COMMENT '생성일시 정렬 인덱스',
    FULLTEXT INDEX idx_title_content (title, content) COMMENT '제목/내용 전문 검색 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='토론 테이블';
```

**컬럼 설명:**

- `id`: 토론 고유 ID (Primary Key)
- `user_id`: 작성자 ID (Foreign Key → users.id)
- `category_id`: 카테고리 ID (Foreign Key → categories.id)
- `title`: 토론 제목
- `content`: 토론 내용
- `start_date`: 토론 시작일시
- `end_date`: 토론 종료일시
- `status`: 토론 상태 (SCHEDULED: 예정, ACTIVE: 진행중, ENDED: 종료)
- `is_hidden`: 숨김 처리 여부
- `view_count`: 조회수
- `created_at`: 생성일시
- `updated_at`: 수정일시

### 5. debate_opinion (입장별 의견 테이블)

```sql
CREATE TABLE debate_opinion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '의견 고유 ID',
    debate_id BIGINT NOT NULL COMMENT '토론 ID',
    user_id BIGINT NOT NULL COMMENT '작성자 ID',
    side ENUM('FOR', 'AGAINST', 'NEUTRAL', 'OTHER') NOT NULL COMMENT '입장 (FOR: 찬성, AGAINST: 반대, NEUTRAL: 중립, OTHER: 기타)',
    content TEXT COMMENT '의견 내용 (선택사항, 작성하지 않아도 됨)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    FOREIGN KEY (debate_id) REFERENCES debate(id) ON DELETE CASCADE COMMENT '토론 외래키',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE COMMENT '작성자 외래키',

    UNIQUE KEY uk_debate_user (debate_id, user_id) COMMENT '한 사용자는 하나의 토론에 대해 하나의 입장만 선택 가능',
    INDEX idx_debate_id (debate_id) COMMENT '토론별 조회 인덱스',
    INDEX idx_user_id (user_id) COMMENT '작성자별 조회 인덱스',
    INDEX idx_side (side) COMMENT '입장별 조회 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='입장 선택 및 의견 테이블 (투표와 의견 통합)';
```

**컬럼 설명:**

- `id`: 의견 고유 ID (Primary Key)
- `debate_id`: 토론 ID (Foreign Key → debate.id)
- `user_id`: 작성자 ID (Foreign Key → users.id)
- `side`: 입장 (FOR: 찬성, AGAINST: 반대, NEUTRAL: 중립, OTHER: 기타)
- `content`: 의견 내용 (선택사항, NULL 허용)
- `created_at`: 작성일시
- `updated_at`: 수정일시

**제약조건:**

- 한 사용자는 하나의 토론에 대해 하나의 입장만 선택 가능 (UNIQUE: debate_id, user_id)
- 찬성, 반대, 중립, 기타 중 하나만 선택 가능
- 의견 내용(content)은 선택사항으로 작성하지 않아도 됨

### 6. comments (댓글 테이블)

```sql
CREATE TABLE comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '댓글 고유 ID',
    user_id BIGINT NOT NULL COMMENT '작성자 ID',
    debate_id BIGINT NOT NULL COMMENT '토론 ID',
    parent_id BIGINT NULL COMMENT '부모 댓글 ID (대댓글인 경우, NULL이면 일반 댓글)',
    content TEXT NOT NULL COMMENT '댓글 내용',
    is_hidden BOOLEAN DEFAULT FALSE COMMENT '숨김 처리 여부',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE COMMENT '작성자 외래키',
    FOREIGN KEY (debate_id) REFERENCES debate(id) ON DELETE CASCADE COMMENT '토론 외래키',
    FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE CASCADE COMMENT '부모 댓글 외래키 (자기 참조)',

    INDEX idx_user_id (user_id) COMMENT '작성자별 조회 인덱스',
    INDEX idx_debate_id (debate_id) COMMENT '토론별 조회 인덱스',
    INDEX idx_parent_id (parent_id) COMMENT '부모 댓글별 조회 인덱스',
    INDEX idx_created_at (created_at) COMMENT '작성일시 정렬 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='댓글 테이블 (대댓글 지원)';
```

**컬럼 설명:**

- `id`: 댓글 고유 ID (Primary Key)
- `user_id`: 작성자 ID (Foreign Key → users.id)
- `debate_id`: 토론 ID (Foreign Key → debate.id)
- `parent_id`: 부모 댓글 ID (대댓글인 경우, Foreign Key → comments.id)
- `content`: 댓글 내용
- `is_hidden`: 숨김 처리 여부
- `created_at`: 작성일시
- `updated_at`: 수정일시

**특징:**

- `parent_id`가 NULL이면 일반 댓글, 값이 있으면 대댓글

### 7. likes (좋아요 테이블)

```sql
CREATE TABLE likes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '좋아요 고유 ID',
    debate_id BIGINT NOT NULL COMMENT '토론 ID',
    user_id BIGINT NOT NULL COMMENT '좋아요한 사용자 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '좋아요 일시',

    FOREIGN KEY (debate_id) REFERENCES debate(id) ON DELETE CASCADE COMMENT '토론 외래키',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE COMMENT '사용자 외래키',

    UNIQUE KEY uk_debate_user (debate_id, user_id) COMMENT '한 사용자는 하나의 토론에 대해 하나의 좋아요만 가능',
    INDEX idx_debate_id (debate_id) COMMENT '토론별 조회 인덱스',
    INDEX idx_user_id (user_id) COMMENT '사용자별 조회 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='좋아요 테이블';
```

**컬럼 설명:**

- `id`: 좋아요 고유 ID (Primary Key)
- `debate_id`: 토론 ID (Foreign Key → debate.id)
- `user_id`: 좋아요한 사용자 ID (Foreign Key → users.id)
- `created_at`: 좋아요 일시

**제약조건:**

- 한 사용자는 하나의 토론에 대해 하나의 좋아요만 가능 (UNIQUE)

### 8. bookmarks (북마크 테이블)

```sql
CREATE TABLE bookmarks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '북마크 고유 ID',
    debate_id BIGINT NOT NULL COMMENT '토론 ID',
    user_id BIGINT NOT NULL COMMENT '북마크한 사용자 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '북마크 일시',

    FOREIGN KEY (debate_id) REFERENCES debate(id) ON DELETE CASCADE COMMENT '토론 외래키',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE COMMENT '사용자 외래키',

    UNIQUE KEY uk_debate_user (debate_id, user_id) COMMENT '한 사용자는 하나의 토론에 대해 하나의 북마크만 가능',
    INDEX idx_debate_id (debate_id) COMMENT '토론별 조회 인덱스',
    INDEX idx_user_id (user_id) COMMENT '사용자별 조회 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='북마크 테이블';
```

**컬럼 설명:**

- `id`: 북마크 고유 ID (Primary Key)
- `debate_id`: 토론 ID (Foreign Key → debate.id)
- `user_id`: 북마크한 사용자 ID (Foreign Key → users.id)
- `created_at`: 북마크 일시

**제약조건:**

- 한 사용자는 하나의 토론에 대해 하나의 북마크만 가능 (UNIQUE)

### 9. reports (신고 테이블)

```sql
CREATE TABLE reports (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '신고 고유 ID',
    reporter_id BIGINT NOT NULL COMMENT '신고자 ID',
    target_type ENUM('DEBATE', 'COMMENT', 'USER') NOT NULL COMMENT '신고 대상 타입 (DEBATE: 토론, COMMENT: 댓글, USER: 사용자)',
    target_id BIGINT NOT NULL COMMENT '신고 대상 ID',
    reason VARCHAR(255) NOT NULL COMMENT '신고 사유',
    description TEXT COMMENT '신고 상세 설명',
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' COMMENT '처리 상태 (PENDING: 대기중, APPROVED: 승인, REJECTED: 반려)',
    processed_by BIGINT NULL COMMENT '처리한 관리자 ID',
    processed_at DATETIME NULL COMMENT '처리일시',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '신고일시',

    FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE CASCADE COMMENT '신고자 외래키',
    FOREIGN KEY (processed_by) REFERENCES admins(id) ON DELETE SET NULL COMMENT '처리 관리자 외래키',

    INDEX idx_reporter_id (reporter_id) COMMENT '신고자별 조회 인덱스',
    INDEX idx_target (target_type, target_id) COMMENT '신고 대상별 조회 인덱스',
    INDEX idx_status (status) COMMENT '처리 상태별 조회 인덱스',
    INDEX idx_created_at (created_at) COMMENT '신고일시 정렬 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='신고 테이블';
```

**컬럼 설명:**

- `id`: 신고 고유 ID (Primary Key)
- `reporter_id`: 신고자 ID (Foreign Key → users.id)
- `target_type`: 신고 대상 타입 (DEBATE: 토론, COMMENT: 댓글, USER: 사용자)
- `target_id`: 신고 대상 ID
- `reason`: 신고 사유
- `description`: 신고 상세 설명
- `status`: 처리 상태 (PENDING: 대기중, APPROVED: 승인, REJECTED: 반려)
- `processed_by`: 처리한 관리자 ID (Foreign Key → admins.id)
- `processed_at`: 처리일시
- `created_at`: 신고일시

### 10. chat_messages (채팅 메시지 테이블)

```sql
CREATE TABLE chat_messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '메시지 고유 ID',
    debate_id BIGINT NOT NULL COMMENT '토론 ID (토론별 채팅방)',
    user_id BIGINT NOT NULL COMMENT '작성자 ID',
    message TEXT NOT NULL COMMENT '메시지 내용',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일시',

    FOREIGN KEY (debate_id) REFERENCES debate(id) ON DELETE CASCADE COMMENT '토론 외래키',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE COMMENT '작성자 외래키',

    INDEX idx_debate_id (debate_id) COMMENT '토론별 조회 인덱스',
    INDEX idx_user_id (user_id) COMMENT '작성자별 조회 인덱스',
    INDEX idx_created_at (created_at) COMMENT '작성일시 정렬 인덱스'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='채팅 메시지 테이블';
```

**컬럼 설명:**

- `id`: 메시지 고유 ID (Primary Key)
- `debate_id`: 토론 ID (Foreign Key → debate.id)
- `user_id`: 작성자 ID (Foreign Key → users.id)
- `message`: 메시지 내용
- `created_at`: 작성일시

## 인덱스 설계

### 주요 인덱스 전략

1. **Foreign Key 인덱스**: 모든 외래키에 인덱스 생성
2. **검색 필드 인덱스**:
   - `users.email` (로그인, 검색)
   - `debate.title`, `debate.content` (FULLTEXT 인덱스)
3. **정렬 필드 인덱스**:
   - `debate.created_at` (최신순)
   - `debate.start_date`, `debate.end_date` (기간별)
4. **상태 필드 인덱스**:
   - `users.status`, `debate.status`, `reports.status`
5. **복합 인덱스**:
   - `debate_user` (debate_opinion, likes, bookmarks 테이블)

## 관계 설정

### 주요 관계

1. **users → debate**: 1:N (한 사용자는 여러 토론 작성 가능)
2. **users → comments**: 1:N (한 사용자는 여러 댓글 작성 가능)
3. **users → debate_opinion**: 1:N (한 사용자는 여러 토론에 입장 선택 가능)
4. **debate → comments**: 1:N (한 토론에 여러 댓글 가능)
5. **debate → debate_opinion**: 1:N (한 토론에 여러 입장 선택 가능)
6. **comments → comments**: 1:N (대댓글, 자기 참조)
7. **categories → debate**: 1:N (한 카테고리에 여러 토론)
8. **users → reports**: 1:N (한 사용자는 여러 신고 가능)
9. **admins → reports**: 1:N (한 관리자는 여러 신고 처리 가능)

### CASCADE 정책

- **ON DELETE CASCADE**:

  - `debate` 삭제 시 관련 `comments`, `debate_opinion`, `likes`, `bookmarks`, `chat_messages` 자동 삭제
  - `users` 삭제 시 작성한 `debate`, `comments`, `debate_opinion` 등 자동 삭제
  - `comments` 삭제 시 대댓글 자동 삭제

- **ON DELETE RESTRICT**:

  - `categories` 삭제 시 해당 카테고리를 사용하는 토론이 있으면 삭제 불가

- **ON DELETE SET NULL**:
  - `admins` 삭제 시 처리한 신고의 `processed_by`를 NULL로 설정

## 추가 고려사항

### 파티셔닝 (선택사항)

- 대용량 데이터의 경우 `chat_messages` 테이블을 날짜별로 파티셔닝 고려
- `debate` 테이블의 경우 카테고리별 파티셔닝 고려 가능

### 백업 전략

- 정기적인 전체 백업
- 트랜잭션 로그 백업
- 중요 테이블별 증분 백업

### 성능 최적화

- 읽기 전용 쿼리의 경우 Replication을 통한 읽기 분산 고려
- 자주 조회되는 통계는 캐싱 또는 별도 테이블로 관리

---

**마지막 업데이트**: 2025년
