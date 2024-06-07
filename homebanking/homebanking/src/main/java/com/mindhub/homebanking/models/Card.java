package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cardHolder;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private CardColor cardColor;

    private String number;

    private Integer cvv;

    private LocalDate creationDate;

    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public Card() {}

    public Card(CardType cardType, CardColor color, String number, Integer cvv) {
        this.cardType = cardType;
        this.cardColor = color;
        this.number = number;
        this.cvv = cvv;
        this.creationDate = LocalDate.now();
        this.expirationDate = LocalDate.now().plusYears(5);
    }

    public long getId() {return id;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public CardType getCardType() {return cardType;}

    public CardColor getColor() {return cardColor;}

    public String getNumber() {return number;}

    public Integer getCvv() {return cvv;}

    public LocalDate getCreationDate() {return creationDate;}

    public LocalDate getExpirationDate() {return expirationDate;}

    public String getCardHolder() {return cardHolder;}

    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}
}
