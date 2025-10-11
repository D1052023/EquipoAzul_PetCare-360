package edo.dosw.reto.services;


import edo.dosw.reto.models.Pet;
import edo.dosw.reto.repositories.PetRepository;
import edo.dosw.reto.services.PetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository repository;

    public PetServiceImpl(PetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pet register(Pet pet) {
        return repository.save(pet);
    }

    @Override
    public Pet findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
    }

    @Override
    public List<Pet> findAll() {
        return repository.findAll();
    }
}
