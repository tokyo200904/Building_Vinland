package BuldingWeb.example.nhom13.Controllers;

import BuldingWeb.example.nhom13.Model.Reponse.DetailTtReponse;
import BuldingWeb.example.nhom13.Model.Reponse.dangTinTucReponse;
import BuldingWeb.example.nhom13.Model.YeuCauTinTucDTO;
import BuldingWeb.example.nhom13.Service.YeuCauDttService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/tintuc")
@PreAuthorize("hasAnyRole('ADMIN', 'NHANVIEN', 'AGENT')")
public class DangTtController {
    @Autowired
    private YeuCauDttService yeuCauDttService;

    @Data
    static class tuchoidangtintuc{
        private String lyDoTuChoi;
    }

    @PostMapping(value = "/dangtin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> dangTinTucMoi(
            @Valid @RequestPart("data") dangTinTucReponse dto,
            @RequestPart(value = "anhDaiDienFile", required = false) MultipartFile anhDaiDienFile) {

        try {
            String message = yeuCauDttService.dangTinTuc(dto, anhDaiDienFile);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/duyettin/tintucs")
    @PreAuthorize("hasAnyRole('ADMIN', 'NHANVIEN')")
    public ResponseEntity<List<YeuCauTinTucDTO>> getAllTinTuc() {
        List<YeuCauTinTucDTO> list = yeuCauDttService.getAllDuyetTinTuc();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/duyettin/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<DetailTtReponse> getTinTucDetail(@PathVariable Integer id) {
        try {
            DetailTtReponse detail = yeuCauDttService.getChiTietDuyetTt(id);
            return ResponseEntity.ok(detail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PutMapping("/duyettin/approve/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<?> approveTinTuc(@PathVariable Integer id) {
        try {
            yeuCauDttService.duyetTinTuc(id);
            return ResponseEntity.ok("Phê duyệt tin tức thành công.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/duyettin/reject/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<?> rejectTinTuc(@PathVariable Integer id, @RequestBody tuchoidangtintuc request) {
        try {
            yeuCauDttService.TuChoiTinTuc(id, request.getLyDoTuChoi());
            return ResponseEntity.ok("Từ chối tin tức thành công.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
