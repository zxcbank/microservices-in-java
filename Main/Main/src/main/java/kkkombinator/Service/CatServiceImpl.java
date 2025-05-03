package kkkombinator.Service;

import kkkombinator.DAO.DTO.CatDTO;
import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Repo.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatServiceImpl {

    private CatRepository catRepository;
    private TransformEntities transformer;

    @Autowired
    public CatServiceImpl(kkkombinator.DAO.Repo.CatRepository catRepository, TransformEntities transformer) {
        this.catRepository = catRepository;
        this.transformer = transformer;
    }

    @Transactional
    public CatDTO save(CatDTO entity) {
        return transformer.mapCatDto(catRepository.save(transformer.mapCat(entity)));
    }

    public Iterable<CatDTO> saveAll(Iterable<CatDTO> entities) {

        return transformer.mapCatDtos(catRepository.saveAll(transformer.mapCats(entities)));
    }

    public Optional<CatDTO> findById(Long id) {
        Optional<Cat> optCat = catRepository.findById(id);
        return optCat.map(cat -> transformer.mapCatDto(cat));
    }

    public boolean existsById(Long id) {
        return catRepository.existsById(id);
    }

    public Iterable<CatDTO> findAll() {
        return transformer.mapCatDtos(catRepository.findAll());
    }

    public Iterable<CatDTO> findAllById(Iterable<Long> ids) {
        return transformer.mapCatDtos(catRepository.findAllById(ids));
    }

    public long count() {
        return catRepository.count();
    }

    public void deleteById(Long id) {
        catRepository.deleteById(id);
    }

    public void delete(CatDTO entity) {
        catRepository.delete(transformer.mapCat(entity));
    }

    public void deleteAllById(Iterable<Long> ids) {
        catRepository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<CatDTO> entities) {
        catRepository.deleteAll(transformer.mapCats(entities));
    }

    public void deleteAll() {
        catRepository.deleteAll();
    }
}
