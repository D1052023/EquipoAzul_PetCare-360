package edo.dosw.reto.controller;

import edo.dosw.reto.dtos.AppointmentDTO;
import edo.dosw.reto.enums.Species;
import edo.dosw.reto.mappers.AppointmentMapper;
import edo.dosw.reto.models.*;
import edo.dosw.reto.services.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/citas")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    /** Agendar una nueva cita **/
    @PostMapping
    public ResponseEntity<AppointmentDTO> agendar(@RequestBody AppointmentDTO dto) {
        if (dto.getPetSpecies() == null || dto.getPetSpecies().isBlank()) {
            throw new IllegalArgumentException("La especie de la mascota no puede estar vacía");
        }

        Species species = Species.fromDisplayName(dto.getPetSpecies());
        Pet pet = new Pet(dto.getPetId(), dto.getPetName(), species, dto.getPetRace(), 0, null) {};
        Veterinary vet = new Veterinary(dto.getVeterinaryId(), dto.getVeterinaryName());
        VetService serviceObj = new VetService(dto.getServiceId(), dto.getServiceName(), dto.getServiceDescription());

        Appointment appointment = AppointmentMapper.toEntity(dto, pet, vet, serviceObj);
        Appointment saved = service.schedule(appointment);
        return ResponseEntity.ok(AppointmentMapper.toDTO(saved));
    }

    /** Consultar cita por id **/
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable String id) {
        Appointment appointment = service.findById(id);
        return ResponseEntity.ok(AppointmentMapper.toDTO(appointment));
    }

    /** Listar citas por veterinario **/
    @GetMapping("/veterinarios/{id}")
    public ResponseEntity<List<AppointmentDTO>> getByVeterinary(@PathVariable String id) {
        List<AppointmentDTO> list = service.findByVeterinary(id)
                .stream().map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    /** Listar citas por mascota **/
    @GetMapping("/mascotas/{id}")
    public ResponseEntity<List<AppointmentDTO>> getByPet(@PathVariable String id) {
        List<AppointmentDTO> list = service.findByPet(id)
                .stream().map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    /** Cancelar cita **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable String id) {
        service.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
