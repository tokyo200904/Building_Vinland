package BuldingWeb.example.nhom13.Model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class RecentNewsDTO {
    private Integer maTin;
    private String tieuDe;
    private String anhDaiDien;
    private LocalDateTime ngayTao;
    private String tenNguoiDang;
}