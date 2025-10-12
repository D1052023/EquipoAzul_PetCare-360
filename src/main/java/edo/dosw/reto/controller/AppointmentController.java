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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    /** Agenda una nueva cita**/
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

        ServiceType type = null;
        if (dto.getServiceType() != null && !dto.getServiceType().isBlank()) {
            try {
                type = ServiceType.fromDisplayName(dto.getServiceType());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid service type: " + dto.getServiceType());
            }
        }

        VetService serviceObj = new VetService(dto.getServiceId(), type, dto.getServiceDescription());

        Appointment appointment = AppointmentMapper.toEntity(dto, pet, vet, serviceObj);
        Appointment saved = service.schedule(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentMapper.toDTO(saved));
    }

    /** Cpnsultar cita por identificacion**/
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment ID cannot be empty.");
        }

        Appointment appointment = service.findById(id);
        return ResponseEntity.ok(AppointmentMapper.toDTO(appointment));
    }

    /** Listado de todas las citas por mascota **/
    @GetMapping("/pets/{id}")
    public ResponseEntity<List<AppointmentDTO>> getByPet(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet ID cannot be empty.");
        }

        List<AppointmentDTO> list = service.findByPet(id)
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

}
