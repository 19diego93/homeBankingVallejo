package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.NewTransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.Type;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<?> transaction(@RequestBody NewTransactionDTO newTransactionDTO, Authentication authentication){
try {
    Client client = clientRepository.findByEmail(authentication.getName());

    if (client == null) {
        return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
    }

    if (newTransactionDTO.fromAccountNumber().isBlank()) {
        return new ResponseEntity<>("You need to select your account", HttpStatus.FORBIDDEN);
    }

    if (newTransactionDTO.amount().isNaN()) {
        return new ResponseEntity<>("You need to set a valid amount ", HttpStatus.FORBIDDEN);
    }

    if (newTransactionDTO.amount() <= 0.0) {
        return new ResponseEntity<>("You cannot transfer zero or below ", HttpStatus.FORBIDDEN);
    }

    if (newTransactionDTO.toAccountNumber().isBlank()) {
        return new ResponseEntity<>("You need to provide an account destination ", HttpStatus.FORBIDDEN);
    }

    if (newTransactionDTO.toAccountNumber().matches(newTransactionDTO.fromAccountNumber())) {
        return new ResponseEntity<>("You need to provide a different account destination ", HttpStatus.FORBIDDEN);
    }

    Account fromAccount = accountRepository.findByNumber(newTransactionDTO.fromAccountNumber());
    if (fromAccount == null) {
        return new ResponseEntity<>("this account does not exist", HttpStatus.FORBIDDEN);
    }

    Account toAccount = accountRepository.findByNumber(newTransactionDTO.toAccountNumber());
    if (toAccount == null) {
        return new ResponseEntity<>("this account does not exist", HttpStatus.FORBIDDEN);
    }

    if (fromAccount.getBalance() < newTransactionDTO.amount()) {
        return new ResponseEntity<>("You do not have enough money in this account", HttpStatus.FORBIDDEN);
    }

    /* tenia pensado seguir con un set balance a la account que envia, restandole el monto, y reptir el proceso con la account que recibe (creidto) sumandole el monto al balance y luego hacer los dos add transaccion  y luego enviar return un responseentity  status ok */

    //Debito del amount
    fromAccount.setBalance(fromAccount.getBalance() - newTransactionDTO.amount());
    //Acredito el amount
    toAccount.setBalance(toAccount.getBalance() + newTransactionDTO.amount());

    Transaction fromTransaction = new Transaction(Type.DEBIT, -newTransactionDTO.amount(), newTransactionDTO.description(), LocalDateTime.now());

    Transaction toTransaction = new Transaction(Type.CREDIT, newTransactionDTO.amount(), newTransactionDTO.description(), LocalDateTime.now());

    fromAccount.addTransaction(fromTransaction);
    toAccount.addTransaction(toTransaction);

    fromTransaction.setAccount(fromAccount);
    toTransaction.setAccount(toAccount);

    transactionRepository.save(fromTransaction);
    transactionRepository.save(toTransaction);

    return new ResponseEntity<>("Transaction completed successfully", HttpStatus.CREATED);

} catch (Exception e){
    /* tiene como propósito imprimir el seguimiento de la pila de la excepción (stack trace) a
     la consola. Esto es útil para la depuración, ya que proporciona información detallada sobre
      la excepción, incluyendo el tipo de excepción y la secuencia de llamadas de método que
       condujeron a la excepción. */
    e.printStackTrace();
    return new ResponseEntity<>("An error occurred while processing the transaction", HttpStatus.INTERNAL_SERVER_ERROR);
}
    }

}
