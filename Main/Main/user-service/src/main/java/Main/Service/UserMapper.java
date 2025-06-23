package Main.Service;

import Main.Models.UserDTO;
import jakarta.persistence.EntityManager;
import Main.Entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class UserMapper {

    private final EntityManager manager;

    public UserMapper(EntityManager manager) {
        this.manager = manager;
    }


    public UserDTO toUserDTO(User user) {
        UserDTO userDataObject = new UserDTO();
        if (user == null) {
            return userDataObject;
        }
        userDataObject.setId(user.getId());
        userDataObject.setName(user.getName());
        userDataObject.setMyCatsIds(new HashSet<>(user.getMyCatsIds()));
        userDataObject.setPassword(user.getPassword());

        return userDataObject;
    }

    public User toUser(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());

        Set<Long> catsIds = userDTO.getMyCatsIds() != null ?
                userDTO.getMyCatsIds() : Collections.emptySet();

        user.setMyCatsIds(catsIds);
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public Iterable<UserDTO> toUserDTOs(Iterable<User> users) {
        return StreamSupport.stream(users.spliterator(), false)
                .map(this::toUserDTO)
                .collect(Collectors.toList());
    }

    public Iterable<User> toUsers(Iterable<UserDTO> userDTOs) {
        Iterable<User> users = StreamSupport.stream(userDTOs.spliterator(), false)
                .map(this::toUser)
                .collect(Collectors.toList());
        return users;
    }
}

