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
        validator.validate(appointment);
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
}
