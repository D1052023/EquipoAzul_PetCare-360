package edo.dosw.reto.services;


import edo.dosw.reto.models.Pet;
import java.util.List;

public interface PetService {
    Pet register(Pet pet);
    Pet findById(String id);
    List<Pet> findAll();
}
