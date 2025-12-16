package BuldingWeb.example.nhom13.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopAgentDTO {
    private String name;
    private String avatar;
    private String email;
    private long postCount;
}
