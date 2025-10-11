package edo.dosw.reto.repositories;


import edo.dosw.reto.models.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Pet save(Pet pet);
    Optional<Pet> findById(String id);
    List<Pet> findAll();
}
