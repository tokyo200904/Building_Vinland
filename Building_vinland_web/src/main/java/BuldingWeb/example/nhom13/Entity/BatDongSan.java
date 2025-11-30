package BuldingWeb.example.nhom13.Entity;

import BuldingWeb.example.nhom13.Enums.DonViTien;
import BuldingWeb.example.nhom13.Enums.LoaiBDS;
import BuldingWeb.example.nhom13.Enums.NoiThat;
import BuldingWeb.example.nhom13.Enums.TrangThaiBDS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bat_dong_san")
public class BatDongSan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_bds")
    private int maBds;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "gia")
    private BigDecimal gia;

    @Enumerated(EnumType.STRING)
    @Column(name = "don_vi_tien")
    private DonViTien donViTien;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_bds")
    private LoaiBDS loaiBds;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThaiBDS trangThai;

    @Column(name = "vi_tri")
    private String viTri;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "thanh_pho")
    private String thanhPho;

    @Column(name = "dien_tich")
    private BigDecimal dienTich;

    @Column(name = "so_phong_ngu")
    private Integer soPhongNgu;

    @Column(name = "so_phong_tam")
    private Integer soPhongTam;

    @Column(name = "tang")
    private Integer tang;

    @Column(name = "tong_tang")
    private Integer tongTang;

    @Enumerated(EnumType.STRING)
    @Column(name = "noi_that")
    private NoiThat noiThat;

    @Column(name = "nam_xay_dung")
    private Integer namXayDung;

    @Column(name = "bai_do_xe")
    private Boolean baiDoXe;

    @Column(name = "ban_cong")
    private Boolean banCong;

    @Column(name = "thang_may")
    private Boolean thangMay;

    @Column(name = "anh_chinh")
    private String anhChinh;

    @CreationTimestamp
    @Column(name = "ngay_tao", updatable = false)
    private LocalDateTime ngayTao;

    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_duyet")
    private User userDuyet;

    @JsonIgnore
    @OneToMany(mappedBy = "batDongSan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HinhAnh> hinhAnhList;

    @JsonIgnore
    @OneToMany(mappedBy = "batDongSan")
    private List<GiaoDich> giaoDichList;

    @JsonIgnore
    @OneToMany(mappedBy = "batDongSan" )
    private Set<YeuThich> yeuThichList;
}
