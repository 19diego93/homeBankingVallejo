package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.Type;

import java.time.LocalDate;

public class CardDto {

    private long id;

    private String cardHolder;

    private Type cardType;

    private CardColor color;

    private String number;

    private int cvv;

    private LocalDate creationDate;

    private LocalDate expirationDate;

    public CardDto(Card card) {
        this.id= card.getId();
        this.cardType = card.getCardType();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.color = card.getColor();
        this.creationDate = card.getCreationDate();
        this.expirationDate = card.getExpirationDate();

    }

    public LocalDate getExpirationDate() {return expirationDate;}

    public LocalDate getCreationDate() {return creationDate;}

    public int getCvv() {return cvv;}

    public String getNumber() {return number;}

    public CardColor getColor() {return color;}

    public Type getCardType() {return cardType;}

    public String getCardHolder() {return cardHolder;}

    public long getId() {return id;}
}
