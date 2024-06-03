package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    private Object Collectors;

    @Override
    public List<Client> getListClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<ClientDto> getListClientsDto(List <Client> clients) {
        return  clients.stream().map(client -> new ClientDto(client)).toList();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public ClientDto getClientDto(Client client) {
        return new ClientDto(client);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public boolean existsClientByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);

    }
}
