package edo.dosw.reto.services;

import edo.dosw.reto.dtos.ClientDTO;
import edo.dosw.reto.expections.ClientNotFoundException;
import edo.dosw.reto.mappers.ClientMapper;
import edo.dosw.reto.models.Client;
import edo.dosw.reto.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = ClientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return ClientMapper.toDTO(client);
    }

    @Override
    public ClientDTO getClientById(String id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente con ID " + id + " no encontrado"));
        return ClientMapper.toDTO(client);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper::toDTO)
                .toList();
    }

    @Override
    public ClientDTO updateClient(String id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente con ID " + id + " no encontrado"));

        existingClient.setName(clientDTO.getName());
        existingClient.setPhone(clientDTO.getPhone());
        existingClient.setEmail(clientDTO.getEmail());

        existingClient = clientRepository.save(existingClient);
        return ClientMapper.toDTO(existingClient);
    }

    @Override
    public void deleteClient(String id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Cliente con ID " + id + " no encontrado");
        }
        clientRepository.deleteById(id);
    }
}
