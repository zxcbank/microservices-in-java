package kkkombinator.Controller;

import jakarta.persistence.EntityManager;
import kkkombinator.DAO.DTO.CatDTO;
import kkkombinator.DAO.DTO.ColorMapper;
import kkkombinator.DAO.DTO.UserDTO;
import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class EntityMapper {

    private final ColorMapper colorMapper;
    private final EntityManager manager;

    public EntityMapper(ColorMapper colorMapper, EntityManager manager) {
        this.colorMapper = colorMapper;
        this.manager = manager;
    }

    public CatDTO toCatDTO(Cat cat) {
        CatDTO catDataObject = new CatDTO();
        catDataObject.setId(cat.getId());
        catDataObject.setName(cat.getName());
        catDataObject.setColor(colorMapper.getStringFromColor(cat.getColor()));

        if (cat.getOwner() != null) {
            catDataObject.setUserId(cat.getOwner().getId());
        }

        catDataObject.setMyFriendsIds(cat.getFriendCatsIds().stream().map(Cat::getId).collect(Collectors.toSet()));
        return catDataObject;
    }

    public Cat toCat(CatDTO catDto) {
        Cat cat = new Cat();
        cat.setName(catDto.getName());
        cat.setId(catDto.getId());

        cat.setOwner(manager.getReference(User.class, catDto.getUserId()));
        cat.setColor(colorMapper.getColorFromString(catDto.getColor()));

        Set<Long> friendIds = catDto.getMyFriendsIds() != null ?
                catDto.getMyFriendsIds() : Collections.emptySet();

        cat.setFriendCatsIds(friendIds.stream().map(x -> manager.find(Cat.class, x)).collect(Collectors.toSet()));
        return cat;
    }

    public UserDTO toUserDTO(User user) {
        UserDTO userDataObject = new UserDTO();
        if (user == null) {
            return userDataObject;
        }
        userDataObject.setId(user.getId());
        userDataObject.setName(user.getName());
        userDataObject.setMyCatsIds(user.getMyCatsIds().stream().map(Cat::getId).collect(Collectors.toSet()));

        return userDataObject;
    }

    public User toUser(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());

        Set<Long> catsIds = userDTO.getMyCatsIds() != null ?
                userDTO.getMyCatsIds() : Collections.emptySet();

        user.setMyCatsIds(catsIds.stream().map(x -> manager.find(Cat.class, x)).collect(Collectors.toSet()));

        return user;
    }

    public Iterable<CatDTO> toCatDTOs(Iterable<Cat> cats) {
        Iterable<CatDTO> catDTOs = StreamSupport.stream(cats.spliterator(), false)
                .map(this::toCatDTO)
                .collect(Collectors.toList());
        return catDTOs;
    }

    public Iterable<Cat> toCats(Iterable<CatDTO> catDtos) {
        Iterable<Cat> catDTOs = StreamSupport.stream(catDtos.spliterator(), false)
                .map(this::toCat)
                .collect(Collectors.toList());
        return catDTOs;
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

