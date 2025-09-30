package edo.dosw.reto.EquipoAzul_PetCare_360.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {
    private String id;
    private String name;
    private String dosage;
}
