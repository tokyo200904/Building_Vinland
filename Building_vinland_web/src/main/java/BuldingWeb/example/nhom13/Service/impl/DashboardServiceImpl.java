package BuldingWeb.example.nhom13.Service.impl;

import BuldingWeb.example.nhom13.Entity.BatDongSan;
import BuldingWeb.example.nhom13.Entity.TinTuc;
import BuldingWeb.example.nhom13.Entity.User;
import BuldingWeb.example.nhom13.Enums.TrangThaiYeuCau;
import BuldingWeb.example.nhom13.Model.*;
import BuldingWeb.example.nhom13.Model.Reponse.YeuCauDtReponse;
import BuldingWeb.example.nhom13.Repository.BdsRepository;
import BuldingWeb.example.nhom13.Repository.TinTucRepository;
import BuldingWeb.example.nhom13.Repository.UserReponsitory;
import BuldingWeb.example.nhom13.Repository.YeuCauDtRepository;
import BuldingWeb.example.nhom13.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private BdsRepository batDongSanRepository;
    @Autowired
    private UserReponsitory userRepository;
    @Autowired
    private YeuCauDtRepository yeuCauDangTinRepository;
    @Autowired
    private TinTucRepository tinTucRepository;
    public DashboardDTO getStats() {
        // 1. Tổng quan
        long totalBds = batDongSanRepository.count();
        long totalUsers = userRepository.count();
        long pendingRequests = yeuCauDangTinRepository.countByTrangThaiYeuCau(TrangThaiYeuCau.CHO_DUYET);
        long totalNews = tinTucRepository.count();

        // 2. Biểu đồ tròn
        List<Object[]> typeStats = batDongSanRepository.countByLoaiBds();
        Map<String, Long> typeMap = new HashMap<>();
        if (typeStats != null) {
            for (Object[] row : typeStats) {
                if(row[0] != null) typeMap.put(row[0].toString(), (Long) row[1]);
            }
        }

        // 3. Biểu đồ đường (12 tháng)
        int currentYear = LocalDate.now().getYear();
        List<Object[]> monthStats = batDongSanRepository.countPostsByMonth(currentYear);
        Map<Integer, Long> monthlyData = new HashMap<>();
        if (monthStats != null) {
            for (Object[] row : monthStats) {
                monthlyData.put((Integer) row[0], (Long) row[1]);
            }
        }

        List<String> months = new ArrayList<>();
        List<Long> posts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add("T" + i);
            posts.add(monthlyData.getOrDefault(i, 0L));
        }

        // 4. Top Agents
        List<Object[]> topAgentRaw = batDongSanRepository.findTopAgents((Pageable) PageRequest.of(0, 5));
        List<TopAgentDTO> topAgents = new ArrayList<>();
        if (topAgentRaw != null) {
            topAgents = topAgentRaw.stream().map(row -> {
                User u = (User) row[0];
                Long count = (Long) row[1];
                return TopAgentDTO.builder()
                        .name(u.getHoTen()).email(u.getEmail()).avatar(u.getAnhDaiDien())
                        .postCount(count).build();
            }).collect(Collectors.toList());
        }

        // 5. Tin BĐS mới nhất (Đã duyệt)
        List<BatDongSan> latestBds = batDongSanRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "ngayTao"))
        ).getContent();

        List<YeuCauDtReponse> recentPosts = latestBds.stream().map(bds -> {
            YeuCauDtReponse dto = new YeuCauDtReponse();
            dto.setMaYeuCauBds(bds.getMaBds());
            dto.setTieuDe(bds.getTieuDe());
            dto.setTenNguoiGui(bds.getUser() != null ? bds.getUser().getHoTen() : "Admin");
            dto.setGia(bds.getGia());
            dto.setAnhChinh(bds.getAnhChinh());
            dto.setNgayTaoYeuCau(bds.getNgayTao());
            return dto;
        }).collect(Collectors.toList());

        // 6. Tin Tức Mới Nhất
        List<TinTuc> newsList = tinTucRepository.findLatestNews((Pageable) PageRequest.of(0, 5));
        List<RecentNewsDTO> recentNews = newsList.stream().map(news ->
                RecentNewsDTO.builder()
                        .maTin(news.getMaTin())
                        .tieuDe(news.getTieuDe())
                        .anhDaiDien(news.getAnhDaiDien())
                        .ngayTao(news.getNgayTao())
                        .tenNguoiDang(news.getUser() != null ? news.getUser().getHoTen() : "Admin")
                        .build()
        ).collect(Collectors.toList());

        return DashboardDTO.builder()
                .totalProperties(totalBds)
                .totalUsers(totalUsers)
                .pendingRequests(pendingRequests)
                .totalNews(totalNews)
                .propertyTypeDistribution(typeMap)
                .months(months)
                .postsPerMonth(posts)
                .topAgents(topAgents)
                .recentPosts(recentPosts)
                .recentNews(recentNews)
                .build();
    }
}