package Repository;


import Entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {


    Role findByName(String roleAdmin);
}
