package edo.dosw.reto.dtos;

import lombok.Data;

@Data
public class PetDTO {
    private String id;
    private String name;
    private String species;
    private String race;
    private int age;
    private String ownerId;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;
}
