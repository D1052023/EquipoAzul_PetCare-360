package edo.dosw.reto.controller;

import edo.dosw.reto.dtos.ProductDTO;
import edo.dosw.reto.enums.ProductCategory;
import edo.dosw.reto.mappers.ProductMapper;
import edo.dosw.reto.models.Product;
import edo.dosw.reto.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product data cannot be null.");
        }
        Product saved = service.create(ProductMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDTO(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable String id, @RequestBody ProductDTO dto) {
        Product updated = service.update(id, ProductMapper.toEntity(dto));
        return ResponseEntity.ok(ProductMapper.toDTO(updated));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> list = service.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable String id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(ProductMapper.toDTO(product));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable String category) {
        ProductCategory cat = ProductCategory.fromDisplayName(category);
        List<ProductDTO> list = service.findByCategory(cat)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchByName(@RequestParam String name) {
        List<ProductDTO> list = service.searchByName(name)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
