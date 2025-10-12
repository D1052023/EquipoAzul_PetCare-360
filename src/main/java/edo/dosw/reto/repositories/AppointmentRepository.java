package edo.dosw.reto.repositories;


import edo.dosw.reto.models.Appointment;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    List<Appointment> findAll();

}
