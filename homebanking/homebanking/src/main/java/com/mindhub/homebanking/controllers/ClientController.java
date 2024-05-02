package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id){
    Client client = clientRepository.findById(id).orElse(null);
    if(client != null){return new ResponseEntity<>(client,HttpStatus.OK);}
    return new ResponseEntity<>("Id: "+id+" doesn't exist!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello Clients!";
    }
}
