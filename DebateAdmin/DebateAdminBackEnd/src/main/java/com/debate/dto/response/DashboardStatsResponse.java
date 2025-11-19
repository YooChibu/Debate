package com.debate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 관리자 대시보드에 표시할 주요 통계를 담는 DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private Long totalUsers;
    private Long totalDebates;
    private Long totalComments;
    private Long activeDebates;
    private Long pendingReports;
    private Long todayNewUsers;
    private Long todayNewDebates;
}

