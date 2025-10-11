package edo.dosw.reto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Appointment {
    private String id;
    private Pet pet;
    private Veterinary veterinary;
    private VetService service;
    private LocalDate date;
    private LocalTime time;
    private String reason;
}
