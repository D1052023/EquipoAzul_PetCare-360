package edo.dosw.reto.services;


import edo.dosw.reto.models.*;
import edo.dosw.reto.repositories.InvoiceRepository;
import edo.dosw.reto.repositories.ShoppingCartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CheckoutService {

    private final ShoppingCartRepository cartRepo;
    private final InvoiceRepository invoiceRepo;

    public CheckoutService(ShoppingCartRepository cartRepo, InvoiceRepository invoiceRepo) {
        this.cartRepo = cartRepo;
        this.invoiceRepo = invoiceRepo;
    }

    public Invoice checkout(String clientId) {
        ShoppingCart cart = cartRepo.findByClientId(clientId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El carrito está vacío o no existe.");
        }

        Invoice invoice = InvoiceFactory.createProductInvoice(cart.getClient(), cart.getItems());
        invoiceRepo.save(invoice);

        cart.getItems().clear();
        cartRepo.save(cart);

        return invoice;
    }

    public Invoice findById(String invoiceId) {
        return invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Invoice not found with id: " + invoiceId));
    }

    public List<Invoice> findAll() {
        return invoiceRepo.findAll();
    }

    public List<Invoice> findByClientId(String clientId) {
        return invoiceRepo.findAll()
                .stream()
                .filter(inv -> inv.getClient() != null && clientId.equals(inv.getClient().getId()))
                .toList();
    }

    public void deleteById(String invoiceId) {
        if (!invoiceRepo.existsById(invoiceId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found with id: " + invoiceId);
        }
        invoiceRepo.deleteById(invoiceId);
    }
}
