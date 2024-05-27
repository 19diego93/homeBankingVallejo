package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cardHolder;

    private Type cardType;

    private CardColor color;

    private String number;

    private int cvv;

    private LocalDate creationDate;

    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public Card() {}

    public Card(Type cardType, CardColor color, String number, int cvv) {
        this.cardType = cardType;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.creationDate = LocalDate.now();
        this.expirationDate = LocalDate.now().plusYears(5);
    }

    public long getId() {return id;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public Type getCardType() {return cardType;}

    public CardColor getColor() {return color;}

    public String getNumber() {return number;}

    public int getCvv() {return cvv;}

    public LocalDate getCreationDate() {return creationDate;}

    public LocalDate getExpirationDate() {return expirationDate;}

    public String getCardHolder() {return cardHolder;}

    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}
}
