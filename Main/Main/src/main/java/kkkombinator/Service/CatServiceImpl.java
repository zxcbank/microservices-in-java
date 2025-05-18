package kkkombinator.Service;

import kkkombinator.Controller.EntityMapper;
import kkkombinator.Controller.Exceptions.UserNotFoundException;
import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Repo.CatRepository;
import kkkombinator.DAO.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CatServiceImpl {

    private CatRepository catRepository;
    private EntityMapper transformer;
    private UserRepository userRepository;

    @Autowired
    public CatServiceImpl(kkkombinator.DAO.Repo.CatRepository catRepository, UserRepository userRepository, EntityMapper transformer) {
        this.catRepository = catRepository;
        this.userRepository = userRepository;
        this.transformer = transformer;
    }

    public Cat save(Cat entity) {
        if (!userRepository.existsById(entity.getOwner().getId())) {
            throw new UserNotFoundException("!!!!!");
        }
        return catRepository.save(entity);
    }

    public Iterable<Cat> saveAll(Iterable<Cat> entities) {
        return catRepository.saveAll(entities);
    }

    public Optional<Cat> findById(Long id) {
        Optional<Cat> findedCat = catRepository.findById(id);
        return findedCat;
    }

    public boolean existsById(Long id) {
        return catRepository.existsById(id);
    }

    public Iterable<Cat> findAll() {
        return catRepository.findAll();
    }

    public Iterable<Cat> findAllById(Iterable<Long> ids) {
        return catRepository.findAllById(ids);
    }

    public long count() {
        return catRepository.count();
    }

    public void deleteById(Long id) {
        catRepository.deleteById(id);
    }

    public void delete(Cat entity) {
        catRepository.delete(entity);
    }

    public void deleteAllById(Iterable<Long> ids) {
        catRepository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<Cat> entities) {
        catRepository.deleteAll(entities);
    }

    public void deleteAll() {
        catRepository.deleteAll();
    }
}
