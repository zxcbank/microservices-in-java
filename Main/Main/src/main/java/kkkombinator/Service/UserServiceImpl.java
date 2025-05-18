package kkkombinator.Service;

import jakarta.transaction.TransactionScoped;
import kkkombinator.DAO.Entities.User;
import kkkombinator.DAO.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User entity) {
        return userRepository.save(entity);
    }

    public Iterable<User> saveAll(Iterable<User> entities) {
        return userRepository.saveAll(entities);
    }

    public Optional<User> findById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.map(user -> optUser.get());
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public Iterable<User> findAllById(Iterable<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }

    public void deleteAllById(Iterable<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<User> entities) {
        userRepository.deleteAll(entities);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
