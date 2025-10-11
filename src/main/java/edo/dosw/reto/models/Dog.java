package edo.dosw.reto.models;

import edo.dosw.reto.enums.Species;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Dog extends Pet {

    public Dog(String id, String name, String race, int age, Client owner) {
        super(id, name, Species.DOG, race, age, owner);
    }
}
