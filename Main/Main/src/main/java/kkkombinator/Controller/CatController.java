package kkkombinator.Controller;

import jakarta.persistence.EntityNotFoundException;
import kkkombinator.DAO.Entities.Cat;
import kkkombinator.Service.CatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import kkkombinator.Controller.Exceptions.*;

import java.util.Objects;


@RestController
@RequestMapping("/api/cats")
public class CatController {

    @Autowired
    private CatServiceImpl catService;

    @GetMapping
    public Iterable<Cat> findAll() {
        return catService.findAll();
    }


    @GetMapping("/{id}")
    public Cat findById(@PathVariable Long id) {
        return catService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cat not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cat create(@RequestBody Cat cat) {
        return catService.save(cat);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        catService.findById(id)
                .orElseThrow();
        catService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Cat updateCat(@RequestBody Cat cat, @PathVariable Long id) {
        if (!Objects.equals(cat.getId(), id)) {
            throw new CatIdMismatchException("miss catId");
        }
        catService.findById(id)
                .orElseThrow();
        return catService.save(cat);
    }

}
