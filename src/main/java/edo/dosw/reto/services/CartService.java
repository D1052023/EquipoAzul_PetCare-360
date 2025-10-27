package edo.dosw.reto.services;

import edo.dosw.reto.models.ShoppingCart;
import edo.dosw.reto.models.Product;

public interface CartService {
    ShoppingCart getCart(String clientId);
    ShoppingCart addItem(String clientId, Product product, int quantity);
    ShoppingCart removeItem(String clientId, String productId);
    void clearCart(String clientId);
}
