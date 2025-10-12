package edo.dosw.reto.repositories;


import edo.dosw.reto.models.Appointment;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final Map<String, Appointment> data = new ConcurrentHashMap<>();

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null)
            appointment.setId(UUID.randomUUID().toString());
        data.put(appointment.getId(), appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }


    @Override
    public List<Appointment> findByPet(String petId) {
        return data.values().stream()
                .filter(a -> a.getPet() != null &&
                        petId.equals(a.getPet().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(data.values());
    }


}
