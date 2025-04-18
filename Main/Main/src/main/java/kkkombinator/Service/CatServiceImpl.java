package kkkombinator.Service;

import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Repo.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatServiceImpl implements CatService {

    private CatRepository CatRepository;

    @Autowired
    public CatServiceImpl(kkkombinator.DAO.Repo.CatRepository catRepository) {
        CatRepository = catRepository;
    }

    @Override
    public <S extends Cat> S save(S entity) {
        return CatRepository.save(entity);
    }

    @Override
    public <S extends Cat> Iterable<S> saveAll(Iterable<S> entities) {
        return CatRepository.saveAll(entities);
    }

    @Override
    public Optional<Cat> findById(Long id) {
        return CatRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return CatRepository.existsById(id);
    }

    @Override
    public Iterable<Cat> findAll() {
        return CatRepository.findAll();
    }

    @Override
    public Iterable<Cat> findAllById(Iterable<Long> ids) {
        return CatRepository.findAllById(ids);
    }

    @Override
    public long count() {
        return CatRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        CatRepository.deleteById(id);
    }

    @Override
    public void delete(Cat entity) {
        CatRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        CatRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends Cat> entities) {
        CatRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        CatRepository.deleteAll();
    }
}
