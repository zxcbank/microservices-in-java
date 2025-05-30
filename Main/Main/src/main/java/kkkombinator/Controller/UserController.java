package kkkombinator.Controller;

import kkkombinator.Controller.Exceptions.UserNotFoundException;
import kkkombinator.DAO.DTO.UserDTO;
import kkkombinator.Service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final EntityMapper mapper;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/")
    public Iterable<UserDTO> findAll() {
        return mapper.toUserDTOs(userService.findAll());
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO userDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getName(),
                        userDTO.getPassword()
                )
        );
        if (!auth.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        return userDTO;
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        var foundUser = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        return mapper.toUserDTO(foundUser);
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO user) {
        return mapper.toUserDTO(userService.register(mapper.toUser(user)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@RequestBody UserDTO user) {

        userService.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        return mapper.toUserDTO(userService.save(mapper.toUser(user)));
    }
}
