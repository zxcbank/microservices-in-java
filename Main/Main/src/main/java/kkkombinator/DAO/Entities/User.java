package kkkombinator.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name= "username")
    @Getter
    @Setter
    private String name;

    @Column(name = "birthDate")
    @Getter
    @Setter
    private LocalDate birthDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cat> cats = new HashSet<>();

    public User(String name, LocalDate date) {
        this.name = name;
        birthDate = date;
    }

    public void addCat(Cat cat) {
        this.cats.add(cat);
        cat.setOwner(this);
    }

    public void deleteCat(Cat cat) {
        this.cats.remove(cat);
        cat.setOwner(null);
    }
}