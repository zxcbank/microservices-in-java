package Main.Models;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private Set<Long> myCatsIds = new HashSet<>();
}
