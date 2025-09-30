package edo.dosw.reto.EquipoAzul_PetCare_360.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "pets")
public abstract class Pet {
    @Id
    private String id;
    private String name;
    private int age;
    private String species;
}
