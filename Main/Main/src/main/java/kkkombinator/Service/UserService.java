package kkkombinator.Service;

import kkkombinator.DAO.Entities.User;

import java.util.Optional;

public interface UserService {
    <S extends User> S save(S entity);

    <S extends User> Iterable<S> saveAll(Iterable<S> entities);

    Optional<User> findById(Long id);

    boolean existsById(Long id);

    Iterable<User> findAll();

    Iterable<User> findAllById(Iterable<Long> ids);

    long count();

    void deleteById(Long id);

    void delete(User entity);

    void deleteAllById(Iterable<? extends Long> ids);

    void deleteAll(Iterable<? extends User> entities);

    void deleteAll();
}
