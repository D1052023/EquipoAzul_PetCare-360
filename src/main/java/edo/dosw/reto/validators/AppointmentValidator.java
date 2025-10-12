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

        // 🔹 Regla 4: validar que tenga veterinario, mascota y servicio asignados
        if (appointment.getVeterinary() == null) {
            throw new IllegalArgumentException("Debe especificar un veterinario para la cita");
        }
        if (appointment.getPet() == null) {
            throw new IllegalArgumentException("Debe especificar una mascota para la cita");
        }
        if (appointment.getService() == null) {
            throw new IllegalArgumentException("Debe especificar un servicio para la cita");
        }

        // 🔹 Regla 1: evitar conflictos de veterinario (ya existente)
        boolean vetConflict = repository.findByVeterinary(appointment.getVeterinary().getId())
                .stream()
                .anyMatch(a -> a.getDate().equals(appointment.getDate()) &&
                        a.getTime().equals(appointment.getTime()));

        if (vetConflict) {
            throw new IllegalStateException("El veterinario ya tiene una cita en esa fecha y hora");
        }

        // 🔹 Regla 2: evitar conflictos de mascota (nueva)
        boolean petConflict = repository.findByPet(appointment.getPet().getId())
                .stream()
                .anyMatch(a -> a.getDate().equals(appointment.getDate()) &&
                        a.getTime().equals(appointment.getTime()));

        if (petConflict) {
            throw new IllegalStateException("La mascota ya tiene una cita en esa fecha y hora");
        }
    }
}
