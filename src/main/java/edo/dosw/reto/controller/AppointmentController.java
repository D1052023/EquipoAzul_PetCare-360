package edo.dosw.reto.controller;

import edo.dosw.reto.dtos.AppointmentDTO;
import edo.dosw.reto.enums.ServiceType;
import edo.dosw.reto.enums.Species;
import edo.dosw.reto.mappers.AppointmentMapper;
import edo.dosw.reto.models.*;
import edo.dosw.reto.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> schedule(@RequestBody AppointmentDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment data cannot be null.");
        }

        if (dto.getPetSpecies() == null || dto.getPetSpecies().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet species cannot be empty.");
        }

        Species species = Species.fromDisplayName(dto.getPetSpecies());
        Pet pet = new Pet(dto.getPetId(), dto.getPetName(), species, dto.getPetRace(), 0, null) {};
        Veterinary vet = new Veterinary(dto.getVeterinaryId(), dto.getVeterinaryName());

        try {
            if (dto.getServiceType() != null && !dto.getServiceType().isBlank()) {
                ServiceType.fromDisplayName(dto.getServiceType());
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid service type: " + dto.getServiceType());
        }

        Appointment appointment = AppointmentMapper.toEntity(dto, pet, vet);
        Appointment saved = service.schedule(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment ID cannot be empty.");
        }

        Appointment appointment = service.findById(id);
        return ResponseEntity.ok(AppointmentMapper.toDTO(appointment));
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<List<AppointmentDTO>> getByPet(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet ID cannot be empty.");
        }

        List<AppointmentDTO> list = service.findByPet(id)
                .stream()
                .map(AppointmentMapper::toDTO)
                .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/veterinaries/{id}")
    public ResponseEntity<List<AppointmentDTO>> getByVeterinary(
            @PathVariable String id,
            @RequestParam(required = false) String date) {

        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veterinary ID cannot be empty.");
        }

        List<Appointment> appointments = service.findByVeterinary(id);

        if (date != null && !date.isBlank()) {
            try {
                LocalDate parsedDate = LocalDate.parse(date);
                appointments = appointments.stream()
                        .filter(a -> a.getDate().equals(parsedDate))
                        .toList();
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Use YYYY-MM-DD.");
            }
        }

        List<AppointmentDTO> list = appointments.stream()
                .map(AppointmentMapper::toDTO)
                .toList();

        return ResponseEntity.ok(list);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment ID cannot be empty.");
        }

        service.cancel(id);
        return ResponseEntity.noContent().build();
    }

}
