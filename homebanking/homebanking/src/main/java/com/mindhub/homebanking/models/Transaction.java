package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private double amount;

    private String description;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    public Transaction() {}

    public Transaction(TransactionType transactionType, double amount, String description, LocalDateTime date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public long getId() {return id;}

    public TransactionType getTransactionType() {return transactionType;}

    public void setTransactionType(TransactionType transactionType) {this.transactionType = transactionType;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public LocalDateTime getDate() {return date;}

    public void setDate(LocalDateTime date) {this.date = date;}

    public Account getAccount() {return account;}

    public void setAccount(Account account) {this.account = account;}

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + transactionType +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
