package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    Loan getLoanById(Long id);

    Loan getLoanByName(String name);

    List getListLoans();

List getListLoansDto(List <Loan> loans);

}
