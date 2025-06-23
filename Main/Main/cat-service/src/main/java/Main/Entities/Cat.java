package Main.Entities;

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

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

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
