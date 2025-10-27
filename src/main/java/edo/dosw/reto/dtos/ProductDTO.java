package edo.dosw.reto.dtos;
import lombok.Data;

@Data
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stock;
}
