package edo.dosw.reto.services;


import edo.dosw.reto.models.Appointment;
import edo.dosw.reto.repositories.AppointmentRepository;

import edo.dosw.reto.validators.AppointmentValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentValidator validator;

    public AppointmentServiceImpl(AppointmentRepository repository, AppointmentValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public Appointment schedule(Appointment appointment) {
        validator.validate(appointment);
        return repository.save(appointment);
    }

    @Override
    public Appointment findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    @Override
    public List<Appointment> findByVeterinary(String veterinaryId) {
        return repository.findByVeterinary(veterinaryId);
    }

    @Override
    public List<Appointment> findByPet(String petId) {
        return repository.findByPet(petId);
    }
    @Override
    public void cancel(String id) {
        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la cita con id: " + id));
        repository.deleteById(existing.getId());
    }

}
