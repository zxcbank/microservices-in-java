package Models;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class CatDTO {
    private Long id;
    private String name;
    private String color;
    private Set<Long> myFriendsIds = new HashSet<>();
    private long userId;
}
