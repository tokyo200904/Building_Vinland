package BuldingWeb.example.nhom13.Controllers;

import BuldingWeb.example.nhom13.Service.impl.yeuThichServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/yeuthich")
public class YeuThichController {

    @Autowired
    private yeuThichServiceImpl yeuThichServiceImpl;
    @PostMapping("/toggle/{maBds}")
    public ResponseEntity<?> toggleYeuThich(@PathVariable Integer maBds) {
        try {
            boolean isLiked = yeuThichServiceImpl.toggleYeuThich(maBds);
            return ResponseEntity.ok(Map.of(
                    "liked", isLiked,
                    "message", isLiked ? "Đã thêm vào danh sách yêu thích" : "Đã bỏ khỏi danh sách yêu thích"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping()
    public ResponseEntity<List<Integer>> danhSachYeuThich() {
//        try {
//            return ResponseEntity.ok(yeuThichServiceImpl.layDanhSachYeuThich());
//        } catch (Exception e) {
//            return ResponseEntity.ok(List.of());
//        }
        return ResponseEntity.ok(yeuThichServiceImpl.layDanhSachYeuThich());
    }
}
