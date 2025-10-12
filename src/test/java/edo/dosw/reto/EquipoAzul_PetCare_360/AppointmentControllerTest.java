package edo.dosw.reto.EquipoAzul_PetCare_360;


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
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            controller.schedule(null);
        });
        assertEquals("Appointment data cannot be null.", ex.getReason());
    }

    @Test
    void schedule_shouldThrow_whenPetSpeciesIsBlank() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setPetSpecies("   ");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            controller.schedule(dto);
        });
        assertEquals("Pet species cannot be empty.", ex.getReason());
    }

    @Test
    void schedule_shouldThrow_whenServiceTypeInvalid() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setPetSpecies("Dog");
        dto.setServiceType("INVALID");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            controller.schedule(dto);
        });
        assertTrue(ex.getReason().contains("Invalid service type"));
    }
}
