package edo.dosw.reto.mappers;

import edo.dosw.reto.dtos.ClientDTO;
import edo.dosw.reto.models.Client;

public class ClientMapper {
    private ClientMapper() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static ClientDTO toDTO(Client client) {
        if (client == null) return null;
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setPhone(client.getPhone());
        dto.setEmail(client.getEmail());
        return dto;
    }

    public static Client toEntity(ClientDTO dto) {
        if (dto == null) return null;
        return new Client(
                dto.getId(),
                dto.getName(),
                dto.getPhone(),
                dto.getEmail()
        );
    }
}
