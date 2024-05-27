package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.dtos.NewCardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;



 @PostMapping("/clients/current/cards")
    public ResponseEntity<?> getClient(@RequestBody NewCardDTO newCardDTO, Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());

     // Verificar si el cliente ya tiene 6 o más tarjetas
     if (client.getCards().size() >= 6) {
         return new ResponseEntity<>("Client cannot have more than 6 cards", HttpStatus.FORBIDDEN);
     }
     if (newCardDTO.cardType() == null || newCardDTO.cardColor() == null) {
         return new ResponseEntity<>("Card type or color cannot be null", HttpStatus.BAD_REQUEST);
     }
     // Verificar si el cliente ya tiene una tarjeta del mismo tipo y color
     boolean cardExists = client.getCards().stream()
             .anyMatch(card -> card.getCardType() == newCardDTO.cardType() && card.getColor() == newCardDTO.cardColor());

     if (cardExists) {
         return new ResponseEntity<>("Client already has a card of this type and color", HttpStatus.FORBIDDEN);
     }
     // Generar un número de tarjeta único
     String cardNumber;
     do {
         cardNumber = RandomNumber.fourDigits() + "-" + RandomNumber.fourDigits() + "-" + RandomNumber.fourDigits() + "-" + RandomNumber.fourDigits();
     } while (cardRepository.findByNumber(cardNumber) != null);

     // Generar el CVV de la tarjeta
     Integer cvv = Integer.valueOf(RandomNumber.threeDigits());

     // Crear la nueva tarjeta y asociarla al cliente
     Card card = new Card(newCardDTO.cardType(), newCardDTO.cardColor(), cardNumber, cvv);
     client.addCard(card);
     card.setClient(client);

     // Guardar el cliente y la tarjeta en el repositorio
     clientRepository.save(client);
     cardRepository.save(card);

     // Devolver la respuesta con el DTO del cliente actualizado
        return new ResponseEntity<>(new ClientDto(client), HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/cards")
    public ResponseEntity<?> getClientCards(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        List<CardDto> cardDtoList = client.getCards().stream()
                .map(CardDto::new)
                .collect(Collectors.toList());
        if (!cardDtoList.isEmpty()){
            return new ResponseEntity<>(cardDtoList, HttpStatus.OK);}
        else{
            return new ResponseEntity<>("There are no Accounts",HttpStatus.NOT_FOUND);}
    }
}
