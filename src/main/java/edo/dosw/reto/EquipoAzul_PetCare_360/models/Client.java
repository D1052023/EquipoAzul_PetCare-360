package edo.dosw.reto.EquipoAzul_PetCare_360.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "clients")
public class Client {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
    private List<Pet> pets;
}
