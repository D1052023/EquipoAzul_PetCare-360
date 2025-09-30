package edo.dosw.reto.EquipoAzul_PetCare_360.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;
    private Date date;
    private String time;
    private Pet pets;
    private String veterinaryId;
}
