package edo.dosw.reto.repositories;


import edo.dosw.reto.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(String id);
    List<Appointment> findByVeterinary(String veterinaryId);
    List<Appointment> findByPet(String petId);
    List<Appointment> findAll();
    void deleteById(String id);

}
