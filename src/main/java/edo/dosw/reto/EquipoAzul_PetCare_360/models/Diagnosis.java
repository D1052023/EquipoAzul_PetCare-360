package edo.dosw.reto.EquipoAzul_PetCare_360.models;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnosis {
    private String id;
    private String description;
    private Date date;
    private Treatment treatment;
}
