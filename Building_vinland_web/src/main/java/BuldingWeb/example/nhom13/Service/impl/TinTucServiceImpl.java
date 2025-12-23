package BuldingWeb.example.nhom13.Service.impl;

import BuldingWeb.example.nhom13.Entity.TinTuc;
import BuldingWeb.example.nhom13.Entity.User;
import BuldingWeb.example.nhom13.Entity.YeuCauDangTinTuc;
import BuldingWeb.example.nhom13.Enums.VaiTro;
import BuldingWeb.example.nhom13.Mapper.TintucMapper;
import BuldingWeb.example.nhom13.Model.AgentNewsDTO;
import BuldingWeb.example.nhom13.Model.TinTucDTO;
import BuldingWeb.example.nhom13.Model.TinTucDetailDTO;
import BuldingWeb.example.nhom13.Repository.TinTucRepository;
import BuldingWeb.example.nhom13.Repository.YeuCauDangTinTucRepository;
import BuldingWeb.example.nhom13.Service.TinTucService;
import BuldingWeb.example.nhom13.Utils.TtNdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TinTucServiceImpl implements TinTucService {

    @Autowired
    private TinTucRepository tinTucRepository;

    @Autowired
    private TintucMapper tinTucMapper;

    @Autowired
    private TtNdUtil ttNdUtil;

    @Autowired
    private YeuCauDangTinTucRepository yeuCauDangTinTucRepository;

    @Override
    public List<TinTucDTO> getAllTt() {
        List<TinTuc> tinTucs = tinTucRepository.findAll(Sort.by(Sort.Direction.DESC, "ngayTao"));

        return tinTucs.stream()
                .map(tinTucMapper::mapTinTucToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TinTucDetailDTO getTinTucById(Integer id) {
        TinTuc tinTuc = tinTucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức với ID: " + id));
         tinTuc.setLuotXem(tinTuc.getLuotXem() + 1);
         tinTucRepository.save(tinTuc);
        return tinTucMapper.mapTinTucToDetailDto(tinTuc);
    }

    @Transactional
    @Override
    public void deleteNews(Integer id) {
        User currentUser = ttNdUtil.getCurrentAuthenticatedUser();
        if (currentUser.getVaiTro() != VaiTro.ADMIN) {
            throw new SecurityException("Chỉ ADMIN mới có quyền xóa tin tức.");
        }
        TinTuc tinTuc = tinTucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tức với ID: " + id));
        tinTucRepository.delete(tinTuc);
    }

    @Override
    public List<AgentNewsDTO> getMyNewsRequests() {
        User currentUser = ttNdUtil.getCurrentAuthenticatedUser();

        List<YeuCauDangTinTuc> myRequests = yeuCauDangTinTucRepository.findByUserGuiYeuCau(
                currentUser,
                Sort.by(Sort.Direction.DESC, "ngayTaoYeuCauTt")
        );

        // 3. Map sang DTO
        return myRequests.stream()
                .map(tinTucMapper::toAgentNewsDTO)
                .collect(Collectors.toList());
    }
}
