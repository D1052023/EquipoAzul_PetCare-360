package edo.dosw.reto.repositories;


import edo.dosw.reto.models.Pet;
import edo.dosw.reto.repositories.PetRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PetRepositoryImpl implements PetRepository {

    private final Map<String, Pet> data = new ConcurrentHashMap<>();

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) pet.setId(UUID.randomUUID().toString());
        data.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Optional<Pet> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Pet> findAll() {
        return new ArrayList<>(data.values());
    }
}
