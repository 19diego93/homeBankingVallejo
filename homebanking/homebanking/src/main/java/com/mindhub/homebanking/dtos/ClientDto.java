package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDto {

    private Long id;

    private String name;

    private String email;

    private Set <AccountDto> accounts;

    private Set<ClientLoanDto> loans = new HashSet<>();

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getFirstName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts()
                .stream()
                .map(account -> new AccountDto(account))
                .collect(Collectors.toSet());
        this.loans = client.getClientLoans().stream().map(clientLoanDto -> new ClientLoanDto(clientLoanDto)).collect(Collectors.toSet());
    }

    public Long getId() {return id;}

    public String getName() {return name;}

    public String getEmail() {return email;}

    public Set<AccountDto> getAccounts() {return accounts;}

    public Set<ClientLoanDto> getLoans() {return loans;}

}
