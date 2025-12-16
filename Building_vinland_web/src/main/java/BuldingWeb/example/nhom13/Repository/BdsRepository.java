package BuldingWeb.example.nhom13.Repository;

import BuldingWeb.example.nhom13.Entity.BatDongSan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BdsRepository extends JpaRepository<BatDongSan,Integer> {
//    @Query("SELECT b.tieu_de, b.mo_ta, b.gia, b.don_vi_tien," +
//            "b.loai_bds, b.trang_thai,CONCAT(b.quan_huyen, ', ', b.thanh_pho) AS vi_tri," +
//            " b.dien_tich," +
//            "b.so_phong_ngu,b.so_phong_tam, b.tong_tang,b.not_that," +
//            "b.nam_xay_dung, b.bai_do_xe, b.ban_cong, b.thang_may," +
//            "b.anh_chinh, a.duong_dan, c.ho_ten, c.so_dien_thoai," +
//            "c.vai_tro, c.anh_dai_dien," +
//            "JSON_ARRAYAGG(h.duong_dan) AS duongDan" +
//            " FROM BatDongSan b " +
//            "inner join user c on b.user_id = c.user_id ")
    BatDongSan findBdsBymaBds(Integer maBds);
    Optional<BatDongSan> findBymaBds(Integer maBds);

    long countByTrangThai(String trangThai);

    @Query("SELECT b.loaiBds, COUNT(b) FROM BatDongSan b GROUP BY b.loaiBds")
    List<Object[]> countByLoaiBds();

    @Query("SELECT MONTH(b.ngayTao), COUNT(b) FROM BatDongSan b WHERE YEAR(b.ngayTao) = :year GROUP BY MONTH(b.ngayTao)")
    List<Object[]> countPostsByMonth(@Param("year") int year);

    @Query("SELECT b.user, COUNT(b) as cnt FROM BatDongSan b GROUP BY b.user ORDER BY cnt DESC")
    List<Object[]> findTopAgents(Pageable pageable);
}
