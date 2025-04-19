package kkkombinator.Controller;

import jakarta.persistence.EntityNotFoundException;
import kkkombinator.DAO.DTO.CatDTO;
import kkkombinator.Service.CatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kkkombinator.Controller.Exceptions.*;

import java.util.Objects;


@RestController
@RequestMapping("/api/cats")
public class CatController {
    private CatServiceImpl catService;

    @Autowired
    public CatController(CatServiceImpl catService) {
        this.catService = catService;
    }

    @Value("${spring.application.name}")
    String appName;

    @GetMapping
    public Iterable<CatDTO> findAll() {
        return catService.findAll();
    }


    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/{id}")
    public CatDTO findById(@PathVariable Long id) {
        return catService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cat not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatDTO create(@RequestBody CatDTO cat) {
        return catService.save(cat);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        catService.findById(id)
                .orElseThrow();

        catService.deleteById(id);
    }

    @PutMapping("/{id}")
    public CatDTO updateCat(@RequestBody CatDTO cat, @PathVariable Long id) {
        if (!Objects.equals(cat.getId(), id)) {
            throw new CatIdMismatchException("miss catId");
        }
        catService.findById(id)
                .orElseThrow();
        return catService.save(cat);
    }

}
