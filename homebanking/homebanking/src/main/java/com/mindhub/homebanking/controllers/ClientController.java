package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllClients(){
        List <Client> clientList = clientRepository.findAll();
        List<ClientDto> clientDtoList= clientList.stream().map(client -> new ClientDto(client)).collect(Collectors.toList());
        if (!clientList.isEmpty()){
            return new ResponseEntity<>(clientDtoList,HttpStatus.OK);}
        else{
            return new ResponseEntity<>("There are no Clients",HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id){
        Client client = clientRepository.findById(id).orElse(null);
        if(client != null){
            ClientDto clientDto = new ClientDto(client);
            return new ResponseEntity<>(clientDto,HttpStatus.OK);}
        else{
            return new ResponseEntity<>("Id: " + id + " doesn't exist!", HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello Clients!";
    }
}
