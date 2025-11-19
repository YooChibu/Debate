/**
 * HomePage 컴포넌트
 * 
 * 애플리케이션의 메인 페이지입니다.
 * 
 * 주요 기능:
 * - 통합 검색 기능
 * - 인기 토론 미리보기
 * - 최신 토론 목록
 * - 카테고리별 미리보기
 */

import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { debateService } from '../services/debateService'
import { categoryService } from '../services/categoryService'
import DebateCard from '../components/debate/DebateCard'
import './HomePage.css'

/**
 * HomePage 컴포넌트
 * 
 * @returns {JSX.Element} 홈페이지 컴포넌트
 */
const HomePage = () => {
  const navigate = useNavigate()
  
  // 상태 관리
  const [popularDebates, setPopularDebates] = useState([]) // 인기 토론 목록
  const [latestDebates, setLatestDebates] = useState([]) // 최신 토론 목록
  const [categories, setCategories] = useState([]) // 카테고리 목록
  const [loading, setLoading] = useState(true) // 로딩 상태
  const [searchKeyword, setSearchKeyword] = useState('') // 검색 키워드

  /**
   * 컴포넌트 마운트 시 데이터 로딩
   */
  useEffect(() => {
    fetchData()
  }, [])

  /**
   * 데이터 가져오기
   * 
   * 토론 목록과 카테고리 목록을 병렬로 가져옵니다.
   */
  const fetchData = async () => {
    try {
      setLoading(true)
      // 토론 목록과 카테고리 목록을 병렬로 가져오기
      const [debatesRes, categoriesRes] = await Promise.all([
        debateService.getAllDebates(0, 6), // 최대 6개 토론 가져오기
        categoryService.getAllCategories(),
      ])
      
      // ApiResponse 구조에서 data 추출
      const debatesData = debatesRes.data || debatesRes
      const categoriesData = categoriesRes.data || categoriesRes
      
      // 인기 토론과 최신 토론을 동일한 데이터로 설정 (실제로는 정렬 기준에 따라 다를 수 있음)
      setPopularDebates(debatesData.content || [])
      setLatestDebates(debatesData.content || [])
      setCategories(categoriesData || [])
    } catch (error) {
      console.error('데이터 로딩 실패:', error)
    } finally {
      setLoading(false)
    }
  }

  /**
   * 검색 처리 함수
   * 
   * 검색어를 입력하고 검색 페이지로 이동합니다.
   * 
   * @param {Event} e - 폼 제출 이벤트
   */
  const handleSearch = (e) => {
    e.preventDefault()
    if (searchKeyword.trim()) {
      // 검색 페이지로 이동 (쿼리 파라미터로 검색어 전달)
      window.location.href = `/search?q=${encodeURIComponent(searchKeyword)}`
    }
  }

  if (loading) {
    return <div className="container">로딩 중...</div>
  }

  return (
    <div className="home-page">
      {/* 검색 영역 */}
      <section className="search-section">
        <div className="container">
          <div className="search-container">
            <h1 className="search-title">토론을 검색하고 참여하세요</h1>
            <p className="search-subtitle">
              관심 있는 주제의 토론을 찾아 건설적인 토론에 참여해보세요
            </p>
            <form onSubmit={handleSearch} className="search-form-main">
              <div className="search-box-main">
                <input
                  type="text"
                  value={searchKeyword}
                  onChange={(e) => setSearchKeyword(e.target.value)}
                  placeholder="토론 제목, 내용, 작성자, 카테고리로 검색..."
                  className="search-input-main"
                />
                <button type="submit" className="search-btn-main">
                  🔍 검색
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>

      <div className="container">
        {/* 인기 토론 */}
        <section className="section">
          <div className="section-header">
            <h2>🔥 인기 토론</h2>
            <Link to="/debate?sort=popular" className="more-link">
              더보기 →
            </Link>
          </div>
          <div className="debate-grid">
            {popularDebates.slice(0, 3).map((debate) => (
              <DebateCard 
                key={debate.id} 
                debate={debate}
                filterState={{}} // 홈페이지에서는 필터 조건 없음
              />
            ))}
          </div>
        </section>

        {/* 최신 토론 */}
        <section className="section">
          <div className="section-header">
            <h2>📢 최신 토론</h2>
            <Link to="/debate?sort=latest" className="more-link">
              더보기 →
            </Link>
          </div>
          <div className="debate-list">
            {latestDebates.map((debate) => (
              <Link 
                key={debate.id} 
                to={`/debate/${debate.id}`} 
                className="debate-item-link"
                state={{}} // 홈페이지에서는 필터 조건 없음
              >
                <div className="debate-item">
                  <div className="debate-item-header">
                    {debate.categoryName && (
                      <span className="category-badge">{debate.categoryName}</span>
                    )}
                    <span className={`status-badge status-${debate.status?.toLowerCase()}`}>
                      {debate.status === 'ACTIVE' ? '진행중' : debate.status === 'SCHEDULED' ? '예정' : '종료'}
                    </span>
                  </div>
                  <h3 className="debate-item-title">
                    {debate.title}
                  </h3>
                  <div className="debate-item-meta" onClick={(e) => e.stopPropagation()}>
                    <span className="author">
                    작성자:{' '}
                    <span 
                      className="author-link" 
                      onClick={(e) => {
                        e.preventDefault()
                        e.stopPropagation()
                        navigate(`/users/${debate.userId}`)
                      }}
                    >
                      {debate.nickname || '알 수 없음'}
                    </span>
                    </span>
                    <span className="stat">👍 {debate.likeCount || 0} | 💬 {debate.commentCount || 0}</span>
                  </div>
                </div>
              </Link>
            ))}
          </div>
        </section>

        {/* 카테고리별 미리보기 */}
        {categories.length > 0 && (
          <section className="section">
            <div className="section-header">
              <h2>📂 카테고리별 토론</h2>
              <Link to="/categories" className="more-link">
                전체 보기 →
              </Link>
            </div>
            <div className="category-preview">
              {categories
                .filter(category => category && category.id && category.name)
                .slice(0, 4)
                .map((category) => (
                  <Link 
                    key={category.id} 
                    to={`/categories/${category.id}`} 
                    className="category-card-link"
                  >
                    <div className="category-card">
                      <h3 className="category-name">{category.name}</h3>
                      <p className="category-count">{category.debateCount || 0}개 토론</p>
                      <span className="category-link">
                        보기 →
                      </span>
                    </div>
                  </Link>
                ))}
            </div>
          </section>
        )}
      </div>
    </div>
  )
}

export default HomePage

