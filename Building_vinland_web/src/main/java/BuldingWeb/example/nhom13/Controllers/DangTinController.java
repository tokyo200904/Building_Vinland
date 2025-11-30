package BuldingWeb.example.nhom13.Controllers;

import BuldingWeb.example.nhom13.Model.YeuCauDTO;
import BuldingWeb.example.nhom13.Service.DangTinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/dangtin")
public class  DangTinController {
    @Autowired
    private DangTinService dangTinService;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN','NHANVIEN','AGENT')")
    public ResponseEntity<?> dangTinBds(
            @RequestPart("data")YeuCauDTO dto,
            @RequestPart("mainImageFile")MultipartFile anh,
            @RequestPart(value = "otherImageFiles", required = false) List<MultipartFile> anhPhu){
        try {
            String messege = dangTinService.dangTin(dto,anh,anhPhu);
            return ResponseEntity.ok(messege);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("loi may chu" +e.getMessage());
        }
    }
}
