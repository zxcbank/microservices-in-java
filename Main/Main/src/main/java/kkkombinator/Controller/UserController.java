package kkkombinator.Controller;

import kkkombinator.Controller.Exceptions.UserNotFoundException;
import kkkombinator.DAO.DTO.UserDTO;
import kkkombinator.Service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
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
                .orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.save(user);
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
        return userService.save(user);
    }

}
