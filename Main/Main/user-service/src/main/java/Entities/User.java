package Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
@Entity
@Table(name= "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "userName")
    private String name;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @ElementCollection
    @CollectionTable(name="users", joinColumns = @JoinColumn(name="id"))
    private Collection<Long> myCatsIds = new HashSet<>();

    @Column(name= "pass")
    private String password;


    @ManyToMany
    @JoinTable(
            name = "usersRoles",
            joinColumns = @JoinColumn(
                    name = "userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roleId", referencedColumnName = "id"))
//    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Role> roles;
}