package com.debate.repository;

import com.debate.entity.Debate;
import com.debate.entity.Debate.DebateStatus;
import com.debate.entity.Category;
import com.debate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 토론(Debate) 엔티티용 리포지토리.
 * <p>
 * 공개 여부, 상태, 기간 등에 따른 조회 편의 메서드를 제공한다.
 */
@Repository
public interface DebateRepository extends JpaRepository<Debate, Long> {
    /** 숨겨지지 않은 토론을 페이지 조회 */
    Page<Debate> findByIsHiddenFalse(Pageable pageable);

    /** 특정 카테고리에 속한 공개 토론을 페이지 조회 */
    Page<Debate> findByCategoryAndIsHiddenFalse(Category category, Pageable pageable);

    /** 특정 사용자가 작성한 공개 토론을 페이지 조회 */
    Page<Debate> findByUserAndIsHiddenFalse(User user, Pageable pageable);

    /** 상태별 공개 토론을 페이지 조회 */
    Page<Debate> findByStatusAndIsHiddenFalse(DebateStatus status, Pageable pageable);
    
    @Query("SELECT d FROM Debate d WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "d.title LIKE %:keyword% OR d.content LIKE %:keyword%) " +
           "AND (:status IS NULL OR d.status = :status) " +
           "AND (:isHidden IS NULL OR d.isHidden = :isHidden)")
    /**
     * 키워드, 상태, 숨김 여부 조건을 동시에 적용한 검색.
     */
    Page<Debate> searchDebates(@Param("keyword") String keyword,
                           @Param("status") DebateStatus status,
                           @Param("isHidden") Boolean isHidden,
                           Pageable pageable);
    
    /** 시작일 기준으로 상태를 가진 토론 조회 (스케줄러 용도) */
    List<Debate> findByStatusAndStartDateLessThanEqual(DebateStatus status, LocalDateTime now);

    /** 종료일 기준으로 상태를 가진 토론 조회 (스케줄러 용도) */
    List<Debate> findByStatusAndEndDateLessThanEqual(DebateStatus status, LocalDateTime now);
    
    @Query("SELECT d FROM Debate d WHERE d.isHidden = false ORDER BY d.viewCount DESC")
    /**
     * 조회수가 높은 공개 토론을 상위 N개 반환.
     */
    List<Debate> findTopByOrderByViewCountDesc(Pageable pageable);
    
    /** 상태별 토론 수 카운트 */
    long countByStatus(DebateStatus status);

    /** 숨김되지 않은 토론 수 카운트 */
    long countByIsHiddenFalse();
}

