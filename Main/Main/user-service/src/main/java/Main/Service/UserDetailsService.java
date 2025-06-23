package Main.Service;


import Main.Repository.RoleRepository;
import Main.Repository.UserRepository;
import Main.Entities.Privilege;
import Main.Entities.Role;
import Main.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userDetailsService")
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByName(username);

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Важно!
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
