package BuldingWeb.example.nhom13.Model;

import BuldingWeb.example.nhom13.Enums.TrangThaiTinTuc;
import BuldingWeb.example.nhom13.Enums.TrangThaiYeuCauTinTuc;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AgentNewsDTO {
    private Integer maYeuCauTt;
    private String tieuDe;
    private String noiDung;
    private String anhDaiDien;
    private LocalDateTime ngayTao;
    private TrangThaiYeuCauTinTuc trangThai;

    private String lyDoTuChoi;

    private String tenNguoiDang;
    private Integer luotXem;
}