package kkkombinator.DAO.Repositories;

import kkkombinator.DAO.Entities.Cat;

import java.util.List;

public interface CatRepository extends CrudRepository<Cat, Long> {
    List<Cat> findByTitle(String title);
}