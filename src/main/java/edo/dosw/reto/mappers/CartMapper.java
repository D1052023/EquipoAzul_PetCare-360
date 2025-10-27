package edo.dosw.reto.mappers;

import edo.dosw.reto.dtos.CartDTO;
import edo.dosw.reto.dtos.CartItemDTO;
import edo.dosw.reto.models.ShoppingCart;


public class CartMapper {

    private CartMapper() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static CartDTO toDTO(ShoppingCart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setClientId(cart.getClient() != null ? cart.getClient().getId() : null);
        dto.setClientName(cart.getClient() != null ? cart.getClient().getName() : null);
        dto.setTotal(cart.calculateTotal());

        dto.setItems(
                cart.getItems().stream()
                        .map(item -> {
                            CartItemDTO i = new CartItemDTO();
                            i.setProductId(item.getProduct().getId());
                            i.setProductName(item.getProduct().getName());
                            i.setPrice(item.getProduct().getPrice());
                            i.setQuantity(item.getQuantity());
                            i.setSubtotal(item.getSubtotal());
                            return i;
                        })
                        .toList()
        );

        return dto;
    }
}
