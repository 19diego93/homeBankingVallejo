package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import java.util.List;

public interface ClientService {

   List<Client> getListClients();

   List<ClientDto> getListClientsDto(List <Client> clients);

   Client getClientById(Long id);

   ClientDto getClientDto(Client client);

   Client getClientByEmail(String email);

   boolean existsClientByEmail(String email);

   void saveClient(Client client);


}
