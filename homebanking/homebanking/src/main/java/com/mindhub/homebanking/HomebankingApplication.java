package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args)->{
			Client client1= new Client("Melba","Morel","melba@mindhub.com");
			Client client2=new Client("Diego","Vallejo","dv93@mindhub.com");
			Account account1 = new Account("VNI001", LocalDate.now(),5000);
			Account account2 = new Account("VNI002", LocalDate.of(2024, 5, 4),7500);
			Account account3 = new Account("VNI003", LocalDate.of(2024, 5, 1),9);
			Transaction transaction1 = new Transaction(TransactionType.CREDIT,800,"Annual payment", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-300,"Daily payment", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.CREDIT,1000,"Annual payment", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-3000,"Weekly payment", LocalDateTime.now());
			Transaction transaction5 = new Transaction(TransactionType.CREDIT,500,"Monthly payment", LocalDateTime.now());
			Transaction transaction6 = new Transaction(TransactionType.DEBIT,-600,"Tax payment", LocalDateTime.now());

			transaction1.setAccount(account1);
			transaction2.setAccount(account1);
			transaction3.setAccount(account2);
			transaction4.setAccount(account2);
			transaction5.setAccount(account3);
			transaction6.setAccount(account3);
			account1.setOwner(client1);
			account2.setOwner(client1);
			account3.setOwner(client2);
			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);


			repository.save( client1 );
			repository.save( client2 );
			accountRepository.save( account1 );
			accountRepository.save( account2 );
			accountRepository.save( account3);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
		  };

}
}

