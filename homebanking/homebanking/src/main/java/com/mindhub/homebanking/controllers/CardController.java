package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.dtos.NewCardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
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
    private ClientService clientService;

    @Autowired
    private CardService cardService;



 @PostMapping("/clients/current/cards")
    public ResponseEntity<?> getClient(@RequestBody NewCardDTO newCardDTO, Authentication authentication){

        Client client = clientService.getClientByEmail(authentication.getName());
     if (client == null) {
         return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
     }
     // Verifica si el cliente ya tiene 6 o más tarjetas
     if (client.getCards().size() >= 6) {
         return new ResponseEntity<>("Client cannot have more than 6 cards", HttpStatus.FORBIDDEN);
     }

     if (newCardDTO.cardColor().isBlank()) {
         return new ResponseEntity<>("Card color cannot be empty", HttpStatus.BAD_REQUEST);
     }

     if(newCardDTO.cardType().isBlank()){
         return new ResponseEntity<>("Card type cannot be empty", HttpStatus.BAD_REQUEST);
     }

     //verifica que coincida lo que llega del dto con los distintos enums
     CardType toCardType;
     try {
         toCardType = CardType.valueOf(newCardDTO.cardType());
     } catch (IllegalArgumentException | NullPointerException e) {
         return new ResponseEntity<>("Check the card type", HttpStatus.FORBIDDEN);
     }

     CardColor toCardColor;
     try {
         toCardColor = CardColor.valueOf(newCardDTO.cardColor());
     } catch (IllegalArgumentException | NullPointerException e) {
         return new ResponseEntity<>("Check the card color", HttpStatus.FORBIDDEN);
     }

     // Verifica si el cliente ya tiene una tarjeta del mismo tipo y color
     boolean cardExists = client.getCards().stream()
             .anyMatch(card -> card.getCardType() == CardType.valueOf(newCardDTO.cardType()) && card.getColor() == CardColor.valueOf(newCardDTO.cardColor()));

     if (cardExists) {
         return new ResponseEntity<>("Client already has a card of this type and color", HttpStatus.FORBIDDEN);
     }


     // Generar un número de tarjeta único
     String cardNumber;
     do {
         cardNumber = RandomNumber.fourDigits() + "-" + RandomNumber.fourDigits() + "-" + RandomNumber.fourDigits() + "-" + RandomNumber.fourDigits();
     } while (cardService.getCardByNumber(cardNumber) != null);

     // Generar el CVV de la tarjeta
     Integer cvv = Integer.valueOf(RandomNumber.threeDigits());

     // Crear la nueva tarjeta y asociarla al cliente
     Card card = new Card(toCardType, toCardColor, cardNumber, cvv);
     client.addCard(card);
     card.setClient(client);

     // Guardar  la tarjeta en el repositorio
         cardService.saveCard(card);

     // Devolver la respuesta con el DTO del cliente actualizado
        return new ResponseEntity<>(new ClientDto(client), HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/cards")
    public ResponseEntity<?> getClientCards(Authentication authentication){
        Client client = clientService.getClientByEmail(authentication.getName());
        if (client == null) {
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        List<CardDto> cardDtoList = client.getCards().stream()
                .map(CardDto::new)
                .collect(Collectors.toList());
        if (!cardDtoList.isEmpty()){
            return new ResponseEntity<>(cardDtoList, HttpStatus.OK);}
        else{
            return new ResponseEntity<>("There are no Cards",HttpStatus.NOT_FOUND);}
    }
}
