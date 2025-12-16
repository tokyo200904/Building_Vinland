package BuldingWeb.example.nhom13.Model;
import BuldingWeb.example.nhom13.Entity.MoiGioi;
import BuldingWeb.example.nhom13.Model.Reponse.YeuCauDtReponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardDTO {

    // 1. Số liệu tổng quan (4 Cards)
    private long totalProperties;
    private long totalUsers;
    private long pendingRequests;
    private long totalNews;

    private Map<String, Long> propertyTypeDistribution;

    private List<String> months;
    private List<Long> postsPerMonth;

    private List<TopAgentDTO> topAgents;

    private List<YeuCauDtReponse> recentPosts;

    private List<RecentNewsDTO> recentNews;
}
