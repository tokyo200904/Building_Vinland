package BuldingWeb.example.nhom13.Service;

import BuldingWeb.example.nhom13.Entity.TinTuc;
import BuldingWeb.example.nhom13.Model.AgentNewsDTO;
import BuldingWeb.example.nhom13.Model.TinTucDTO;
import BuldingWeb.example.nhom13.Model.TinTucDetailDTO;

import java.util.List;

public interface TinTucService {
    public List<TinTucDTO> getAllTt();
    public TinTucDetailDTO getTinTucById(Integer id);
    public void deleteNews(Integer id);
    List<AgentNewsDTO> getMyNewsRequests();
}
