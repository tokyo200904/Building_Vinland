package BuldingWeb.example.nhom13.Model;

import BuldingWeb.example.nhom13.Enums.DonViTien;
import BuldingWeb.example.nhom13.Enums.TrangThaiYeuCau;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentListingDTO {
    private Integer id; // Mã Yêu Cầu
    private String tieuDe;
    private BigDecimal gia;
    private DonViTien donViTien;
    private BigDecimal dienTich;
    private String anhChinh;
    private LocalDateTime ngayTao;
    private TrangThaiYeuCau trangThai;

    private String lyDoTuChoi;
}
