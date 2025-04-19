package kkkombinator.Controller;

import jakarta.persistence.EntityNotFoundException;
import kkkombinator.Controller.Exceptions.UserIdMismatchException;
import kkkombinator.DAO.DTO.UserDTO;
import kkkombinator.DAO.Entities.User;
import kkkombinator.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    private UserServiceImpl userService;

    @GetMapping
    public Iterable<UserDTO> findAll() {
        return userService.findAll();
    }


    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cat not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.findById(id)
                .orElseThrow();
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@RequestBody UserDTO user, @PathVariable Long id) {
        if (!Objects.equals(user.getId(), id)) {
            throw new UserIdMismatchException("miss userId");
        }
        userService.findById(id)
                .orElseThrow();
        return userService.save(user);
    }

}
