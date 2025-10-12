package edo.dosw.reto;

import edo.dosw.reto.controller.AppointmentController;
import edo.dosw.reto.dtos.AppointmentDTO;
import edo.dosw.reto.models.Appointment;
import edo.dosw.reto.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    private AppointmentService service;
    private AppointmentController controller;

    @BeforeEach
    void setUp() {
        service = mock(AppointmentService.class);
        controller = new AppointmentController(service);
    }

    @Test
    void schedule_shouldReturnCreated_whenValid() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setPetSpecies("Cat");
        dto.setPetId("1");
        dto.setPetName("Michi");
        dto.setPetRace("Siamese");
        dto.setVeterinaryId("vet1");
        dto.setVeterinaryName("Dr. Smith");

        Appointment appointment = new Appointment();
        when(service.schedule(any(Appointment.class))).thenReturn(appointment);

        ResponseEntity<?> response = controller.schedule(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service, times(1)).schedule(any(Appointment.class));
    }

    @Test
    void schedule_shouldThrow_whenDTOIsNull() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.schedule(null));
        assertEquals("Appointment data cannot be null.", ex.getReason());
    }

    @Test
    void schedule_shouldThrow_whenPetSpeciesIsBlank() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setPetSpecies("   ");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.schedule(dto));
        assertEquals("Pet species cannot be empty.", ex.getReason());
    }

    @Test
    void schedule_shouldThrow_whenServiceTypeInvalid() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setPetSpecies("Dog");
        dto.setServiceType("INVALID");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.schedule(dto));
        assertTrue(ex.getReason().contains("Invalid service type"));
    }


    @Test
    void getById_existingAppointment_shouldReturnOk() {
        Appointment appointment = new Appointment();
        when(service.findById("1")).thenReturn(appointment);

        ResponseEntity<AppointmentDTO> response = controller.getById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).findById("1");
    }

    @Test
    void getById_nullOrBlankId_shouldThrowException() {
        ResponseStatusException ex1 = assertThrows(ResponseStatusException.class, () -> controller.getById(null));
        assertEquals("Appointment ID cannot be empty.", ex1.getReason());

        ResponseStatusException ex2 = assertThrows(ResponseStatusException.class, () -> controller.getById("   "));
        assertEquals("Appointment ID cannot be empty.", ex2.getReason());
    }

    @Test
    void getById_nonExistingAppointment_shouldThrowException() {
        when(service.findById("1")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found with id: 1"));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.getById("1"));
        assertEquals("Appointment not found with id: 1", ex.getReason());
    }
}
