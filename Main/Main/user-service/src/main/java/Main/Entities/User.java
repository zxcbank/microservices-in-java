package Main.Entities;

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
    @CollectionTable(name="usersCats", joinColumns = @JoinColumn(name="id"))
    private Collection<Long> myCatsIds = new HashSet<>();

    @Column(name= "pass")
    private String password;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usersRoles",
            joinColumns = @JoinColumn(
                    name = "userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roleId", referencedColumnName = "id"))
    private Collection<Role> roles;


//    public void addRole(Role role) {
//        roles.add(role);
//        role.getUsers().add(this);
//    }
//
//    public void removeRole(Role role) {
//        roles.remove(role);
//        role.getUsers().remove(this);
//    }
//
//    @PreRemove
//    private void removeRolesAssociations() {
//        for (Role role : new HashSet<>(roles)) {
//            removeRole(role);
//        }
//    }
}