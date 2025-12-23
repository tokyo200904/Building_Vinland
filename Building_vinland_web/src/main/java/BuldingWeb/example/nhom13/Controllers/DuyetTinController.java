package BuldingWeb.example.nhom13.Controllers;

import BuldingWeb.example.nhom13.Model.Reponse.YeuCauDtReponse;
import BuldingWeb.example.nhom13.Model.Reponse.chitietYcDtReponse;
import BuldingWeb.example.nhom13.Service.YeuCauDtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/duyettin")
public class DuyetTinController {
    @Autowired
    private YeuCauDtService yeuCauDtService;

    @PreAuthorize("hasAnyRole('ADMIN','NHANVIEN')")
    @GetMapping("/all")
    public ResponseEntity<List<YeuCauDtReponse>> getAllYeuCauDt() {
        List<YeuCauDtReponse> response = yeuCauDtService.getAllYeuCauDt();
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN','NHANVIEN','AGENT')")
    @GetMapping("/{id}")
    public ResponseEntity<chitietYcDtReponse> getYeuCauDtById(@PathVariable Integer id) {
        try {
            chitietYcDtReponse response = yeuCauDtService.getYeuCauDtById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN','NHANVIEN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> DuyetYeuCauDt(@PathVariable Integer id) {
        try {
            yeuCauDtService.DuyetYeuCauDt(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN','NHANVIEN')")
    @PutMapping("/tuchoi/{id}")
    public ResponseEntity<?> TuChoiYeuCauDt(@PathVariable Integer id, @RequestBody String lyDoTuChoi) {
        try {
            yeuCauDtService.TuChoiYeuCauDt(id, lyDoTuChoi);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}