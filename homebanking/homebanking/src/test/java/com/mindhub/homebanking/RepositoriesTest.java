package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardRepository cardRepository;

    //LOANSTEST
       @Test
    public void existLoans() {

       List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));
    }

    @Test

    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,hasItem(hasProperty("name", is("Personal"))));
    }

    //CLIENTSTEST
    @Test
    public void existClients() {

       List<Client> clients = clientRepository.findAll();

        assertThat(clients,is(not(empty())));
    }

    @Test

    public void findClientById(){

        Client client = clientRepository.findById(1L).orElse(null);

        assertThat(client,is(not(nullValue())));
    }
    @Test
    public void existsClientByEmail() {

        assertThat(clientRepository.existsByEmail("melba@mindhub.com"),is(true));
    }
    @Test
    public void findClientByEmail() {

        Client client = clientRepository.findByEmail("melba@mindhub.com");
        assertThat(client,is(not(nullValue())));
    }

    @Test
    @Transactional
    public void saveClient(){

        Client client = new Client("Melba","Lopez","melba@lopez.com","12345678");
        clientRepository.save(client);
        assertThat(clientRepository.findByEmail("melba@lopez.com"),is(client));
    }

    //ACCOUNTSTEST
   @Test
    public void existAccounts() {

       List<Account> accounts = accountRepository.findAll();

        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void findAccountById(){

        Account account = accountRepository.findById(1L).orElse(null);

        assertThat(account,is(not(nullValue())));
    }

    @Test
    public void findAccountByNumber() {
    Account account = accountRepository.findByNumber("VNI001");
    assertThat(account,is(not(nullValue())));
    }

    @Test
    public void existAccountByNumber() {
    assertThat(accountRepository.existsByNumber("VNI001"),is(true));
    }

    //TRANSACTIONSTEST

    @Test
    public void existTransaction() {
    List<Transaction> transactions = transactionRepository.findAll();
    assertThat(transactions,is(not(empty())));
    }

    @Test
    @Transactional
    public void saveTransaction(){
        Transaction transaction = new Transaction(TransactionType.CREDIT, 100.0, "test", LocalDateTime.now());
        transactionRepository.save(transaction);
        Transaction transactionSaved = transactionRepository.findById(transaction.getId()).orElse(null);
        assertThat(transaction.getDescription(),is(transactionSaved.getDescription()));
    }
//CARDSTEST
    @Test
    public void existCards(){

        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }

    @Test
    public void existCardByNumber() {
        Card card = cardRepository.findByNumber("3325-6745-7876-4445");
        assertThat(card,is(not(nullValue())));
    }



}

