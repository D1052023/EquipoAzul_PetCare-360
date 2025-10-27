package edo.dosw.reto.services;

import edo.dosw.reto.dtos.ClientDTO;
import java.util.List;

public interface ClientService {
    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO getClientById(String id);
    List<ClientDTO> getAllClients();
    ClientDTO updateClient(String id, ClientDTO clientDTO);
    void deleteClient(String id);
}
