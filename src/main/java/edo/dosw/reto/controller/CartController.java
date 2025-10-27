package edo.dosw.reto.controller;

import edo.dosw.reto.dtos.CartDTO;
import edo.dosw.reto.mappers.CartMapper;
import edo.dosw.reto.models.Product;
import edo.dosw.reto.models.ShoppingCart;
import edo.dosw.reto.repositories.ProductRepository;
import edo.dosw.reto.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;
    private final ProductRepository productRepo;

    public CartController(CartService service, ProductRepository productRepo) {
        this.service = service;
        this.productRepo = productRepo;
    }


    @GetMapping("/{clientId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable String clientId) {
        ShoppingCart cart = service.getCart(clientId);
        return ResponseEntity.ok(CartMapper.toDTO(cart));
    }


    @PostMapping("/{clientId}/add/{productId}/{qty}")
    public ResponseEntity<CartDTO> addItem(
            @PathVariable String clientId,
            @PathVariable String productId,
            @PathVariable int qty) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ShoppingCart updated = service.addItem(clientId, product, qty);
        return ResponseEntity.ok(CartMapper.toDTO(updated));
    }


    @DeleteMapping("/{clientId}/remove/{productId}")
    public ResponseEntity<CartDTO> removeItem(
            @PathVariable String clientId,
            @PathVariable String productId) {

        ShoppingCart updated = service.removeItem(clientId, productId);
        return ResponseEntity.ok(CartMapper.toDTO(updated));
    }


    @DeleteMapping("/{clientId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable String clientId) {
        service.clearCart(clientId);
        return ResponseEntity.noContent().build();
    }
}
