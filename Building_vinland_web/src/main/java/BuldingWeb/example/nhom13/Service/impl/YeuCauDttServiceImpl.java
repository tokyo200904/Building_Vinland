package BuldingWeb.example.nhom13.Service.impl;

import BuldingWeb.example.nhom13.Entity.TinTuc;
import BuldingWeb.example.nhom13.Entity.User;
import BuldingWeb.example.nhom13.Entity.YeuCauDangTinTuc;
import BuldingWeb.example.nhom13.Enums.TrangThaiYeuCauTinTuc;
import BuldingWeb.example.nhom13.Enums.VaiTro;
import BuldingWeb.example.nhom13.Mapper.TintucMapper;
import BuldingWeb.example.nhom13.Model.Reponse.DetailTtReponse;
import BuldingWeb.example.nhom13.Model.Reponse.dangTinTucReponse;
import BuldingWeb.example.nhom13.Model.YeuCauTinTucDTO;
import BuldingWeb.example.nhom13.Repository.TinTucRepository;
import BuldingWeb.example.nhom13.Repository.YeuCauDangTinTucRepository;
import BuldingWeb.example.nhom13.Service.YeuCauDttService;
import BuldingWeb.example.nhom13.Utils.TtNdUtil;
import BuldingWeb.example.nhom13.Utils.UploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YeuCauDttServiceImpl implements YeuCauDttService {

    @Autowired
    private TtNdUtil ttNdUtil;
    @Autowired
    private UploadUtil uploadUtil;
    @Autowired
    private TintucMapper tintucMapper;
    @Autowired
    private TinTucRepository tinTucRepository;
    @Autowired
    private YeuCauDangTinTucRepository yeuCauDangTinTucRepository;

    @Transactional
    public String dangTinTuc(dangTinTucReponse dto, MultipartFile anh) throws Exception {
        User user = ttNdUtil.getCurrentAuthenticatedUser();
        VaiTro vaiTro = user.getVaiTro();
        String anhDaiDienUrl = null;
        if(anh != null && !anh.isEmpty()) {
            anhDaiDienUrl = uploadUtil.saveFile(anh);
        }
        if (vaiTro == VaiTro.ADMIN || vaiTro == VaiTro.NHANVIEN) {
            TinTuc tinTuc = tintucMapper.mapDtoToTinTuc(dto, user, anhDaiDienUrl);
            tinTucRepository.save(tinTuc);
            return "Tin tức đã được đăng thành công!";
        } else if (vaiTro == VaiTro.AGENT) {
            YeuCauDangTinTuc yeuCau = tintucMapper.mapDtoToYeuCau(dto, user, anhDaiDienUrl);
            yeuCauDangTinTucRepository.save(yeuCau);
            return "Yêu cầu đăng tin của bạn đã được gửi và đang chờ duyệt!";
        } else {
            throw new SecurityException("Bạn không có quyền thực hiện chức năng này.");
        }
    }

    @Transactional
    @Override
    public List<YeuCauTinTucDTO> getAllDuyetTinTuc() {
        List<YeuCauDangTinTuc> yeuCaus = yeuCauDangTinTucRepository
                .findByTrangThaiYeuCauTt(TrangThaiYeuCauTinTuc.CHO_DUYET);

        return yeuCaus.stream()
                .map(tintucMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DetailTtReponse getChiTietDuyetTt(Integer yeuCauId) {
        YeuCauDangTinTuc yeuCau = yeuCauDangTinTucRepository.findById(yeuCauId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu tin tức với ID: " + yeuCauId));

        if (yeuCau.getTrangThaiYeuCauTt() != TrangThaiYeuCauTinTuc.CHO_DUYET) {
            throw new RuntimeException("Yêu cầu này không ở trạng thái chờ duyệt.");
        }

        return tintucMapper.mapEntityToDetailDto(yeuCau);
    }

    @Transactional
    public void duyetTinTuc(Integer yeuCauId) {
        User adminUser = ttNdUtil.getCurrentAuthenticatedUser();

        YeuCauDangTinTuc yeuCau = yeuCauDangTinTucRepository.findById(yeuCauId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu tin tức với ID: " + yeuCauId));

        if (yeuCau.getTrangThaiYeuCauTt() != TrangThaiYeuCauTinTuc.CHO_DUYET) {
            throw new RuntimeException("Yêu cầu này không ở trạng thái chờ duyệt.");
        }

        TinTuc newTinTuc = tintucMapper.mapYeuCauToTinTuc(yeuCau, adminUser);
        tinTucRepository.save(newTinTuc);

        yeuCau.setTrangThaiYeuCauTt(TrangThaiYeuCauTinTuc.DA_DUYET);
        yeuCau.setUserDuyet(adminUser);
        yeuCau.setNgayCapNhatYeuCauTt(LocalDateTime.now());

        yeuCauDangTinTucRepository.save(yeuCau);
    }

    @Transactional
    public void TuChoiTinTuc(Integer yeuCauId, String lyDo) {
        User adminUser = ttNdUtil.getCurrentAuthenticatedUser();

        YeuCauDangTinTuc yeuCau = yeuCauDangTinTucRepository.findById(yeuCauId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu tin tức với ID: " + yeuCauId));

        if (yeuCau.getTrangThaiYeuCauTt() != TrangThaiYeuCauTinTuc.CHO_DUYET) {
            throw new RuntimeException("Yêu cầu này không ở trạng thái chờ duyệt.");
        }

        yeuCau.setTrangThaiYeuCauTt(TrangThaiYeuCauTinTuc.TU_CHOI);
        yeuCau.setUserDuyet(adminUser);
        yeuCau.setNgayCapNhatYeuCauTt(LocalDateTime.now());
        yeuCau.setLoiNhanTuChoi(lyDo);

        yeuCauDangTinTucRepository.save(yeuCau);
    }

}
