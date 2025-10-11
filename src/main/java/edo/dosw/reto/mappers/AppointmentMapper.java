package edo.dosw.reto.mappers;

import edo.dosw.reto.dtos.AppointmentDTO;
import edo.dosw.reto.models.*;

import java.time.LocalDate;
import java.time.LocalTime;

public final class AppointmentMapper {

    private AppointmentMapper() {}

    public static AppointmentDTO toDTO(Appointment entity) {
        if (entity == null) return null;
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(entity.getId());
        if (entity.getPet() != null) {
            dto.setPetId(entity.getPet().getId());
            dto.setPetName(entity.getPet().getName());
            dto.setPetSpecies(entity.getPet().getSpecies() != null ? entity.getPet().getSpecies().name() : null);
            dto.setPetRace(entity.getPet().getRace());
        }
        if (entity.getVeterinary() != null) {
            dto.setVeterinaryId(entity.getVeterinary().getId());
            dto.setVeterinaryName(entity.getVeterinary().getName());
        }
        if (entity.getService() != null) {
            dto.setServiceId(entity.getService().getId());
            dto.setServiceName(entity.getService().getName());
            dto.setServiceDescription(entity.getService().getDescription());
        }
        if (entity.getDate() != null) dto.setDate(entity.getDate().toString());
        if (entity.getTime() != null) dto.setTime(entity.getTime().toString());
        dto.setReason(entity.getReason());
        return dto;
    }

    public static Appointment toEntity(AppointmentDTO dto, Pet pet, Veterinary vet, VetService service) {
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setPet(pet);
        appointment.setVeterinary(vet);
        appointment.setService(service);
        appointment.setReason(dto.getReason());
        appointment.setDate(LocalDate.parse(dto.getDate()));
        appointment.setTime(LocalTime.parse(dto.getTime()));
        return appointment;
    }
}
