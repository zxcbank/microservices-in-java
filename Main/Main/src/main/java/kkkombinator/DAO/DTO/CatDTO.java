package kkkombinator.DAO.DTO;

import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Entities.Color;
import kkkombinator.DAO.Entities.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CatDTO {
    private Long id;
    private String name;
    private Color color;
    private Set<Cat> myFriends = new HashSet<>();
    private  User user;
}
