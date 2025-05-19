package kkkombinator.DAO.Repo;

import kkkombinator.DAO.Entities.Cat;
import kkkombinator.DAO.Entities.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
}
