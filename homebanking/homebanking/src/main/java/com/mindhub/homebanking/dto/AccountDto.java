package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;

public class AccountDto {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }
}