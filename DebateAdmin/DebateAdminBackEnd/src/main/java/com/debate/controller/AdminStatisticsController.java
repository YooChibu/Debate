package com.debate.controller;

import com.debate.dto.response.ApiResponse;
import com.debate.service.AdminStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 관리자 통계 및 분석 데이터를 제공하는 컨트롤러.
 * <p>
 * 회원/토론의 누적 통계와 일별 추이 데이터를 조회하여 BI 혹은 차트 화면에 전달한다.
 */
@Tag(name = "관리자 통계 API", description = "통계 및 분석 데이터 조회 API")
@RestController
@RequestMapping("/api/admin/statistics")
@RequiredArgsConstructor
public class AdminStatisticsController {
    private final AdminStatisticsService adminStatisticsService;

    /**
     * 전체 회원 수, 상태별 회원 수 등의 통계를 제공한다.
     *
     * @return 회원 통계 Map wrapped ApiResponse
     */
    @Operation(summary = "회원 통계 조회", description = "회원 관련 통계를 조회합니다.")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserStatistics() {
        Map<String, Object> stats = adminStatisticsService.getUserStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * 토론의 상태/숨김 여부 등을 집계한 통계를 제공한다.
     *
     * @return 토론 통계 Map wrapped ApiResponse
     */
    @Operation(summary = "토론 통계 조회", description = "토론 관련 통계를 조회합니다.")
    @GetMapping("/debates")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDebateStatistics() {
        Map<String, Object> stats = adminStatisticsService.getDebateStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * 지정한 기간 동안의 일별 회원 가입 수를 조회한다.
     *
     * @param days 조회할 일수(오늘 포함)
     * @return 날짜별 회원 가입 수 Map wrapped ApiResponse
     */
    @Operation(summary = "일별 회원 가입 통계", description = "지정된 일수 동안의 일별 회원 가입 통계를 조회합니다.")
    @GetMapping("/users/daily")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getDailyUserRegistrations(
            @RequestParam(defaultValue = "7") int days) {
        Map<String, Long> stats = adminStatisticsService.getDailyUserRegistrations(days);
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * 지정한 기간 동안의 일별 토론 생성 수를 조회한다.
     *
     * @param days 조회할 일수(오늘 포함)
     * @return 날짜별 토론 생성 수 Map wrapped ApiResponse
     */
    @Operation(summary = "일별 토론 생성 통계", description = "지정된 일수 동안의 일별 토론 생성 통계를 조회합니다.")
    @GetMapping("/debates/daily")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getDailyDebateCreations(
            @RequestParam(defaultValue = "7") int days) {
        Map<String, Long> stats = adminStatisticsService.getDailyDebateCreations(days);
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}

