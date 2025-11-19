package com.debate.controller;

import com.debate.dto.response.ApiResponse;
import com.debate.dto.response.DashboardStatsResponse;
import com.debate.entity.Debate;
import com.debate.entity.Report;
import com.debate.entity.User;
import com.debate.service.AdminDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자 대시보드에서 사용하는 통계 및 하이라이트 데이터를 제공하는 컨트롤러.
 * <p>
 * 이용자 현황, 인기 토론, 신고 현황 등 프런트 대시보드 UI에 필요한 데이터를 조회한다.
 */
@Tag(name = "관리자 대시보드 API", description = "관리자 대시보드 통계 및 최근 활동 조회 API")
@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final AdminDashboardService adminDashboardService;

    /**
     * 대시보드 핵심 지표(회원 수, 토론 수, 신고 수 등)를 조회한다.
     *
     * @return 대시보드 통계 DTO wrapped ApiResponse
     */
    @Operation(summary = "대시보드 통계 조회", description = "전체 통계 정보를 조회합니다.")
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStatsResponse>> getStats() {
        DashboardStatsResponse stats = adminDashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * 가장 최근 가입한 회원 목록을 제한된 개수만큼 조회한다.
     *
     * @param limit 조회할 최대 인원
     * @return 회원 목록 wrapped ApiResponse
     */
    @Operation(summary = "최근 가입 회원 조회", description = "최근 가입한 회원 목록을 조회합니다.")
    @GetMapping("/recent-users")
    public ResponseEntity<ApiResponse<List<User>>> getRecentUsers(
            @RequestParam(defaultValue = "10") int limit) {
        List<User> users = adminDashboardService.getRecentUsers(limit);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    /**
     * 조회수가 높은 토론을 상위 N개 조회한다.
     *
     * @param limit 조회 수 상위 개수
     * @return 인기 토론 목록 wrapped ApiResponse
     */
    @Operation(summary = "인기 토론 조회", description = "조회수가 높은 토론 목록을 조회합니다.")
    @GetMapping("/top-debates")
    public ResponseEntity<ApiResponse<List<Debate>>> getTopDebates(
            @RequestParam(defaultValue = "5") int limit) {
        List<Debate> debates = adminDashboardService.getTopDebates(limit);
        return ResponseEntity.ok(ApiResponse.success(debates));
    }

    /**
     * 처리 대기 상태의 신고를 제한 개수만큼 조회한다.
     *
     * @param limit 조회할 신고 건수
     * @return 미처리 신고 목록 wrapped ApiResponse
     */
    @Operation(summary = "미처리 신고 조회", description = "처리 대기 중인 신고 목록을 조회합니다.")
    @GetMapping("/pending-reports")
    public ResponseEntity<ApiResponse<List<Report>>> getPendingReports(
            @RequestParam(defaultValue = "10") int limit) {
        List<Report> reports = adminDashboardService.getPendingReports(limit);
        return ResponseEntity.ok(ApiResponse.success(reports));
    }
}

