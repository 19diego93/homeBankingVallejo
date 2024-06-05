package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private boolean isAdmin = false;

    @OneToMany(mappedBy = "accountOwner", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<Card> cards = new HashSet<>();

    public Client() {}

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Client(String firstName, String lastName, String email, String password, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Set<Card> getCards() {return cards;}

    public Set<ClientLoan> getClientLoans() {return clientLoans;}

    public Set<Account> getAccounts() {return accounts;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public Long getId(){return id;}

    public void addAccount(Account account){
        account.setOwner(this);
        accounts.add(account);
    }
    public void addLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    public List<Loan> getLoans() {
        return this.getClientLoans().stream().map(loan -> loan.getLoan()).collect(toList());}

    public void addCard(Card card){
        card.setClient(this);
        cards.add(card);
        card.setCardHolder(this.firstName+" "+this.lastName);
    }

    @Override
    public String toString() {
        return "Client{" +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", id=" + id +
                '}';
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
