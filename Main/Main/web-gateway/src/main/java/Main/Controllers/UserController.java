package Main.Controllers;


import Main.Models.UserDTO;
import Main.Service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/users/")
@RequiredArgsConstructor
public class UserController {

//    private final AuthenticationManager authenticationManager;

    private final KafkaService kafkaService;

    @GetMapping
    public List<UserDTO> findAll() throws ExecutionException, InterruptedException {
        try {
            return kafkaService.sendFindAllUsersMessage();
        } catch (ExecutionException e) {
            System.out.println("some exception in kafkaService (NOT EXECUTED)");
        } catch (InterruptedException e) {
            System.out.println("some exception in kafkaService (INTERRUPTED)");
        }
        return null;
    }

//    @PostMapping("/login")
//    public UserDTO login(@RequestBody UserDTO dto) {
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        dto.getName(),
//                        dto.getPassword()
//                )
//        );
//        if (!auth.isAuthenticated()) {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        return dto;
//    }
//
    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO dto) {
        try {
            return kafkaService.sendSaveUserMessage(dto);
        } catch (ExecutionException e) {
            System.out.println("some exception in kafkaService (NOT EXECUTED)");
        } catch (InterruptedException e) {
            System.out.println("some exception in kafkaService (INTERRUPTED)");
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public UserDTO delete(@PathVariable Long id) {
        try {
            return kafkaService.sendDeleteUserByIdMessage(id);
        } catch (ExecutionException e) {
            System.out.println("some exception in kafkaService (NOT EXECUTED)");
        } catch (InterruptedException e) {
            System.out.println("some exception in kafkaService (INTERRUPTED)");
        }
        return null;

    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@RequestBody UserDTO dto) {
        try {
            return kafkaService.sendUpdateUserByIdMessage(dto);
        } catch (ExecutionException e) {
            System.out.println("some exception in kafkaService (NOT EXECUTED)");
        } catch (InterruptedException e) {
            System.out.println("some exception in kafkaService (INTERRUPTED)");
        }
        return null;
    }
}