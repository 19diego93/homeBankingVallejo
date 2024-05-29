package com.mindhub.homebanking.dtos;

public record NewTransactionDTO(Double amount,String description,String toAccountNumber,String fromAccountNumber) {
}
