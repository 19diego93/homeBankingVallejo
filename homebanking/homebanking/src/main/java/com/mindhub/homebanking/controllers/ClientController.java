package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<?> getAllClients(){

        List<ClientDto> clientDtoList= clientService.getListClientsDto(clientService.getListClients());
        if (!clientDtoList.isEmpty()){
            return new ResponseEntity<>(clientDtoList,HttpStatus.OK);}
        else{
            return new ResponseEntity<>("There are no Clients",HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id){
        Client client = clientService.getClientById(id);

        if(client != null){
            ClientDto clientDto = clientService.getClientDto(client);
            return new ResponseEntity<>(clientDto,HttpStatus.OK);}
        else{
            return new ResponseEntity<>("Id: " + id + " doesn't exist!", HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello Client!";
    }
}
