package kkkombinator.DAO.DTO;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private Set<Long> myCatsIds = new HashSet<>();
    private String password;
}
