package edo.dosw.reto.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "invoices")
public class MedicalInvoice extends Invoice {
    private Appointment appointment;

    @Override
    public String getType() {
        return "CONSULTA_MEDICA";
    }
}
