package edo.dosw.reto.mappers;


import edo.dosw.reto.dtos.ProductDTO;
import edo.dosw.reto.enums.ProductCategory;
import edo.dosw.reto.models.Product;

public class ProductMapper {
    private ProductMapper() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory().getDisplayName());
        dto.setStock(product.getStock());
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        return new Product(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                ProductCategory.fromDisplayName(dto.getCategory()),
                dto.getStock()
        );
    }
}
