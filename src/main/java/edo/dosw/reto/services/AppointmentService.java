package edo.dosw.reto.services;


import edo.dosw.reto.models.Appointment;
import java.util.List;

public interface AppointmentService {
    Appointment schedule(Appointment appointment);
    Appointment findById(String id);
}
