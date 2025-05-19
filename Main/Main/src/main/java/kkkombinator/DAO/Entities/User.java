package kkkombinator.DAO.Entities;

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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Cat> myCatsIds = new HashSet<>();

    // Spring Security info
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToMany
    @JoinTable(
            name = "usersRoles",
            joinColumns = @JoinColumn(
                    name = "userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roleId", referencedColumnName = "id"))
    private Collection<Role> roles;
}