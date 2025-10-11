package edo.dosw.reto.validators;

import edo.dosw.reto.models.Appointment;
import edo.dosw.reto.repositories.AppointmentRepository;
import org.springframework.stereotype.Component;

@Component
public class AppointmentValidator {

    private final AppointmentRepository repository;

    public AppointmentValidator(AppointmentRepository repository) {
        this.repository = repository;
    }

    public void validate(Appointment appointment) {
        if (appointment.getVeterinary() == null)
            throw new IllegalArgumentException("Debe especificar un veterinario");
        if (appointment.getDate() == null || appointment.getTime() == null)
            throw new IllegalArgumentException("Debe especificar fecha y hora de la cita");

        boolean conflict = repository.findByVeterinary(appointment.getVeterinary().getId())
                .stream()
                .anyMatch(a -> a.getDate().equals(appointment.getDate()) &&
                        a.getTime().equals(appointment.getTime()));
        if (conflict)
            throw new IllegalStateException("El veterinario ya tiene una cita en esa fecha/hora");
    }
}
