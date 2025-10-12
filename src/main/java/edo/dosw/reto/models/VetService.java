package edo.dosw.reto.models;

import edo.dosw.reto.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VetService {
    private String id;
    private ServiceType type;
    private String description;
}
