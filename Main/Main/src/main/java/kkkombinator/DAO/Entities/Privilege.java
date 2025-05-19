package kkkombinator.DAO.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Privilege(String name) {
        this.name = name;
    }

    public Privilege() {
    }

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
