package edo.dosw.reto.controller;

import edo.dosw.reto.models.Invoice;
import edo.dosw.reto.services.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }


    @PostMapping("/{clientId}")
    public ResponseEntity<Invoice> checkout(@PathVariable String clientId) {
        Invoice invoice = checkoutService.checkout(clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
    }


    @GetMapping("/{invoiceId}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String invoiceId) {
        Invoice invoice = checkoutService.findById(invoiceId);
        return ResponseEntity.ok(invoice);
    }


    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> list = checkoutService.findAll();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Invoice>> getInvoicesByClient(@PathVariable String clientId) {
        List<Invoice> list = checkoutService.findByClientId(clientId);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable String invoiceId) {
        checkoutService.deleteById(invoiceId);
        return ResponseEntity.noContent().build();
    }
}
