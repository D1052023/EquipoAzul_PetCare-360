package edo.dosw.reto.services;

import edo.dosw.reto.models.Appointment;
import edo.dosw.reto.repositories.AppointmentRepository;
import edo.dosw.reto.validators.AppointmentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (appointment == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Appointment data cannot be null."
            );
        }

        // Apply business rules
        validator.validate(appointment);

        // Save appointment
        return repository.save(appointment);
    }

    @Override
    public Appointment findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Appointment not found with id: " + id
                ));
    }

    @Override
    public List<Appointment> findByVeterinary(String veterinaryId) {
        if (veterinaryId == null || veterinaryId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Veterinary ID cannot be null or empty."
            );
        }
        return repository.findByVeterinary(veterinaryId);
    }

    @Override
    public List<Appointment> findByPet(String petId) {
        if (petId == null || petId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Pet ID cannot be null or empty."
            );
        }
        return repository.findByPet(petId);
    }

    @Override
    public void cancel(String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Appointment ID cannot be null or empty."
            );
        }

        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No appointment found with id: " + id
                ));

        repository.deleteById(existing.getId());
    }
}
