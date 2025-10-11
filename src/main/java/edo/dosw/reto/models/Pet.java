package edo.dosw.reto.models;

import edo.dosw.reto.enums.Species;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pet {
    private String id;
    private String name;
    private Species species;
    private String race;
    private int age;
    private Client owner;
}
