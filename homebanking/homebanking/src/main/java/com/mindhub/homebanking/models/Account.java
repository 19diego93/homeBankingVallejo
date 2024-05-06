package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private LocalDate creationDate;

    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client accountOwner;

    @OneToMany(mappedBy = "accountId", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() {}

    public Account(String number, LocalDate creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {return transactions;}

    public Long getId() {return id;}

    public Client getOwner() {return accountOwner;}

    public void setOwner(Client owner) {this.accountOwner = owner;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public LocalDate getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDate creationDate) {this.creationDate = creationDate;}

    public double getBalance() {return balance;}

    public void setBalance(double balance) {this.balance = balance;}

    public void addTransaction(Transaction transaction){
       transaction.setAccount(this);
        transactions.add(transaction);
    }
}
