package edo.dosw.reto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "invoices")
public abstract class Invoice {
    @Id
    private String id;
    private Client client;
    private double total;
    private LocalDateTime createdAt;

    public abstract String getType();
}
