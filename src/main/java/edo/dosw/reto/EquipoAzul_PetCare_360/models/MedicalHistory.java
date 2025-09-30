package edo.dosw.reto.EquipoAzul_PetCare_360.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "medical_histories")
public class MedicalHistory {
    @Id
    private String id;
    private String animalId;
    private List<Diagnosis> diagnoses;
}
