package kkkombinator.Service;

import kkkombinator.DAO.DTO.UserDTO;
import kkkombinator.DAO.Entities.User;
import kkkombinator.DAO.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private UserRepository userRepository;
    private TransformEntities transformer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TransformEntities transformer) {
        this.userRepository = userRepository;
        this.transformer = transformer;
    }

    @Transactional
    public UserDTO save(UserDTO entity) {

        return transformer.mapUserDto(userRepository.save(transformer.mapUser(entity)));
    }

    public Iterable<UserDTO> saveAll(Iterable<UserDTO> entities) {
        return transformer.mapUserDtos(userRepository.saveAll(transformer.mapUsers(entities)));
    }

    public Optional<UserDTO> findById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.map(user -> transformer.mapUserDto(optUser.get()));
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public Iterable<UserDTO> findAll() {
        return transformer.mapUserDtos(userRepository.findAll());
    }

    public Iterable<UserDTO> findAllById(Iterable<Long> ids) {
        return transformer.mapUserDtos(userRepository.findAllById(ids));
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(UserDTO entity) {
        userRepository.delete(transformer.mapUser(entity));
    }

    public void deleteAllById(Iterable<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<UserDTO> entities) {
        userRepository.deleteAll(transformer.mapUsers(entities));
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
