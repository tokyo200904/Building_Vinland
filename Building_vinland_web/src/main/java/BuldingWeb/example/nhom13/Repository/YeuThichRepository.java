package BuldingWeb.example.nhom13.Repository;

import BuldingWeb.example.nhom13.Entity.User;
import BuldingWeb.example.nhom13.Entity.YeuThich;
import BuldingWeb.example.nhom13.Entity.YeuThichId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YeuThichRepository extends JpaRepository<YeuThich, YeuThichId> {

    List<YeuThich> findAllByUser(User user);

    boolean existsById(YeuThichId id);

    void deleteById(YeuThichId id);

    List<YeuThich> findByBatDongSan_MaBds(Integer maBds);
}