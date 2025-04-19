package kkkombinator.DAO.DTO;

import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Entities.User;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private Set<Cat> myCats = new HashSet<>();
}
