package kkkombinator.DAO.Repo;

import kkkombinator.DAO.Entities.Cat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatRepository extends CrudRepository   <Cat, Long> {
    <S extends Cat> S save(S entity);

    <S extends Cat> Iterable<S> saveAll(Iterable<S> entities);

    Optional<Cat> findById(Long id);

    boolean existsById(Long id);

    Iterable<Cat> findAll();

    Iterable<Cat> findAllById(Iterable<Long> ids);

    long count();

    void deleteById(Long id);

    void delete(Cat entity);

    void deleteAllById(Iterable<? extends Long> ids);

    void deleteAll(Iterable<? extends Cat> entities);

    void deleteAll();
}
