package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository accountRepository){
		return (args)->{
			Client client1= new Client("Melba","Morel","melba@mindhub.com");
			Client client2=new Client("Diego","Vallejo","dv93@mindhub.com");
			Account account1 = new Account("VNI001", LocalDate.now(),5000);
			Account account2 = new Account("VNI002", LocalDate.of(2024, 5, 4),7500);
			Account account3 = new Account("VNI003", LocalDate.of(2024, 5, 1),9);

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

		  };

}
}

