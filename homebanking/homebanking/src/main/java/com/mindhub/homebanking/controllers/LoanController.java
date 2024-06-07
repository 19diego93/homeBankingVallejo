package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public ResponseEntity<?> getLoans(Authentication authentication) {

        List<LoanDTO> loansListDTO = loanService.getListLoansDto(loanService.getListLoans());
        if (!loansListDTO.isEmpty()) {
            return new ResponseEntity<>(loansListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are no loans available", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> createLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        try {
            Client client = clientService.getClientByEmail(authentication.getName());

            if (loanApplicationDTO.amount() == null) {
                return new ResponseEntity<>("Amount can not be empty", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.payments() == null) {
                return new ResponseEntity<>("Instalments can not be empty", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.accountNumber() == null) {
                return new ResponseEntity<>("Account can not be empty", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.idLoan() == null) {
                return new ResponseEntity<>("Loan can not be empty", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.idLoan().toString().isBlank()) {
                return new ResponseEntity<>("Type of loan cannot be empty", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.accountNumber().isBlank()) {
                return new ResponseEntity<>("The account number cannot be empty", HttpStatus.FORBIDDEN);
            }

            if (accountService.getAccountByNumber(loanApplicationDTO.accountNumber()) == null) {
                return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
            }

            Loan loan = loanService.getLoanById(loanApplicationDTO.idLoan());

            if (loan == null) {
                return new ResponseEntity<>("The loan does not exist", HttpStatus.FORBIDDEN);
            }

            if (!client.getAccounts().contains(accountService.getAccountByNumber(loanApplicationDTO.accountNumber()))) {
                return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
            }


            if (loanApplicationDTO.amount().toString().isBlank()) {
                return new ResponseEntity<>("The amount cannot be empty", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.payments().toString().isBlank()) {
                return new ResponseEntity<>("The number of payments cannot be empty", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.amount() <= 0) {
                return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.payments() <= 0) {
                return new ResponseEntity<>("The number of payments must be greater than 0", HttpStatus.FORBIDDEN);
            }

            if (!loan.getPayments().contains(loanApplicationDTO.payments())) {
                return new ResponseEntity<>("The quantity of installments does not match any of the options for this loan type", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.amount() > loan.getMaxAmount()) {
                return new ResponseEntity<>("The amount exceeds the maximum allowed for this loan type", HttpStatus.FORBIDDEN);
            }
            // quiero 1 sola repeticion de loans
            if (client.getClientLoans().stream().anyMatch(clientLoan -> clientLoan.getLoan().getId().equals(loanApplicationDTO.idLoan()))) {
                return new ResponseEntity<>("The client already has a loan of this type", HttpStatus.FORBIDDEN);
            }

            Account account = accountService.getAccountByNumber(loanApplicationDTO.accountNumber());

            account.setBalance(account.getBalance() + loanApplicationDTO.amount());

            Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.amount(), "Loan application", LocalDateTime.now());

            account.addTransaction(transaction);

            transaction.setAccount(account);

            int instalments = loanApplicationDTO.payments();

            double interestRate;
            if (instalments == 12) {
                interestRate = 1.20;
            } else if (instalments < 12) {
                interestRate = 1.15;
            } else {
                interestRate = 1.25;
            }

            ClientLoan clientLoan = new ClientLoan((loanApplicationDTO.amount() * interestRate), loanApplicationDTO.payments());

            clientLoan.setClient(client);
            clientLoan.setLoan(loan);
            client.addLoan(clientLoan);
            loan.addClient(clientLoan);

            transactionService.saveTransaction(transaction);
            clientLoanService.saveClientLoan(clientLoan);

            return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
