package BuldingWeb.example.nhom13.Mapper;

import BuldingWeb.example.nhom13.Entity.TinTuc;
import BuldingWeb.example.nhom13.Entity.User;
import BuldingWeb.example.nhom13.Entity.YeuCauDangTinTuc;
import BuldingWeb.example.nhom13.Enums.TrangThaiTinTuc;
import BuldingWeb.example.nhom13.Enums.TrangThaiYeuCauTinTuc;
import BuldingWeb.example.nhom13.Model.Reponse.DetailTtReponse;
import BuldingWeb.example.nhom13.Model.Reponse.dangTinTucReponse;
import BuldingWeb.example.nhom13.Model.TinTucDTO;
import BuldingWeb.example.nhom13.Model.TinTucDetailDTO;
import BuldingWeb.example.nhom13.Model.YeuCauTinTucDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TintucMapper {
    public TinTuc mapDtoToTinTuc(dangTinTucReponse dto, User user, String anh){
        return TinTuc.builder()
                .tieuDe(dto.getTieuDe())
                .duongDan(dto.getDuongDan())
                .noiDung(dto.getNoiDung())
                .anhDaiDien(anh)
                .trangThai(TrangThaiTinTuc.xuat_ban)
                .ngayXuatBan(dto.getNgayXuatBan() != null ? dto.getNgayXuatBan() : LocalDateTime.now())
                .luotXem(0)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .user(user)
                .build();
    }
    public YeuCauDangTinTuc mapDtoToYeuCau(dangTinTucReponse dto, User user, String anh){
        return YeuCauDangTinTuc.builder()
                .tieuDe(dto.getTieuDe())
                .duongDan(dto.getDuongDan())
                .noiDung(dto.getNoiDung())
                .anhDaiDien(anh)
                .ngayXuatBan(dto.getNgayXuatBan())
                .trangThaiYeuCauTt(TrangThaiYeuCauTinTuc.CHO_DUYET)
                .ngayTaoYeuCauTt(LocalDateTime.now())
                .ngayCapNhatYeuCauTt(LocalDateTime.now())
                .userGuiYeuCau(user)
                .build();
    }
    public TinTuc mapYeuCauToTinTuc(YeuCauDangTinTuc yeuCau, User adminUser){
        return TinTuc.builder()
                .tieuDe(yeuCau.getTieuDe())
                .duongDan(yeuCau.getDuongDan())
                .noiDung(yeuCau.getNoiDung())
                .anhDaiDien(yeuCau.getAnhDaiDien())
                .trangThai(TrangThaiTinTuc.xuat_ban)
                .ngayXuatBan(yeuCau.getNgayXuatBan() != null ? yeuCau.getNgayXuatBan() : LocalDateTime.now())
                .luotXem(0)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .user(yeuCau.getUserGuiYeuCau())
                .build();
    }
    public YeuCauTinTucDTO mapEntityToDto(YeuCauDangTinTuc yeuCau){
        YeuCauTinTucDTO dto = new YeuCauTinTucDTO();
        dto.setMaYeuCauTt(yeuCau.getMaYeuCauTt());
        dto.setTieuDe(yeuCau.getTieuDe());
        dto.setAnhDaiDien(yeuCau.getAnhDaiDien());
        dto.setNgayTaoYeuCauTt(yeuCau.getNgayTaoYeuCauTt());
        if (yeuCau.getUserGuiYeuCau() != null){
            dto.setTenNguoiGui(yeuCau.getUserGuiYeuCau().getHoTen());
        }
        return dto;
    }

    public DetailTtReponse mapEntityToDetailDto(YeuCauDangTinTuc yeuCau){
        DetailTtReponse dto = new DetailTtReponse();
        dto.setMaYeuCauTt(yeuCau.getMaYeuCauTt());
        dto.setTieuDe(yeuCau.getTieuDe());
        dto.setDuongDan(yeuCau.getDuongDan());
        dto.setNoiDung(yeuCau.getNoiDung());
        dto.setAnhDaiDien(yeuCau.getAnhDaiDien());
        dto.setNgayXuatBan(yeuCau.getNgayXuatBan());
        dto.setNgayTaoYeuCauTt(yeuCau.getNgayTaoYeuCauTt());
        if (yeuCau.getUserGuiYeuCau() != null) {
            User user = yeuCau.getUserGuiYeuCau();
            dto.setTenNguoiGui(user.getHoTen());
            dto.setEmailNguoiGui(user.getEmail());
            dto.setSdtNguoiGui(user.getSoDienThoai());
        }

        return dto;
    }


    public TinTucDTO mapTinTucToDto(TinTuc tinTuc) {
        return TinTucDTO.builder()
                .maTin(tinTuc.getMaTin())
                .tieuDe(tinTuc.getTieuDe())
                .anhDaiDien(tinTuc.getAnhDaiDien())
                .trangThai(tinTuc.getTrangThai())
                .tenNguoiDang(tinTuc.getUser() != null ? tinTuc.getUser().getHoTen() : "N/A")
                .ngayTao(tinTuc.getNgayTao())
                .luotXem(tinTuc.getLuotXem())
                .build();
    }


    public TinTucDetailDTO mapTinTucToDetailDto(TinTuc tinTuc) {
        return TinTucDetailDTO.builder()
                .maTin(tinTuc.getMaTin())
                .tieuDe(tinTuc.getTieuDe())
                .noiDung(tinTuc.getNoiDung())
                .anhDaiDien(tinTuc.getAnhDaiDien())
                .tenNguoiDang(tinTuc.getUser() != null ? tinTuc.getUser().getHoTen() : "N/A")
                .ngayTao(tinTuc.getNgayTao())
                .ngayXuatBan(tinTuc.getNgayXuatBan())
                .trangThai(tinTuc.getTrangThai())
                .luotXem(tinTuc.getLuotXem())
                .build();
    }
}
