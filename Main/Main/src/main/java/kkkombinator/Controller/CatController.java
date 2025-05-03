package kkkombinator.Controller;

import kkkombinator.DAO.DTO.CatDTO;
import kkkombinator.Service.CatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import kkkombinator.Controller.Exceptions.*;


@RestController
@RequestMapping("/cats")
@RequiredArgsConstructor
public class CatController {
    private CatServiceImpl catService;

    @Autowired
    public CatController(CatServiceImpl catService) {
        this.catService = catService;
    }

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public Iterable<CatDTO> findAll() {
        return catService.findAll();
    }


    @GetMapping("/{id}")
    public CatDTO findById(@PathVariable Long id) {

        return catService.findById(id)
                .orElseThrow(() -> new CatNotFoundException("Cat not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatDTO create(@RequestBody CatDTO cat) {
        return catService.save(cat);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        catService.findById(id)
                .orElseThrow(() -> new CatNotFoundException("not found cat"));

        catService.deleteById(id);
    }

    @PutMapping
    public CatDTO updateCat(@RequestBody CatDTO cat) {
        catService.findById(cat.getId())
                .orElseThrow(() -> new CatNotFoundException("not found cat"));
        return catService.save(cat);
    }
}
