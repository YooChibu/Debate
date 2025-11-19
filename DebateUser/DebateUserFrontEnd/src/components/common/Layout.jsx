/**
 * Layout 컴포넌트
 *
 * 애플리케이션의 공통 레이아웃을 제공하는 컴포넌트입니다.
 * 모든 페이지에 공통으로 적용되는 Header와 Footer를 포함합니다.
 *
 * 주요 기능:
 * - Header 컴포넌트 포함 (네비게이션, 로고, 사용자 메뉴)
 * - Footer 컴포넌트 포함 (사이트 정보, 링크)
 * - 메인 콘텐츠 영역 제공
 *
 * 수정사항:
 * - 고정 헤더 때문에 바디가 가려지는 문제 해결
 * - main 요소에 padding-top: 64px 추가 (헤더 높이)
 */

import Header from "./Header";
import Footer from "./Footer";
import "./Layout.css";

/**
 * Layout 컴포넌트
 *
 * @param {Object} props - 컴포넌트 props
 * @param {React.ReactNode} props.children - 페이지 콘텐츠
 * @returns {JSX.Element} 레이아웃 컴포넌트
 */
const Layout = ({ children }) => {
  return (
    <div className="app">
      {/* 상단 헤더: 로고, 네비게이션, 사용자 메뉴 */}
      <Header />

      {/* 메인 콘텐츠 영역: 각 페이지의 내용이 여기에 표시됨 */}
      {/* padding-top: 64px로 헤더가 내용을 가리지 않도록 함 */}
      <main className="main main-with-header">{children}</main>

      {/* 하단 푸터: 사이트 정보, 링크 */}
      <Footer />
    </div>
  );
};

export default Layout;
