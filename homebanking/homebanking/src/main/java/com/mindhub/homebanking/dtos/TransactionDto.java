package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.Type;
import java.time.LocalDateTime;

public class TransactionDto {

    private long id;

    private Type type;

    private double amount;

    private String description;

    private LocalDateTime date;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
    }

    public long getId() {return id;}

    public Type getType() {return type;}

    public double getAmount() {return amount;}

    public String getDescription() {return description;}

    public LocalDateTime getDate() {return date;}
}
