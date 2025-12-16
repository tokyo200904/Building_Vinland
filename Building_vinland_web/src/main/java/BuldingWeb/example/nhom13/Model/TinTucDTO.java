package BuldingWeb.example.nhom13.Model;

import BuldingWeb.example.nhom13.Enums.TrangThaiTinTuc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TinTucDTO {
    private Integer maTin;
    private String tieuDe;
    private String anhDaiDien;
    private TrangThaiTinTuc trangThai;
    private String tenNguoiDang;
    private LocalDateTime ngayTao;
    private Integer luotXem;
}