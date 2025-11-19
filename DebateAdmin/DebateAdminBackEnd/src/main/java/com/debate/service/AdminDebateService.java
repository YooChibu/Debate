package com.debate.service;

import com.debate.entity.Debate;
import com.debate.exception.ResourceNotFoundException;
import com.debate.repository.DebateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 관리자 토론(Debate) 운영 로직을 담당하는 서비스.
 * <p>
 * 검색, 상세 조회, 정보 수정, 상태 변경, 숨김 토글, 삭제 등의 CRUD 액션을 제공한다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminDebateService {
    private final DebateRepository debateRepository;

    /**
     * 조건에 맞는 토론을 페이지 조회한다.
     *
     * @param keyword  제목/내용 검색 키워드
     * @param status   토론 상태
     * @param isHidden 숨김 여부
     * @param pageable 페이지 정보
     * @return 토론 페이지 결과
     */
    public Page<Debate> searchDebates(String keyword, Debate.DebateStatus status, Boolean isHidden, Pageable pageable) {
        log.debug("[ADMIN-DEBATE] 토론 검색 - keyword={}, status={}, isHidden={} page={} size={}",
                keyword, status, isHidden, pageable.getPageNumber(), pageable.getPageSize());
        return debateRepository.searchDebates(keyword, status, isHidden, pageable);
    }

    /**
     * 토론 ID로 엔티티를 조회한다.
     *
     * @param debateId 토론 ID
     * @return 존재하는 토론 엔티티
     * @throws ResourceNotFoundException 토론이 없을 때
     */
    public Debate getDebateById(Long debateId) {
        return debateRepository.findById(debateId)
                .orElseThrow(() -> {
                    log.warn("[ADMIN-DEBATE] 토론 조회 실패 - 존재하지 않음 debateId={}", debateId);
                    return new ResourceNotFoundException("토론을 찾을 수 없습니다");
                });
    }

    /**
     * 토론의 기본 정보를 선택적으로 변경한다.
     *
     * @param debateId  토론 ID
     * @param title     변경할 제목
     * @param content   변경할 내용
     * @param startDate 변경할 시작일
     * @param endDate   변경할 종료일
     * @return 수정된 토론 엔티티
     */
    @Transactional
    public Debate updateDebate(Long debateId, String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
        Debate debate = getDebateById(debateId);
        if (title != null) debate.setTitle(title);
        if (content != null) debate.setContent(content);
        if (startDate != null) debate.setStartDate(startDate);
        if (endDate != null) debate.setEndDate(endDate);
        Debate updated = debateRepository.save(debate);
        log.info("[ADMIN-DEBATE] 토론 수정 - debateId={}, title={}", updated.getId(), updated.getTitle());
        return updated;
    }

    /**
     * 토론 상태를 변경한다.
     *
     * @param debateId 토론 ID
     * @param status 설정할 상태
     * @return 상태가 변경된 토론
     */
    @Transactional
    public Debate updateDebateStatus(Long debateId, Debate.DebateStatus status) {
        Debate debate = getDebateById(debateId);
        debate.setStatus(status);
        Debate updated = debateRepository.save(debate);
        log.info("[ADMIN-DEBATE] 토론 상태 변경 - debateId={}, status={}", updated.getId(), updated.getStatus());
        return updated;
    }

    /**
     * 토론의 숨김 플래그를 토글한다.
     *
     * @param debateId 토론 ID
     * @return 숨김 상태가 토글된 토론
     */
    @Transactional
    public Debate toggleDebateHidden(Long debateId) {
        Debate debate = getDebateById(debateId);
        debate.setIsHidden(!debate.getIsHidden());
        Debate updated = debateRepository.save(debate);
        log.info("[ADMIN-DEBATE] 토론 숨김 토글 - debateId={}, hidden={}", updated.getId(), updated.getIsHidden());
        return updated;
    }

    /**
     * 토론을 삭제한다.
     *
     * @param debateId 토론 ID
     */
    @Transactional
    public void deleteDebate(Long debateId) {
        Debate debate = getDebateById(debateId);
        debateRepository.delete(debate);
        log.info("[ADMIN-DEBATE] 토론 삭제 - debateId={}", debateId);
    }
}

