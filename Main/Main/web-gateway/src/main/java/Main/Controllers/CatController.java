package Main.Controllers;

import Main.Models.CatDTO;
import Main.Service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/cats/")
@RequiredArgsConstructor
public class CatController {
    
    private final KafkaService kafkaService;

    @GetMapping
    public List<CatDTO> findAll() throws ExecutionException, InterruptedException {
        try {
            return kafkaService.sendFindAllCatsMessage();
        } catch (ExecutionException e) {
            System.out.println("some exception in kafkaService (NOT EXECUTED)");
        } catch (InterruptedException e) {
            System.out.println("some exception in kafkaService (INTERRUPTED)");
        }
        return null;
    }

    //    @PostMapping("/login")
//    public CatDTO login(@RequestBody CatDTO dto) {
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatDTO create(@RequestBody CatDTO dto) {
        try {
            return kafkaService.sendSaveCatMessage(dto);
        } catch (ExecutionException e) {
            System.out.println("some exception in kafkaService (NOT EXECUTED)");
        } catch (InterruptedException e) {
            System.out.println("some exception in kafkaService (INTERRUPTED)");
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public CatDTO delete(@PathVariable Long id) {
        try {
            return kafkaService.sendDeleteCatByIdMessage(id);
        } catch (ExecutionException e) {
            System.out.println("some exception in kafkaService (NOT EXECUTED)");
        } catch (InterruptedException e) {
            System.out.println("some exception in kafkaService (INTERRUPTED)");
        }
        return null;

    }

    @PutMapping("/{id}")
    public CatDTO updateCat(@RequestBody CatDTO dto) {
        try {
            return kafkaService.sendUpdateCatByIdMessage(dto);
        } catch (ExecutionException e) {
            System.out.println("some exception in kafkaService (NOT EXECUTED)");
        } catch (InterruptedException e) {
            System.out.println("some exception in kafkaService (INTERRUPTED)");
        }
        return null;
    }
}