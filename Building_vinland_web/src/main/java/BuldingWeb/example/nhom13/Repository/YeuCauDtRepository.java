package BuldingWeb.example.nhom13.Repository;

import BuldingWeb.example.nhom13.Entity.YeuCauDangTin;
import BuldingWeb.example.nhom13.Enums.TrangThaiYeuCau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YeuCauDtRepository extends JpaRepository<YeuCauDangTin, Integer> {

    List<YeuCauDangTin> findByTrangThaiYeuCau(TrangThaiYeuCau trangThaiYeuCau);
    Optional<YeuCauDangTin> findBymaYeuCauBds(Integer id);
    Long countByTrangThaiYeuCau(TrangThaiYeuCau trangThaiYeuCau);
}
