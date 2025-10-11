package edo.dosw.reto.enums;

public enum Species {
    DOG("Dog"),
    CAT("Cat"),
    BIRD("Bird"),
    RABBIT("Rabbit"),
    FISH("Fish");

    private final String displayName;

    Species(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static Species fromDisplayName(String name) {
        if (name == null) return null;
        for (Species s : Species.values()) {
            if (s.getDisplayName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

}
