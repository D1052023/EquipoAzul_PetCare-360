package edo.dosw.reto.EquipoAzul_PetCare_360;


import edo.dosw.reto.models.Appointment;
import edo.dosw.reto.repositories.AppointmentRepository;
import edo.dosw.reto.services.AppointmentServiceImpl;
import edo.dosw.reto.validators.AppointmentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceImplTest {

    private AppointmentRepository repository;
    private AppointmentValidator validator;
    private AppointmentServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(AppointmentRepository.class);
        validator = mock(AppointmentValidator.class);
        service = new AppointmentServiceImpl(repository, validator);
    }

    @Test
    void schedule_validAppointment_shouldSaveAndReturnAppointment() {
        Appointment appointment = new Appointment();
        when(repository.save(appointment)).thenReturn(appointment);

        Appointment result = service.schedule(appointment);

        verify(validator).validate(appointment);
        verify(repository).save(appointment);
        assertEquals(appointment, result);
    }

    @Test
    void schedule_nullAppointment_shouldThrowException() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            service.schedule(null);
        });
        assertEquals("Appointment data cannot be null.", exception.getReason());
        assertEquals(400, exception.getStatusCode().value());
    }

    @Test
    void schedule_invalidAppointment_shouldThrowValidationException() {
        Appointment appointment = new Appointment();
        doThrow(new IllegalArgumentException("Invalid appointment"))
                .when(validator).validate(appointment);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.schedule(appointment);
        });

        assertEquals("Invalid appointment", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
