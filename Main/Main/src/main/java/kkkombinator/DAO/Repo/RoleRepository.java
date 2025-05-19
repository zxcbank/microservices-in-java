package kkkombinator.DAO.Repo;

import kkkombinator.DAO.Entities.Privilege;
import kkkombinator.DAO.Entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
