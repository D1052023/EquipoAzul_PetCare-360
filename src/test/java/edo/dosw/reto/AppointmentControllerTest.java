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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void getByPet_validPetId_shouldReturnList() {
        String petId = "pet1";
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(service.findByPet(petId)).thenReturn(appointments);

        ResponseEntity<List<AppointmentDTO>> response = controller.getByPet(petId);

        assertEquals(2, response.getBody().size());
        verify(service).findByPet(petId);
    }

    @Test
    void getByPet_nullOrBlankPetId_shouldThrowException() {
        ResponseStatusException ex1 = assertThrows(ResponseStatusException.class, () -> controller.getByPet(null));
        assertEquals("Pet ID cannot be empty.", ex1.getReason());

        ResponseStatusException ex2 = assertThrows(ResponseStatusException.class, () -> controller.getByPet("   "));
        assertEquals("Pet ID cannot be empty.", ex2.getReason());
    }
    @Test
    void getByVeterinary_validId_shouldReturnList() {
        String vetId = "vet1";
        Appointment app1 = new Appointment();
        Appointment app2 = new Appointment();
        app1.setDate(LocalDate.of(2025,10,12));
        app2.setDate(LocalDate.of(2025,10,13));

        List<Appointment> appointments = Arrays.asList(app1, app2);
        when(service.findByVeterinary(vetId)).thenReturn(appointments);

        ResponseEntity<List<AppointmentDTO>> response = controller.getByVeterinary(vetId, null);

        assertEquals(2, response.getBody().size());
        verify(service).findByVeterinary(vetId);
    }

    @Test
    void getByVeterinary_validIdWithDateFilter_shouldReturnFilteredList() {
        String vetId = "vet1";
        Appointment app1 = new Appointment();
        Appointment app2 = new Appointment();
        app1.setDate(LocalDate.of(2025,10,12));
        app2.setDate(LocalDate.of(2025,10,13));

        when(service.findByVeterinary(vetId)).thenReturn(Arrays.asList(app1, app2));

        ResponseEntity<List<AppointmentDTO>> response = controller.getByVeterinary(vetId, "2025-10-12");

        assertEquals(1, response.getBody().size());
        assertEquals(LocalDate.of(2025,10,12),
                LocalDate.parse(response.getBody().get(0).getDate()));
    }


    @Test
    void getByVeterinary_invalidDateFormat_shouldThrowException() {
        String vetId = "vet1";
        when(service.findByVeterinary(vetId)).thenReturn(Arrays.asList(new Appointment()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                controller.getByVeterinary(vetId, "12-10-2025"));

        assertEquals("Invalid date format. Use YYYY-MM-DD.", ex.getReason());
    }

    @Test
    void getByVeterinary_nullOrBlankId_shouldThrowException() {
        ResponseStatusException ex1 = assertThrows(ResponseStatusException.class, () -> controller.getByVeterinary(null, null));
        assertEquals("Veterinary ID cannot be empty.", ex1.getReason());

        ResponseStatusException ex2 = assertThrows(ResponseStatusException.class, () -> controller.getByVeterinary("   ", null));
        assertEquals("Veterinary ID cannot be empty.", ex2.getReason());
    }
    @Test
    void cancel_validId_shouldReturnNoContent() {
        String id = "app1";

        ResponseEntity<Void> response = controller.cancel(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(service).cancel(id);
    }

    @Test
    void cancel_nullOrBlankId_shouldThrowException() {
        ResponseStatusException ex1 = assertThrows(ResponseStatusException.class, () -> controller.cancel(null));
        assertEquals("Appointment ID cannot be empty.", ex1.getReason());

        ResponseStatusException ex2 = assertThrows(ResponseStatusException.class, () -> controller.cancel("   "));
        assertEquals("Appointment ID cannot be empty.", ex2.getReason());
    }
}