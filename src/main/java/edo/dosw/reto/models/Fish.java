package edo.dosw.reto.models;

import edo.dosw.reto.enums.Species;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Fish extends Pet {

    public Fish(String id, String name, String race, int age, Client owner) {
        super(id, name, Species.FISH, race, age, owner);
    }
}
