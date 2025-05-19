package kkkombinator.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
@Entity
@Table(name = "cats")
@Data
public class Cat {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, name="name")
    private String name;

    @Column(name="birthDate")
    private LocalDate birthDate;

    @Column(name="catType")
    private String catType;

    @Column(name="color")
    private Color color;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "catFriends",
            joinColumns = @JoinColumn(name = "catId"),
            inverseJoinColumns = @JoinColumn(name = "friendId")
    )

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<Cat> friendCats = new HashSet<>();
}
