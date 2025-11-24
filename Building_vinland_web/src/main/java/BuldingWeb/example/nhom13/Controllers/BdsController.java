package BuldingWeb.example.nhom13.Controllers;

import BuldingWeb.example.nhom13.Model.Reponse.editbdsReponse;
import BuldingWeb.example.nhom13.Model.bdsDTO;
import BuldingWeb.example.nhom13.Model.ctbdsDTO;
import BuldingWeb.example.nhom13.Service.BdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/bds")
public class BdsController {
    @Autowired
    private BdsService bdsService;

    @GetMapping
    public ResponseEntity<List<bdsDTO>> getAllBds() {
        List<bdsDTO> bdsDTOS = bdsService.getAllBds();
        return ResponseEntity.ok(bdsDTOS);
    }
    @GetMapping("/chitiet/{id}")
    public ResponseEntity<?> getBdsById(@PathVariable("id") Integer id) {
        ctbdsDTO dto = bdsService.getBdsById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBds(@PathVariable Integer id) {
        bdsService.deleteBds(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBds(@PathVariable("id") Integer id, @ModelAttribute editbdsReponse DTO){
        editbdsReponse updatedBds = bdsService.updateBds(id, DTO);
        return ResponseEntity.ok(updatedBds);
    }
}