package kkkombinator.Service;

import kkkombinator.DAO.DTO.CatDTO;
import kkkombinator.DAO.DTO.ColorMapper;
import kkkombinator.DAO.DTO.UserDTO;
import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Entities.User;
import kkkombinator.DAO.Repo.CatRepository;
import kkkombinator.DAO.Repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class TransformEntities {

    private final UserRepository userRepository;
    private final CatRepository catRepository;
    private final ColorMapper colorMapper;

    public TransformEntities(UserRepository userRepository, CatRepository catRepository, ColorMapper colorMapper) {
        this.userRepository = userRepository;
        this.catRepository = catRepository;
        this.colorMapper = colorMapper;
    }

    public CatDTO mapCatDto(Cat cat) {
        CatDTO tmpdto = new CatDTO();
        tmpdto.setId(cat.getId());
        tmpdto.setName(cat.getName());
        tmpdto.setColor(colorMapper.getStringFromColor(cat.getColor()));
        if (cat.getOwner() != null) {
            tmpdto.setUserId(cat.getOwner().getId());
        }

        tmpdto.setMyFriendsIds(cat.getFriendCats().stream().map(Cat::getId).collect(Collectors.toSet()));
        return tmpdto;
    }

    public Cat mapCat(CatDTO catDto) {
        Cat tmp_cat = new Cat();
        tmp_cat.setName(catDto.getName());
        tmp_cat.setId(catDto.getId());

        Optional<User> optUser = userRepository.findById(catDto.getUserId());
        optUser.ifPresent(tmp_cat::setOwner);

        tmp_cat.setColor(colorMapper.getColorFromString(catDto.getColor()));

        Set<Long> friendIds = catDto.getMyFriendsIds() != null ?
                catDto.getMyFriendsIds() : Collections.emptySet();

        Set<Cat> friendSet = StreamSupport.stream(
                catRepository.findAllById(friendIds).spliterator(), false
        ).collect(Collectors.toSet());

        tmp_cat.setFriendCats(friendSet);
        return tmp_cat;
    }

    public UserDTO mapUserDto(User user) {
        UserDTO tmpdto = new UserDTO();
        if (user == null) {
            return tmpdto;
        }
        tmpdto.setId(user.getId());
        tmpdto.setName(user.getName());


        tmpdto.setMyCatsIds(user.getCats().stream().map(Cat::getId).collect(Collectors.toSet()));
        return tmpdto;
    }

    public User mapUser(UserDTO userDTO) {
        User tmpuser = new User();

        tmpuser.setId(userDTO.getId());
        tmpuser.setName(userDTO.getName());

        Set<Long> catsIds = userDTO.getMyCatsIds() != null ?
                userDTO.getMyCatsIds() : Collections.emptySet();

        Set<Cat> friendSet = StreamSupport.stream(
                catRepository.findAllById(catsIds).spliterator(), false
        ).collect(Collectors.toSet());

        tmpuser.setCats(friendSet);

        return tmpuser;
    }

    public Iterable<CatDTO> mapCatDtos(Iterable<Cat> cats) {
        Iterable<CatDTO> catDTOs = StreamSupport.stream(cats.spliterator(), false)
                .map(this::mapCatDto)
                .collect(Collectors.toList());
        return catDTOs;
    }

    public Iterable<Cat> mapCats(Iterable<CatDTO> catDtos) {
        Iterable<Cat> catDTOs = StreamSupport.stream(catDtos.spliterator(), false)
                .map(this::mapCat)
                .collect(Collectors.toList());
        return catDTOs;
    }

    public Iterable<UserDTO> mapUserDtos(Iterable<User> users) {
        Iterable<UserDTO> userDTOs = StreamSupport.stream(users.spliterator(), false)
                .map(this::mapUserDto)
                .collect(Collectors.toList());
        return userDTOs;
    }

    public Iterable<User> mapUsers(Iterable<UserDTO> userDTOs) {
        Iterable<User> users = StreamSupport.stream(userDTOs.spliterator(), false)
                .map(this::mapUser)
                .collect(Collectors.toList());
        return users;
    }
}

