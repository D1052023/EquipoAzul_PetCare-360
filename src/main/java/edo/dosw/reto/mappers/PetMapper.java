package edo.dosw.reto.mappers;

import edo.dosw.reto.dtos.PetDTO;
import edo.dosw.reto.enums.Species;
import edo.dosw.reto.models.*;

public final class PetMapper {

    private PetMapper() {}

    public static PetDTO toDTO(Pet pet) {
        if (pet == null) return null;
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setSpecies(pet.getSpecies() != null ? pet.getSpecies().name() : null);
        dto.setRace(pet.getRace());
        dto.setAge(pet.getAge());
        if (pet.getOwner() != null) {
            dto.setOwnerId(pet.getOwner().getId());
            dto.setOwnerName(pet.getOwner().getName());
            dto.setOwnerPhone(pet.getOwner().getPhone());
            dto.setOwnerEmail(pet.getOwner().getEmail());
        }
        return dto;
    }

    public static Pet toEntity(PetDTO dto) {
        if (dto == null) return null;
        if (dto.getSpecies() == null || dto.getSpecies().isBlank()) {
            throw new IllegalArgumentException("El campo 'species' no puede estar vacío");
        }

        Species s;
        try {
            s = Species.valueOf(dto.getSpecies().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de especie inválido: " + dto.getSpecies());
        }

        Client owner = new Client(dto.getOwnerId(), dto.getOwnerName(), dto.getOwnerPhone(), dto.getOwnerEmail());

        switch (s) {
            case DOG:
                return new Dog(dto.getId(), dto.getName(), dto.getRace(), dto.getAge(), owner);
            case CAT:
                return new Cat(dto.getId(), dto.getName(), dto.getRace(), dto.getAge(), owner);
            case BIRD:
                return new Bird(dto.getId(), dto.getName(), dto.getRace(), dto.getAge(), owner);
            case FISH:
                return new Fish(dto.getId(), dto.getName(), dto.getRace(), dto.getAge(), owner);
            case RABBIT:
                return new Rabbit(dto.getId(), dto.getName(), dto.getRace(), dto.getAge(), owner);
            default:
                throw new IllegalArgumentException("Species no soportada: " + s);
        }
    }
}
