package BuldingWeb.example.nhom13.Repository;

import BuldingWeb.example.nhom13.Entity.TinTuc;
import BuldingWeb.example.nhom13.Enums.TrangThaiTinTuc;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface TinTucRepository extends JpaRepository<TinTuc,Integer> {
    List<TinTuc> findAll(Sort sort);
    List<TinTuc> findByTrangThai(TrangThaiTinTuc trangThai);
    @Query("SELECT t FROM TinTuc t WHERE t.trangThai = 'XUAT_BAN' ORDER BY t.ngayTao DESC")
    List<TinTuc> findLatestNews(Pageable pageable);
}
