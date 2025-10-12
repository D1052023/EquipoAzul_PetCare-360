package edo.dosw.reto.repositories;


import edo.dosw.reto.models.Appointment;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(String id);
    List<Appointment> findByVeterinary(String veterinaryId);
    List<Appointment> findByPet(String petId);
    List<Appointment> findAll();
    void deleteById(String id);

}
