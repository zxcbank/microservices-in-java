package kkkombinator.DAO.DTO;

import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
public class TransformEntities {

    private final ModelMapper modelMapper;

    public TransformEntities(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public CatDTO mapCatDto(Cat cat) {
        return modelMapper.map(cat, CatDTO.class);
    }

    public Cat mapCat(CatDTO catDto) {
        return modelMapper.map(catDto, Cat.class);
    }

    public UserDTO mapUserDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User mapUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public Iterable<CatDTO> mapCatDtos(Iterable<Cat> cats) {
        ModelMapper modelMapper = new ModelMapper();

        Iterable<CatDTO> catDTOs = StreamSupport.stream(cats.spliterator(), false)
                .map(cat -> modelMapper.map(cat, CatDTO.class))
                .collect(Collectors.toList());
        return catDTOs;
    }

    public Iterable<Cat> mapCats(Iterable<CatDTO> catDtos) {
        ModelMapper modelMapper = new ModelMapper();

        Iterable<Cat> catDTOs = StreamSupport.stream(catDtos.spliterator(), false)
                .map(cat -> modelMapper.map(cat, Cat.class))
                .collect(Collectors.toList());
        return catDTOs;
    }

    public Iterable<UserDTO> mapUserDtos(Iterable<User> users) {
        ModelMapper modelMapper = new ModelMapper();

        Iterable<UserDTO> userDTOs = StreamSupport.stream(users.spliterator(), false)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return userDTOs;
    }

    public Iterable<User> mapUsers(Iterable<UserDTO> userDTOs) {
        ModelMapper modelMapper = new ModelMapper();

        Iterable<User> users = StreamSupport.stream(userDTOs.spliterator(), false)
                .map(userDto -> modelMapper.map(userDto, User.class))
                .collect(Collectors.toList());
        return users;
    }
}

