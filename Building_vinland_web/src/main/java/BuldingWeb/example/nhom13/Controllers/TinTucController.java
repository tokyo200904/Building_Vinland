package BuldingWeb.example.nhom13.Controllers;

import BuldingWeb.example.nhom13.Model.TinTucDTO;
import BuldingWeb.example.nhom13.Model.TinTucDetailDTO;
import BuldingWeb.example.nhom13.Service.TinTucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/tintuc")
public class TinTucController {
    @Autowired
    private TinTucService tinTucService;

    @GetMapping("/tintucs")
    public ResponseEntity<List<TinTucDTO>> getAllPublishedNews() {
        List<TinTucDTO> list = tinTucService.getAllTt();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TinTucDetailDTO> getPublishedNewsDetail(@PathVariable Integer id) {
        try {
            TinTucDetailDTO detail = tinTucService.getTinTucById(id);
            return ResponseEntity.ok(detail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteNews(@PathVariable Integer id) {
        try {
            tinTucService.deleteNews(id);
            return ResponseEntity.ok("Xóa tin tức thành công.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
