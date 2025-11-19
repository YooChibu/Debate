# Argu → Debate 복사 누락 및 텍스트 변경 분석 보고서

## 📋 목차
1. [누락된 파일 및 디렉토리](#누락된-파일-및-디렉토리)
2. [텍스트 변경이 필요한 부분](#텍스트-변경이-필요한-부분)
3. [문제가 될 수 있는 부분](#문제가-될-수-있는-부분)
4. [복사 및 변경 작업 체크리스트](#복사-및-변경-작업-체크리스트)

---

## 누락된 파일 및 디렉토리

### 1. 루트 디렉토리 파일

#### ❌ 완전히 누락된 파일
- `README.md` - 프로젝트 설명서
- `PROJECT_ANALYSIS.md` - 상세 분석 보고서
- `README-Admin.md` - 관리자 가이드
- `README-Database.md` - 데이터베이스 가이드
- `insert_categories.sql` - 카테고리 초기 데이터

#### ⚠️ 부분적으로만 존재
- `PROJECT_COMPARISON_ANALYSIS.md` - 비교 분석 보고서 (있음)

### 2. DebateUserFrontEnd 누락 파일

#### ❌ 완전히 누락된 파일

**컴포넌트 (components/)**
- `components/common/Footer.jsx` 및 `Footer.css`
- `components/common/Button.jsx` 및 `Button.css`
- `components/common/ImageUploadModal.jsx` 및 `ImageUploadModal.css`
- `components/common/Layout.jsx`
- `components/common/ProtectedRoute.jsx`

**페이지 (pages/)**
- `pages/HomePage.jsx` 및 `HomePage.css`
- `pages/auth/LoginPage.jsx` 및 `pages/auth/Auth.css`
- `pages/auth/RegisterPage.jsx`
- `pages/CategoryListPage.jsx` 및 `CategoryListPage.css`
- `pages/CategoryDetailPage.jsx` 및 `CategoryDetailPage.css`
- `pages/SearchPage.jsx` 및 `SearchPage.css`
- `pages/UserProfilePage.jsx` 및 `UserProfilePage.css`
- `pages/MyPage.jsx` 및 `MyPage.css`
- `pages/MyPageEdit.jsx` 및 `MyPageEdit.css`
- `pages/MyPageSettings.jsx` 및 `MyPageSettings.css`

**컨텍스트 (context/)**
- `context/AuthContext.jsx`
- `context/ThemeContext.jsx`

**서비스 (services/)**
- `services/api.js` - Axios 인스턴스 설정
- `services/authService.js` - 인증 API 서비스
- `services/categoryService.js` - 카테고리 API 서비스
- `services/userService.js` - 사용자 API 서비스
- `services/reportService.js` - 신고 API 서비스
- `services/fileUploadService.js` - 파일 업로드 서비스

**유틸리티 (utils/)**
- `utils/quillConfig.js` - Quill 에디터 설정

**기타**
- `main.jsx` - React 진입점
- `styles/index.css` - 전역 스타일
- `index.html` - HTML 진입점
- `vite.config.js` - Vite 설정
- `public/images/` - 이미지 파일들

### 3. DebateAdminBackEnd 누락 파일

#### ❌ 완전히 누락된 파일 (52개 Java 파일)

**설정 (config/)**
- `config/SecurityConfig.java`
- `config/JwtProperties.java`
- `config/OpenApiConfig.java`
- `config/WebConfig.java`

**컨트롤러 (controller/)**
- `controller/AdminAuthController.java`
- `controller/AdminArguController.java` → `AdminDebateController.java`로 변경 필요
- `controller/AdminUserController.java`
- `controller/AdminCommentController.java`
- `controller/AdminCategoryController.java`
- `controller/AdminReportController.java`
- `controller/AdminStatisticsController.java`
- `controller/AdminDashboardController.java`
- `controller/AdminManagementController.java`
- `controller/FileUploadController.java`

**서비스 (service/)**
- `service/AdminAuthService.java`
- `service/AdminArguService.java` → `AdminDebateService.java`로 변경 필요
- `service/AdminUserService.java`
- `service/AdminCommentService.java`
- `service/AdminCategoryService.java`
- `service/AdminReportService.java`
- `service/AdminStatisticsService.java`
- `service/AdminDashboardService.java`
- `service/AdminManagementService.java`

**리포지토리 (repository/)**
- `repository/AdminRepository.java`
- `repository/ArguRepository.java` → `DebateRepository.java`로 변경 필요
- `repository/UserRepository.java`
- `repository/CommentRepository.java`
- `repository/CategoryRepository.java`
- `repository/LikeRepository.java`
- `repository/ReportRepository.java`

**엔티티 (entity/)**
- `entity/Admin.java`
- `entity/Argu.java` → `Debate.java`로 변경 필요
- `entity/User.java`
- `entity/Comment.java`
- `entity/Category.java`
- `entity/Like.java`
- `entity/Report.java`

**DTO (dto/)**
- `dto/request/AdminLoginRequest.java`
- `dto/request/CreateAdminRequest.java`
- `dto/response/AdminAuthResponse.java`
- `dto/response/DashboardStatsResponse.java`
- `dto/response/UserDetailResponse.java`
- `dto/response/ApiResponse.java`

**보안 (security/)**
- `security/CustomAdminDetailsService.java`
- `security/JwtAuthenticationFilter.java`

**유틸리티 (util/)**
- `util/JwtUtil.java`
- `util/SecurityUtil.java`

**예외 처리 (exception/)**
- `exception/BadRequestException.java`
- `exception/ResourceNotFoundException.java`
- `exception/UnauthorizedException.java`
- `exception/GlobalExceptionHandler.java`

**기타**
- `build.gradle` - 이미 있지만 내용 확인 필요
- `gradle/wrapper/` - Gradle 래퍼 파일들
- `gradlew`, `gradlew.bat` - Gradle 실행 스크립트

### 4. DebateAdminFrontEnd - 완전히 누락

#### ❌ 전체 디렉토리 누락
- `DebateAdminFrontEnd/` 전체 디렉토리

**주요 파일들:**
- `src/App.jsx`
- `src/main.jsx`
- `src/pages/` (12개 페이지)
  - `LoginPage.jsx`
  - `DashboardPage.jsx`
  - `UsersPage.jsx`
  - `ArguPage.jsx` → `DebatePage.jsx`로 변경 필요
  - `CommentsPage.jsx`
  - `CategoriesPage.jsx`
  - `ReportsPage.jsx`
  - `StatisticsPage.jsx`
  - `SettingsPage.jsx`
  - `AdminsPage.jsx`
- `src/components/common/` (3개 컴포넌트)
- `src/services/` (10개 서비스 파일)
- `src/context/` (2개 컨텍스트)
- `src/utils/quillConfig.js`
- `package.json`
- `vite.config.js`
- `index.html`
- `public/images/ARGU.png` → `DEBATE.png`로 변경 필요

### 5. 추가 리소스 디렉토리

#### ❌ 완전히 누락된 디렉토리
- `Files/editor/images/` - 파일 업로드 저장 디렉토리
- `mockup/` - 디자인 목업 파일들
  - `mockup/admin/` - 관리자 페이지 목업
  - `mockup/user/` - 사용자 페이지 목업

### 6. DebateUserBackEnd 추가 파일

#### ⚠️ 확인 필요한 파일
- `add_table_comments.sql` - 테이블 주석 추가 스크립트
- `insert_categories.sql` - 카테고리 초기 데이터
- `remove_username_column.sql` - 마이그레이션 스크립트
- `README-CURSOR.md` - Cursor 관련 문서
- `README-GRADLE.md` - Gradle 관련 문서
- `settings.gradle` - Gradle 설정
- `yarn-setup.bat`, `yarn-setup.ps1` - Yarn 설정 스크립트

---

## 텍스트 변경이 필요한 부분

### 1. Java 파일 내 텍스트 변경

#### 패키지명 변경
- `com.argu` → `com.debate` (모든 Java 파일)
- `com.argu.entity.Argu` → `com.debate.entity.Debate`
- `com.argu.entity.ArguOpinion` → `com.debate.entity.DebateOpinion`

#### 클래스명 변경
- `Argu` → `Debate`
- `ArguOpinion` → `DebateOpinion`
- `ArguStatus` → `DebateStatus`
- `ArguController` → `DebateController`
- `ArguService` → `DebateService`
- `ArguRepository` → `DebateRepository`
- `ArguResponse` → `DebateResponse`
- `CreateArguRequest` → `CreateDebateRequest`
- `UpdateArguRequest` → `UpdateDebateRequest`

#### 주석 및 문자열 변경
- "논쟁" → "토론" (모든 주석, 로그 메시지)
- "argu" → "debate" (URL 경로, 변수명)
- "Argu" → "Debate" (표시명, 설명)

### 2. JavaScript/JSX 파일 내 텍스트 변경

#### 파일명 변경
- `arguService.js` → `debateService.js`
- `ArguCard.jsx` → `DebateCard.jsx`
- `ArguListPage.jsx` → `DebateListPage.jsx`
- `ArguDetailPage.jsx` → `DebateDetailPage.jsx`
- `ArguCreatePage.jsx` → `DebateCreatePage.jsx`
- `ArguEditPage.jsx` → `DebateEditPage.jsx`

#### 코드 내 변경
- `argu` → `debate` (변수명, 함수명, API 경로)
- `Argu` → `Debate` (컴포넌트명, 표시명)
- "논쟁" → "토론" (모든 사용자에게 보이는 텍스트)
- `/argu` → `/debate` (라우트 경로)
- `arguService` → `debateService`

### 3. 설정 파일 내 텍스트 변경

#### application.yml
- `argu-user` → `debate-user`
- `argu_db` → `debate_db`
- `argu_web` → `debate_web`
- `argu-secret-key` → `debate-secret-key`
- "논쟁 플랫폼" → "토론 플랫폼"

#### package.json
- `argu-user-frontend` → `debate-user-frontend`
- `argu-admin-frontend` → `debate-admin-frontend`
- "논쟁 플랫폼" → "토론 플랫폼"

#### build.gradle
- `com.argu` → `com.debate`
- "논쟁 플랫폼" → "토론 플랫폼"

### 4. 데이터베이스 관련

#### SQL 파일
- 테이블명: `argu` → `debate`
- 테이블명: `argu_opinion` → `debate_opinion`
- 데이터베이스명: `argu_db` → `debate_db`
- 사용자명: `argu_web` → `debate_web`
- "논쟁" → "토론" (주석, 설명)

### 5. 문서 파일

#### README.md
- "논쟁" → "토론" (모든 텍스트)
- "Argu" → "Debate" (프로젝트명)
- "argu" → "debate" (경로, 명령어)

#### 기타 문서
- `PROJECT_ANALYSIS.md` → `PROJECT_ANALYSIS.md` (내용 변경)
- `README-Admin.md` → `README-Admin.md` (내용 변경)

### 6. 이미지 파일

#### 파일명 변경
- `ARGU.png` → `DEBATE.png`
- 경로 내 `argu` → `debate`

---

## 문제가 될 수 있는 부분

### 1. App.jsx에서 import하는 파일이 존재하지 않음

**DebateUserFrontEnd/src/App.jsx**에서 다음 파일들을 import하지만 실제로는 존재하지 않음:

```javascript
// ❌ 존재하지 않는 파일들
import { AuthProvider } from './context/AuthContext'  // 파일 없음
import { ThemeProvider } from './context/ThemeContext'  // 파일 없음
import Layout from './components/common/Layout'  // 파일 없음
import HomePage from './pages/HomePage'  // 파일 없음
import LoginPage from './pages/auth/LoginPage'  // 파일 없음
import RegisterPage from './pages/auth/RegisterPage'  // 파일 없음
import MyPage from './pages/MyPage'  // 파일 없음
import MyPageEdit from './pages/MyPageEdit'  // 파일 없음
import MyPageSettings from './pages/MyPageSettings'  // 파일 없음
import UserProfilePage from './pages/UserProfilePage'  // 파일 없음
import CategoryListPage from './pages/CategoryListPage'  // 파일 없음
import CategoryDetailPage from './pages/CategoryDetailPage'  // 파일 없음
import SearchPage from './pages/SearchPage'  // 파일 없음
import ProtectedRoute from './components/common/ProtectedRoute'  // 파일 없음
```

**결과**: 애플리케이션이 실행되지 않음

### 2. 서비스 파일 누락

**DebateUserFrontEnd/src/services/**에 다음 파일들이 없음:
- `api.js` - Axios 인스턴스 설정 없으면 모든 API 호출 실패
- `authService.js` - 로그인/회원가입 불가능
- `categoryService.js` - 카테고리 기능 불가능
- `userService.js` - 사용자 프로필 기능 불가능
- `reportService.js` - 신고 기능 불가능
- `fileUploadService.js` - 파일 업로드 불가능

### 3. 관리자 애플리케이션 미구현

**DebateAdminBackEnd**는 기본 구조만 있고 실제 기능이 없음:
- 컨트롤러, 서비스, 리포지토리 모두 누락
- 관리자 인증 불가능
- 관리 기능 전부 미구현

**DebateAdminFrontEnd**는 완전히 없음:
- 관리자 페이지 접근 불가능

### 4. 데이터베이스 설정 불일치

**application.yml**에서:
- 데이터베이스명: `debate_db` (변경됨) ✅
- 사용자명: `debate_web` (변경됨) ✅
- 하지만 실제 데이터베이스가 생성되어 있는지 확인 필요

### 5. 파일 업로드 경로 문제

**application.yml**에서:
```yaml
file:
  upload-dir: ../../Files/editor/images
```

- `Files/` 디렉토리가 Debate 프로젝트에 없음
- 파일 업로드 시 오류 발생 가능

### 6. 빌드 파일 누락

**DebateAdminBackEnd**에 다음 파일들이 없음:
- `gradle/wrapper/` 디렉토리
- `gradlew`, `gradlew.bat`
- 빌드 및 실행 불가능

### 7. 의존성 문제

**package.json**은 있지만:
- `node_modules/` 디렉토리 확인 필요
- `yarn.lock` 또는 `package-lock.json` 확인 필요

### 8. 텍스트 변경 누락

다음 부분들에서 "argu"/"Argu"/"논쟁" 텍스트가 남아있을 수 있음:
- 주석
- 로그 메시지
- 에러 메시지
- 사용자에게 보이는 UI 텍스트
- API 응답 메시지

---

## 복사 및 변경 작업 체크리스트

### Phase 1: 필수 파일 복사

#### DebateUserFrontEnd
- [ ] `src/main.jsx` 복사
- [ ] `src/styles/index.css` 복사
- [ ] `src/context/AuthContext.jsx` 복사 및 텍스트 변경
- [ ] `src/context/ThemeContext.jsx` 복사 및 텍스트 변경
- [ ] `src/components/common/Layout.jsx` 복사 및 텍스트 변경
- [ ] `src/components/common/Footer.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/components/common/Button.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/components/common/ProtectedRoute.jsx` 복사 및 텍스트 변경
- [ ] `src/components/common/ImageUploadModal.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/HomePage.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/auth/LoginPage.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/auth/RegisterPage.jsx` 복사 및 텍스트 변경
- [ ] `src/pages/CategoryListPage.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/CategoryDetailPage.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/SearchPage.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/UserProfilePage.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/MyPage.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/MyPageEdit.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/pages/MyPageSettings.jsx` 및 CSS 복사 및 텍스트 변경
- [ ] `src/services/api.js` 복사 및 텍스트 변경
- [ ] `src/services/authService.js` 복사 및 텍스트 변경
- [ ] `src/services/categoryService.js` 복사 및 텍스트 변경
- [ ] `src/services/userService.js` 복사 및 텍스트 변경
- [ ] `src/services/reportService.js` 복사 및 텍스트 변경
- [ ] `src/services/fileUploadService.js` 복사 및 텍스트 변경
- [ ] `src/utils/quillConfig.js` 복사 및 텍스트 변경
- [ ] `index.html` 복사 및 텍스트 변경
- [ ] `vite.config.js` 복사 및 텍스트 변경

#### DebateAdminBackEnd
- [ ] 모든 Java 파일 복사 (52개)
- [ ] `build.gradle` 내용 확인 및 수정
- [ ] `gradle/wrapper/` 디렉토리 복사
- [ ] `gradlew`, `gradlew.bat` 복사

#### DebateAdminFrontEnd
- [ ] 전체 디렉토리 복사
- [ ] 모든 파일 텍스트 변경

### Phase 2: 텍스트 일괄 변경

#### Java 파일
- [ ] `com.argu` → `com.debate` (모든 파일)
- [ ] `Argu` → `Debate` (클래스명, 변수명)
- [ ] `argu` → `debate` (변수명, 메서드명)
- [ ] "논쟁" → "토론" (주석, 문자열)

#### JavaScript/JSX 파일
- [ ] `argu` → `debate` (변수명, 함수명, 경로)
- [ ] `Argu` → `Debate` (컴포넌트명)
- [ ] "논쟁" → "토론" (UI 텍스트)
- [ ] `/argu` → `/debate` (라우트)

#### 설정 파일
- [ ] `application.yml` - 모든 참조 변경
- [ ] `package.json` - 이름 및 설명 변경
- [ ] `build.gradle` - 그룹 ID 및 설명 변경

#### SQL 파일
- [ ] 테이블명 변경
- [ ] 데이터베이스명 변경
- [ ] 주석 변경

### Phase 3: 추가 리소스

- [ ] `Files/editor/images/` 디렉토리 생성
- [ ] `mockup/` 디렉토리 복사 (선택사항)
- [ ] 루트 README 파일들 복사 및 텍스트 변경

### Phase 4: 검증

- [ ] 모든 import 경로 확인
- [ ] 빌드 테스트 (백엔드)
- [ ] 빌드 테스트 (프론트엔드)
- [ ] 실행 테스트
- [ ] 데이터베이스 연결 테스트
- [ ] API 엔드포인트 테스트
- [ ] UI 텍스트 확인

---

## 우선순위별 작업 순서

### 🔴 긴급 (애플리케이션 실행 불가)
1. DebateUserFrontEnd 필수 파일 복사
   - `main.jsx`
   - `context/AuthContext.jsx`, `ThemeContext.jsx`
   - `components/common/Layout.jsx`, `ProtectedRoute.jsx`
   - `services/api.js`
2. App.jsx에서 import하는 모든 페이지 파일 복사

### 🟡 중요 (기능 동작 불가)
3. 나머지 서비스 파일 복사
4. DebateAdminBackEnd 전체 복사
5. DebateAdminFrontEnd 전체 복사

### 🟢 개선 (사용자 경험)
6. 추가 컴포넌트 복사 (Footer, Button 등)
7. 문서 파일 복사 및 텍스트 변경
8. 추가 리소스 디렉토리 생성

---

**작성일**: 2025년 1월
**분석 대상**: D:\vs\Argu → D:\vs\Debate 복사 상태

