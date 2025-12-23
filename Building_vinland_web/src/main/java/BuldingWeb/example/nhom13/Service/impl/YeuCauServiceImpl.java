package BuldingWeb.example.nhom13.Service.impl;

import BuldingWeb.example.nhom13.Entity.User;
import BuldingWeb.example.nhom13.Entity.YeuCauDangTin;
import BuldingWeb.example.nhom13.Mapper.YeuCauMapper;
import BuldingWeb.example.nhom13.Model.AgentListingDTO;
import BuldingWeb.example.nhom13.Repository.YeuCauDtRepository;
import BuldingWeb.example.nhom13.Service.YeuCauService;
import BuldingWeb.example.nhom13.Utils.TtNdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YeuCauServiceImpl implements YeuCauService {

    @Autowired
    private YeuCauDtRepository yeuCauDangTinRepository;

    @Autowired
    private TtNdUtil serviceUtils;

    @Autowired
    private YeuCauMapper yeuCauMapper;

    public List<AgentListingDTO> getMyListings() {
        User currentUser = serviceUtils.getCurrentAuthenticatedUser();

        List<YeuCauDangTin> myRequests = yeuCauDangTinRepository.findByUserGuiYeuCau(
                currentUser,
                Sort.by(Sort.Direction.DESC, "ngayTaoYeuCau")
        );

        return myRequests.stream()
                .map(yeuCauMapper::toAgentListingDTO)
                .collect(Collectors.toList());
    }
}