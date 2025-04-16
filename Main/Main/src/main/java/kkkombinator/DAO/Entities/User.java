package kkkombinator.DAO.Entities;

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

    @NotNull
    @Column(name= "username")
    @Getter
    @Setter
    private String name;

    @NotNull
    @Column(name = "birthDate")
    @Getter
    @Setter
    private LocalDate birthDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cat> cats = new HashSet<>();
    // OneToMany mappedby owner <=> 1 владелец , у него много котов.
    // orhpanRemoval = true <=> наличие владельца это необходимое условие существование кота.
    // cascade = CascadeType.ALL <=> одновременное изменение данных в разных таблицах (как я понял если условно имя владельца поменяется, то и в таблице с котами тоже самое будет)

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