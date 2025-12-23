package BuldingWeb.example.nhom13.Controllers;


import BuldingWeb.example.nhom13.Model.AgentListingDTO;
import BuldingWeb.example.nhom13.Model.AgentNewsDTO;
import BuldingWeb.example.nhom13.Service.TinTucService;
import BuldingWeb.example.nhom13.Service.YeuCauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/v1/yeu-cau")
public class QlAgentController {

    @Autowired
    private TinTucService tinTucService;

    @Autowired
    private YeuCauService yeuCauService;

    @GetMapping("/my-listings")
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<List<AgentListingDTO>> getMyListings() {
        return ResponseEntity.ok(yeuCauService.getMyListings());
    }
    @GetMapping("/my")
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<List<AgentNewsDTO>> getMyNewsRequests() {
        return ResponseEntity.ok(tinTucService.getMyNewsRequests());
    }
}