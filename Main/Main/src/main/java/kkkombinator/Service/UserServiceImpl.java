package kkkombinator.Service;

import jakarta.transaction.TransactionScoped;
import kkkombinator.DAO.Entities.User;
import kkkombinator.DAO.Repo.RoleRepository;
import kkkombinator.DAO.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User entity) {
        return userRepository.save(entity);
    }

    public User register(User entity) {
        System.out.println(entity.toString());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
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
