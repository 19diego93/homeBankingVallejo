package com.mindhub.homebanking.dtos;


public record LoanApplicationDTO(Double amount, Integer payments, Long idLoan, String accountNumber) {
}
