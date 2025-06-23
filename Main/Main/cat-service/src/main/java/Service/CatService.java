package Service;


import Entities.Cat;
import Repository.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CatService {

    private CatRepository userRepository;

    @Autowired
    public CatService(CatRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Cat save(Cat entity) {
        return userRepository.save(entity);
    }

    public Iterable<Cat> saveAll(Iterable<Cat> entities) {
        return userRepository.saveAll(entities);
    }

    public Optional<Cat> findById(Long id) {
        Optional<Cat> optCat = userRepository.findById(id);
        return optCat.map(user -> optCat.get());
    }

    public Cat update(Cat entity) {
        userRepository.save(entity);
        return entity;
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public Iterable<Cat> findAll() {
        return userRepository.findAll();
    }

    public Iterable<Cat> findAllById(Iterable<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(Cat entity) {
        userRepository.delete(entity);
    }

    public void deleteAllById(Iterable<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<Cat> entities) {
        userRepository.deleteAll(entities);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}

