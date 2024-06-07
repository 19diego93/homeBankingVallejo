package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.ClientLoan;

public interface ClientLoanService {

    ClientLoan getClientLoanByLoanId(Long loanId);
    void saveClientLoan(ClientLoan clientLoan);
}
