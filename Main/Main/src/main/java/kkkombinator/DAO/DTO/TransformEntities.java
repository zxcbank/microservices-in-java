package kkkombinator.DAO.DTO;

import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class TransformEntities {

    private final ModelMapper modelMapper;

    public TransformEntities(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public CatDTO mapPetDto(Cat cat) {
        return modelMapper.map(cat, CatDTO.class);
    }

    public Cat mapPetEntity(CatDTO catDto) {
        return modelMapper.map(catDto, Cat.class);
    }

    public UserDTO mapUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User mapPetOwnerEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}

