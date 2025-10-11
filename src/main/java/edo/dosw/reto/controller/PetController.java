package edo.dosw.reto.controller;


import edo.dosw.reto.dtos.PetDTO;
import edo.dosw.reto.mappers.PetMapper;
import edo.dosw.reto.models.Pet;
import edo.dosw.reto.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mascotas")
public class PetController {

    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PetDTO> registerPet(@RequestBody PetDTO petDTO) {
        Pet saved = service.register(PetMapper.toEntity(petDTO));
        return ResponseEntity.ok(PetMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable String id) {
        Pet found = service.findById(id);
        return ResponseEntity.ok(PetMapper.toDTO(found));
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> pets = service.findAll().stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pets);
    }
}
