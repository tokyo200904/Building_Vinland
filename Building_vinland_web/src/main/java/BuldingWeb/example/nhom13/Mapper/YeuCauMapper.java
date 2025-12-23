package BuldingWeb.example.nhom13.Mapper;

import BuldingWeb.example.nhom13.Entity.*;
import BuldingWeb.example.nhom13.Model.AgentListingDTO;
import BuldingWeb.example.nhom13.Model.Reponse.YeuCauDtReponse;
import BuldingWeb.example.nhom13.Model.Reponse.chitietYcDtReponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class YeuCauMapper {
    public YeuCauDtReponse convertToDTO(YeuCauDangTin yeuCau) {
        YeuCauDtReponse dto = new YeuCauDtReponse();
        dto.setMaYeuCauBds(yeuCau.getMaYeuCauBds());
        dto.setTieuDe(yeuCau.getTieuDe());
        dto.setNgayTaoYeuCau(yeuCau.getNgayTaoYeuCau());
        if (yeuCau.getUserGuiYeuCau() != null){
            dto.setTenNguoiGui(yeuCau.getUserGuiYeuCau().getHoTen());
        }
        else {
            dto.setTenNguoiGui("N/A");
        }
        dto.setGia(yeuCau.getGia());
        dto.setDienTich(yeuCau.getDienTich());
        dto.setDiaChi(yeuCau.getQuanHuyen()+", "+yeuCau.getThanhPho());
        dto.setAnhChinh(yeuCau.getAnhChinh());
        return dto;
    }
    public chitietYcDtReponse convertTochitietDTO(YeuCauDangTin yeuCau) {
        chitietYcDtReponse dto = new chitietYcDtReponse();
        dto.setMaYeuCauBds(yeuCau.getMaYeuCauBds());
        dto.setTieuDe(yeuCau.getTieuDe());
        dto.setMoTa(yeuCau.getMoTa());
        dto.setNgayTaoYeuCau(yeuCau.getNgayTaoYeuCau());
        if(yeuCau.getUserGuiYeuCau()!= null){
            dto.setTenNguoiGui(yeuCau.getUserGuiYeuCau().getHoTen());
            dto.setEmailNguoiGui(yeuCau.getUserGuiYeuCau().getEmail());
            dto.setSdtNguoiGui(yeuCau.getUserGuiYeuCau().getSoDienThoai());
        }
        dto.setGia(yeuCau.getGia());
        dto.setDonViTien(String.valueOf(yeuCau.getDonViTien()));
        dto.setDienTich(yeuCau.getDienTich());
        dto.setViTri(yeuCau.getViTri());
        dto.setQuanHuyen(yeuCau.getQuanHuyen());
        dto.setThanhPho(yeuCau.getThanhPho());
        dto.setLoaiBds(yeuCau.getLoaiBds());
        dto.setMucDichTinDang(yeuCau.getTrangThai());
        dto.setSoPhongNgu(yeuCau.getSoPhongNgu());
        dto.setSoPhongTam(yeuCau.getSoPhongTam());
        dto.setTang(yeuCau.getTang());
        dto.setTongTang(yeuCau.getTongTang());
        dto.setNoiThat(yeuCau.getNoiThat());
        dto.setNamXayDung(yeuCau.getNamXayDung());
        dto.setBaiDoXe(yeuCau.getBaiDoXe());
        dto.setBanCong(yeuCau.getBanCong());
        dto.setThangMay(yeuCau.getThangMay());
        dto.setLyDoTuChoi(yeuCau.getLoiNhanTuChoi());
        dto.setTrangThai(yeuCau.getTrangThaiYeuCau());

        dto.setAnhChinh(yeuCau.getAnhChinh());
        if (yeuCau.getHinhAnhYeuCauList() !=null && !yeuCau.getHinhAnhYeuCauList().isEmpty()){
        List<String> anhphu =  yeuCau.getHinhAnhYeuCauList()
                .stream()
                .map(HinhAnhYeuCau::getDuongDan)
                .toList();
        dto.setAnhPhu(anhphu);
        }
        return dto;
    }
    public BatDongSan convertToBds(YeuCauDangTin yeuCau, User admin) {
        BatDongSan bds = new BatDongSan();
        bds.setTieuDe(yeuCau.getTieuDe());
        bds.setMoTa(yeuCau.getMoTa());
        bds.setGia(yeuCau.getGia());
        bds.setDonViTien(yeuCau.getDonViTien());
        bds.setDienTich(yeuCau.getDienTich());
        bds.setViTri(yeuCau.getViTri());
        bds.setQuanHuyen(yeuCau.getQuanHuyen());
        bds.setThanhPho(yeuCau.getThanhPho());
        bds.setLoaiBds(yeuCau.getLoaiBds());
        bds.setTrangThai(yeuCau.getTrangThai());
        bds.setSoPhongNgu(yeuCau.getSoPhongNgu());
        bds.setSoPhongTam(yeuCau.getSoPhongTam());
        bds.setTang(yeuCau.getTang());
        bds.setTongTang(yeuCau.getTongTang());
        bds.setNoiThat(yeuCau.getNoiThat());
        bds.setNamXayDung(yeuCau.getNamXayDung());
        bds.setAnhChinh(yeuCau.getAnhChinh());
        bds.setThangMay(yeuCau.getThangMay());
        bds.setBaiDoXe(yeuCau.getBaiDoXe());
        bds.setBanCong(yeuCau.getBanCong());
        if (yeuCau.getHinhAnhYeuCauList()!=null && !yeuCau.getHinhAnhYeuCauList().isEmpty()){
            List<HinhAnh> hinhAnhs = new ArrayList<>();
            for (HinhAnhYeuCau anhYeuCau : yeuCau.getHinhAnhYeuCauList()){
                HinhAnh anhmoi = new HinhAnh();
                anhmoi.setDuongDan(anhYeuCau.getDuongDan());
                anhmoi.setBatDongSan(bds);
                hinhAnhs.add(anhmoi);
            }
            bds.setHinhAnhList(hinhAnhs);
        }

        bds.setUser(yeuCau.getUserGuiYeuCau());
        bds.setUserDuyet(yeuCau.getUserDuyet());
        bds.setNgayTao(yeuCau.getNgayTaoYeuCau());

        return bds;
    }

    public AgentListingDTO toAgentListingDTO(YeuCauDangTin entity) {
        return AgentListingDTO.builder()
                .id(entity.getMaYeuCauBds())
                .tieuDe(entity.getTieuDe())
                .gia(entity.getGia())
                .donViTien(entity.getDonViTien())
                .dienTich(entity.getDienTich())
                .anhChinh(entity.getAnhChinh())
                .ngayTao(entity.getNgayTaoYeuCau())
                .trangThai(entity.getTrangThaiYeuCau())
                .lyDoTuChoi(entity.getLoiNhanTuChoi())
                .build();
    }
}
