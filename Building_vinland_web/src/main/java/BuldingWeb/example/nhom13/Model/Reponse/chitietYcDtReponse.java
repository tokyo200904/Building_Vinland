package BuldingWeb.example.nhom13.Model.Reponse;

import BuldingWeb.example.nhom13.Enums.LoaiBDS;
import BuldingWeb.example.nhom13.Enums.NoiThat;
import BuldingWeb.example.nhom13.Enums.TrangThaiBDS;
import BuldingWeb.example.nhom13.Enums.TrangThaiYeuCau;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class chitietYcDtReponse {
    private Integer maYeuCauBds;
    private String tieuDe;
    private String moTa;
    private LocalDateTime ngayTaoYeuCau;

    private String tenNguoiGui;
    private String emailNguoiGui;
    private String sdtNguoiGui;

    private BigDecimal gia;
    private String donViTien;
    private BigDecimal dienTich;
    private String viTri;
    private String quanHuyen;
    private String thanhPho;
    private LoaiBDS loaiBds;
    private TrangThaiBDS mucDichTinDang;
    private Integer soPhongNgu;
    private Integer soPhongTam;
    private Integer tang;
    private Integer tongTang;
    private NoiThat noiThat;
    private Integer namXayDung;

    private Boolean baiDoXe;
    private Boolean banCong;
    private Boolean thangMay;

    private String anhChinh;
    private List<String> anhPhu;
    private String lyDoTuChoi;
    private TrangThaiYeuCau trangThai;

}
