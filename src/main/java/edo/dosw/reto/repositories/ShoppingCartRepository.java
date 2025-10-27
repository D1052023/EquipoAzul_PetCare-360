package edo.dosw.reto.repositories;

import edo.dosw.reto.models.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String> {
    ShoppingCart findByClientId(String clientId);
}
