package kkkombinator.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Entity
@Table(name = "cats")
@Data
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable= false, name="name")
    private String name;

    @Column(name="birthDate")
    private LocalDate birthDate;

    @Column(name="CatType")
    private String catType;

    @Column(name="Color")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY) // Загрузка отложена до момента явного образения к полю
    @JoinColumn(name = "user_id")
    private User owner;


    @ManyToMany
    @JoinTable(
            name = "cat_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Cat> friendCats = new HashSet<>();
    // EqualsAndHashCode.Exclude  <=> аннотация для выполнения контракта с коллекией хэшсет, тем более там изменяемые поля, исключает в т ч это поле из hashCode
    // ToString.Exclude <=> исключает поле из ToString (в данном случае возможно для отсутствия циклических ссылок по котам)

    public Cat(String name, String catType, Color color, LocalDate date, User owner) {
        this.name=name;
        this.catType=catType;
        this.color=color;
        this.birthDate = date;
        this.owner = owner;

    }
}
