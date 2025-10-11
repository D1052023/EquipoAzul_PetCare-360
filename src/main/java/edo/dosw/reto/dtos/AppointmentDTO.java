package edo.dosw.reto.dtos;


import lombok.Data;

@Data
public class AppointmentDTO {
    private String id;
    private String petId;
    private String petName;
    private String petSpecies;
    private String petRace;
    private String veterinaryId;
    private String veterinaryName;
    private String serviceId;
    private String serviceName;
    private String serviceDescription;
    private String date;
    private String time;
    private String reason;
}
