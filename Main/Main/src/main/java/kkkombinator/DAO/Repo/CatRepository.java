package kkkombinator.DAO.Repo;

import kkkombinator.DAO.Entities.Cat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends CrudRepository<Cat, Long> {
}
