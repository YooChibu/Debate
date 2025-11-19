package com.debate.service;

import com.debate.entity.Debate;
import com.debate.entity.User;
import com.debate.repository.DebateRepository;
import com.debate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 관리자 통계 화면을 위한 집계 로직을 제공하는 서비스.
 * <p>
 * 현재 구현은 대부분 전체 데이터를 메모리에 로드해 필터링하므로 대량 데이터 환경에서는
 * 쿼리 최적화나 배치 집계가 필요하다.
 */
@Service
@RequiredArgsConstructor
public class AdminStatisticsService {
    private final UserRepository userRepository;
    private final DebateRepository debateRepository;

    /**
     * 회원 현황 통계를 계산한다.
     *
     * @return 회원 수 통계를 담은 Map
     */
    public Map<String, Object> getUserStatistics() {
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.findAll().stream()
                .filter(user -> user.getStatus() == User.UserStatus.ACTIVE)
                .count();
        long suspendedUsers = userRepository.findAll().stream()
                .filter(user -> user.getStatus() == User.UserStatus.SUSPENDED)
                .count();
        long deletedUsers = userRepository.findAll().stream()
                .filter(user -> user.getStatus() == User.UserStatus.DELETED)
                .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("activeUsers", activeUsers);
        stats.put("suspendedUsers", suspendedUsers);
        stats.put("deletedUsers", deletedUsers);
        return stats;
    }

    /**
     * 토론 현황 통계를 계산한다.
     *
     * @return 토론 상태별/숨김 여부 통계를 담은 Map
     */
    public Map<String, Object> getDebateStatistics() {
        long totalDebates = debateRepository.count();
        long scheduledDebates = debateRepository.countByStatus(Debate.DebateStatus.SCHEDULED);
        long activeDebates = debateRepository.countByStatus(Debate.DebateStatus.ACTIVE);
        long endedDebates = debateRepository.countByStatus(Debate.DebateStatus.ENDED);
        long hiddenDebates = debateRepository.findAll().stream()
                .filter(debate -> debate.getIsHidden())
                .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDebates", totalDebates);
        stats.put("scheduledDebates", scheduledDebates);
        stats.put("activeDebates", activeDebates);
        stats.put("endedDebates", endedDebates);
        stats.put("hiddenDebates", hiddenDebates);
        return stats;
    }

    /**
     * 지정된 기간 동안의 일별 회원 가입 수를 계산한다.
     *
     * @param days 조회 기간(일)
     * @return 날짜 문자열을 키로 하는 가입 수 Map
     */
    public Map<String, Long> getDailyUserRegistrations(int days) {
        Map<String, Long> dailyStats = new HashMap<>();
        LocalDate today = LocalDate.now();
        
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
            
            long count = userRepository.findAll().stream()
                    .filter(user -> user.getCreatedAt() != null &&
                            user.getCreatedAt().isAfter(startOfDay) &&
                            user.getCreatedAt().isBefore(endOfDay))
                    .count();
            
            dailyStats.put(date.toString(), count);
        }
        
        return dailyStats;
    }

    /**
     * 지정된 기간 동안의 일별 토론 생성 수를 계산한다.
     *
     * @param days 조회 기간(일)
     * @return 날짜 문자열을 키로 하는 토론 생성 수 Map
     */
    public Map<String, Long> getDailyDebateCreations(int days) {
        Map<String, Long> dailyStats = new HashMap<>();
        LocalDate today = LocalDate.now();
        
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
            
            long count = debateRepository.findAll().stream()
                    .filter(debate -> debate.getCreatedAt() != null &&
                            debate.getCreatedAt().isAfter(startOfDay) &&
                            debate.getCreatedAt().isBefore(endOfDay))
                    .count();
            
            dailyStats.put(date.toString(), count);
        }
        
        return dailyStats;
    }
}

