package edo.dosw.reto.models;


import java.time.LocalDateTime;
import java.util.List;

public class InvoiceFactory {
    private InvoiceFactory() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static Invoice createMedicalInvoice(Client client, Appointment appointment, double total) {
        MedicalInvoice invoice = new MedicalInvoice();
        invoice.setClient(client);
        invoice.setAppointment(appointment);
        invoice.setTotal(total);
        invoice.setCreatedAt(LocalDateTime.now());
        return invoice;
    }

    public static Invoice createProductInvoice(Client client, List<CartItem> items) {
        double total = items.stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        ProductInvoice invoice = new ProductInvoice();
        invoice.setClient(client);
        invoice.setItems(items);
        invoice.setTotal(total);
        invoice.setCreatedAt(LocalDateTime.now());
        return invoice;
    }
}
