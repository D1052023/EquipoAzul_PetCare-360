package edo.dosw.reto;


import edo.dosw.reto.models.Appointment;
import edo.dosw.reto.repositories.AppointmentRepository;
import edo.dosw.reto.services.AppointmentServiceImpl;
import edo.dosw.reto.validators.AppointmentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.schedule(null));
        assertEquals("Appointment data cannot be null.", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void schedule_invalidAppointment_shouldThrowValidationException() {
        Appointment appointment = new Appointment();
        doThrow(new IllegalArgumentException("Invalid appointment")).when(validator).validate(appointment);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.schedule(appointment));
        assertEquals("Invalid appointment", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void findById_existingAppointment_shouldReturnAppointment() {
        Appointment appointment = new Appointment();
        when(repository.findById("1")).thenReturn(Optional.of(appointment));

        Appointment result = service.findById("1");

        assertEquals(appointment, result);
        verify(repository).findById("1");
    }

    @Test
    void findById_nonExistingAppointment_shouldThrowException() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.findById("1"));
        assertEquals("Appointment not found with id: 1", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void findByPet_validPetId_shouldReturnAppointments() {
        String petId = "pet1";
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(repository.findByPet(petId)).thenReturn(appointments);

        List<Appointment> result = service.findByPet(petId);

        assertEquals(2, result.size());
        verify(repository).findByPet(petId);
    }

    @Test
    void findByPet_nullOrBlankPetId_shouldThrowException() {
        ResponseStatusException ex1 = assertThrows(ResponseStatusException.class, () -> service.findByPet(null));
        assertEquals("Pet ID cannot be null or empty.", ex1.getReason());

        ResponseStatusException ex2 = assertThrows(ResponseStatusException.class, () -> service.findByPet("   "));
        assertEquals("Pet ID cannot be null or empty.", ex2.getReason());
    }
}