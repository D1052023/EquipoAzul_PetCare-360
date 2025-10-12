package edo.dosw.reto.enums;

public enum ServiceType {
    GENERAL_CONSULTATION("General Consultation"),
    VACCINATION("Vaccination"),
    SURGERY("Surgery"),
    DEWORMING("Deworming"),
    EMERGENCY("Emergency");

    private final String displayName;

    ServiceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ServiceType fromDisplayName(String name) {
        for (ServiceType type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid service type: " + name);
    }
}
