package edo.dosw.reto.models;



import edo.dosw.reto.enums.Species;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Cat extends Pet {

    public Cat(String id, String name, String race, int age, Client owner) {
        super(id, name, Species.CAT, race, age, owner);
    }
}
