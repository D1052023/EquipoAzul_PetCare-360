package edo.dosw.reto.services;

import edo.dosw.reto.models.Product;
import edo.dosw.reto.enums.ProductCategory;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product update(String id, Product product);
    Product findById(String id);
    void delete(String id);
    List<Product> findAll();
    List<Product> findByCategory(ProductCategory category);
    List<Product> searchByName(String name);
}
