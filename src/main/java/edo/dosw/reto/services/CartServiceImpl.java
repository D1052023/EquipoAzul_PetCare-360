package edo.dosw.reto.services;

import edo.dosw.reto.models.Client;
import edo.dosw.reto.models.Product;
import edo.dosw.reto.models.ShoppingCart;
import edo.dosw.reto.repositories.ClientRepository;
import edo.dosw.reto.repositories.ShoppingCartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CartServiceImpl implements CartService {

    private final ShoppingCartRepository cartRepo;
    private final ClientRepository clientRepo;

    public CartServiceImpl(ShoppingCartRepository cartRepo,
                           ClientRepository clientRepo) {
        this.cartRepo = cartRepo;
        this.clientRepo = clientRepo;
    }

    @Override
    public ShoppingCart getCart(String clientId) {
        return cartRepo.findByClientId(clientId);
    }

    @Override
    public ShoppingCart addItem(String clientId, Product product, int quantity) {
        ShoppingCart cart = cartRepo.findByClientId(clientId);

        if (cart == null) {
            cart = new ShoppingCart();

            Client client = clientRepo.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            cart.setClient(client);
        }

        cart.addItem(product, quantity);
        return cartRepo.save(cart);
    }

    @Override
    public ShoppingCart removeItem(String clientId, String productId) {
        var cart = cartRepo.findByClientId(clientId);
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found for clientId: " + clientId);

        cart.removeItem(productId);
        return cartRepo.save(cart);
    }

    @Override
    public void clearCart(String clientId) {
        var cart = cartRepo.findByClientId(clientId);
        if (cart != null) {
            cart.getItems().clear();
            cartRepo.save(cart);
        }
    }
}
