package edo.dosw.reto.models;


import edo.dosw.reto.enums.Species;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Bird extends Pet {

    public Bird(String id, String name, String race, int age, Client owner) {
        super(id, name, Species.BIRD, race, age, owner);
    }
}
