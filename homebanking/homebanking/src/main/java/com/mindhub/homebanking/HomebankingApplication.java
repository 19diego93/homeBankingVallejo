package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {
	
//	@Autowired
//	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

//@Bean
//	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
//		return (args)->{
//			Client client1= new Client("Melba","Morel","melba@mindhub.com", passwordEncoder.encode("1234"));
//			Client client2= new Client("Diego","Vallejo","dv93@mindhub.com",passwordEncoder.encode("4321"));
//			Client Admin= new Client("Admin","Admin","admin@homebanking.com",passwordEncoder.encode("admin"),true);
//
//			Account account1 = new Account("VNI001",5000);
//			Account account2 = new Account("VNI002",7500);
//			Account account3 = new Account("VNI003",9);
//
//			Transaction transaction1 = new Transaction(TransactionType.CREDIT,800,"Annual payment", LocalDateTime.now());
//			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-300,"Daily payment", LocalDateTime.now());
//			Transaction transaction3 = new Transaction(TransactionType.CREDIT,1000,"Annual payment", LocalDateTime.now());
//			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-3000,"Weekly payment", LocalDateTime.now());
//			Transaction transaction5 = new Transaction(TransactionType.CREDIT,500,"Monthly payment", LocalDateTime.now());
//			Transaction transaction6 = new Transaction(TransactionType.DEBIT,-600,"Tax payment", LocalDateTime.now());
//
//			Loan loan1 = new Loan("Mortgage",500000, Set.of(12,24,36,48,60));
//			Loan loan2 = new Loan("Personal",100000,Set.of(6,12,24));
//			Loan loan3 = new Loan("Automotive",300000,Set.of(6,12,24,36));
//
//			ClientLoan clientLoan1 =new ClientLoan(400000,60);
//			ClientLoan clientLoan2 =new ClientLoan(50000,12);
//			ClientLoan clientLoan3 =new ClientLoan(100000,24);
//			ClientLoan clientLoan4 =new ClientLoan(200000,36);
//
//			Card card1 = new Card(CardType.DEBIT,CardColor.GOLD,"3325-6745-7876-4445",345);
//			Card card2 = new Card(CardType.CREDIT,CardColor.TITANIUM,"2345-6745-8876-4567",123);
//			Card card3 = new Card(CardType.CREDIT,CardColor.SILVER,"1111-2345-4566-9898",999);
//
//			account1.setOwner(client1);
//			account2.setOwner(client1);
//			account3.setOwner(client2);
//
//			client1.addAccount(account1);
//			client1.addAccount(account2);
//			client2.addAccount(account3);
//
//			transaction1.setAccount(account1);
//			transaction2.setAccount(account1);
//			transaction3.setAccount(account2);
//			transaction4.setAccount(account2);
//			transaction5.setAccount(account3);
//			transaction6.setAccount(account3);
//
//			System.out.println(transaction1);
//
//			clientLoan2.setLoan(loan2);
//			clientLoan2.setClient(client1);
//			client1.addLoan(clientLoan2);
//			loan2.addClient(clientLoan2);
//
//			clientLoan1.setLoan(loan1);
//			clientLoan1.setClient(client1);
//			client1.addLoan(clientLoan1);
//			loan1.addClient(clientLoan1);
//
//			clientLoan3.setLoan(loan2);
//			clientLoan3.setClient(client2);
//			client2.addLoan(clientLoan3);
//			loan2.addClient(clientLoan3);
//
//			clientLoan4.setLoan(loan3);
//			clientLoan4.setClient(client2);
//			client2.addLoan(clientLoan4);
//			loan3.addClient(clientLoan4);
//
//			card1.setClient(client1);
//			client1.addCard(card1);
//
//			card2.setClient(client1);
//			client1.addCard(card2);
//
//			card3.setClient(client2);
//			client2.addCard(card3);
//
//			clientRepository.save( client1 );
//			clientRepository.save( client2 );
//			clientRepository.save( Admin );
//
//			accountRepository.save( account1 );
//			accountRepository.save( account2 );
//			accountRepository.save( account3);
//
//			transactionRepository.save(transaction1);
//			transactionRepository.save(transaction2);
//			transactionRepository.save(transaction3);
//			transactionRepository.save(transaction4);
//			transactionRepository.save(transaction5);
//			transactionRepository.save(transaction6);
//
//			loanRepository.save(loan1);
//			loanRepository.save(loan2);
//			loanRepository.save(loan3);
//
//			clientLoanRepository.save(clientLoan1);
//			clientLoanRepository.save(clientLoan2);
//			clientLoanRepository.save(clientLoan3);
//			clientLoanRepository.save(clientLoan4);
//
//			cardRepository.save(card1);
//			cardRepository.save(card2);
//			cardRepository.save(card3);
//
//			System.out.println(client1.getLoans());
//			System.out.println(loan2.getClients());
//		};
//	}
}

