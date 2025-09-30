package edo.dosw.reto.EquipoAzul_PetCare_360.models;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Treatment {
    private String id;
    private String description;
    private int durationDays;
    private List<Medicine> medicines;
}
