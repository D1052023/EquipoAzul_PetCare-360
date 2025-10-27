package edo.dosw.reto.dtos;

import lombok.Data;
import java.util.List;

@Data
public class CartDTO {
    private String id;
    private String clientId;
    private String clientName;
    private double total;
    private List<CartItemDTO> items;
}
