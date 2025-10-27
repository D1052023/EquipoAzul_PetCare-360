package edo.dosw.reto.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "invoices")
public class ProductInvoice extends Invoice {
    private List<CartItem> items;

    @Override
    public String getType() {
        return "COMPRA_PRODUCTOS";
    }
}
