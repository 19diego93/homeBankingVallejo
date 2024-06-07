package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    Loan getLoanById(Long id);

    Loan getLoanByName(String name);

    List<Loan> getListLoans();

List<LoanDTO> getListLoansDto(List <Loan> loans);

}
