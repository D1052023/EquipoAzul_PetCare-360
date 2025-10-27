package edo.dosw.reto.enums;

public enum ProductCategory {
    FOOD("Comida"),
    MEDICINE("Medicamento"),
    ACCESSORY("Accesorio"),
    HYGIENE("Higiene"),
    TOY("Juguete");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ProductCategory fromDisplayName(String name) {
        for (ProductCategory c : values()) {
            if (c.displayName.equalsIgnoreCase(name) || c.name().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Categoría inválida: " + name);
    }
}
