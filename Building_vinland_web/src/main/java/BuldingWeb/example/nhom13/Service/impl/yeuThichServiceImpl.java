package BuldingWeb.example.nhom13.Service.impl;

import BuldingWeb.example.nhom13.Entity.BatDongSan;
import BuldingWeb.example.nhom13.Entity.User;
import BuldingWeb.example.nhom13.Entity.YeuThich;
import BuldingWeb.example.nhom13.Entity.YeuThichId;
import BuldingWeb.example.nhom13.Repository.BdsRepository;
import BuldingWeb.example.nhom13.Repository.YeuThichRepository;
import BuldingWeb.example.nhom13.Service.yeuThichService;
import BuldingWeb.example.nhom13.Utils.TtNdUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class yeuThichServiceImpl implements yeuThichService {
    @Autowired
    private YeuThichRepository yeuThichRepository;
    @Autowired
    private BdsRepository bdsRepository;
    @Autowired
    private TtNdUtil ttNdUtil;


    @Transactional
    @Override
    public boolean toggleYeuThich(Integer maBds) {
        User user = ttNdUtil.getCurrentAuthenticatedUser();
        YeuThichId id = new YeuThichId(user.getUserId(), maBds);

        if (yeuThichRepository.existsById(id)) {
            yeuThichRepository.deleteById(id);
            return false;
        } else {
            BatDongSan bds = bdsRepository.findById(maBds)
                    .orElseThrow(() -> new RuntimeException("Bất động sản không tồn tại"));
            YeuThich newLike = new YeuThich();
            newLike.setId(id);
            newLike.setUser(user);
            newLike.setBatDongSan(bds);

            yeuThichRepository.save(newLike);
            return true;
        }
    }

    @Override
    public List<Integer> layDanhSachYeuThich() {
        User user = ttNdUtil.getCurrentAuthenticatedUser();
        List<YeuThich> likes = yeuThichRepository.findAllByUser(user);

        return likes.stream()
                .map(like -> like.getId().getMaBds())
                .collect(Collectors.toList());
    }
}
