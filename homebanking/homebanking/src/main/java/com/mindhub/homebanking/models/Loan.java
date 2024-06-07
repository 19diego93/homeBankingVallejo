package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double maxAmount;

    @ElementCollection
    private Set<Integer> payments = new HashSet<>();

    @OneToMany(mappedBy ="loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clients = new HashSet<>();

    public Loan() {}

    public Loan(String name, double maxAmount, Set<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Set<ClientLoan> getClientLoans() {return clients;}

    public Long getId() {return id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getMaxAmount() {return maxAmount;}

    public void setMaxAmount(double maxAmount) {this.maxAmount = maxAmount;}

    public Set<Integer> getPayments() {return payments;}

    public void setPayments(Set<Integer> payments) {this.payments = payments;}

    public void addClient(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clients.add(clientLoan);
    }

    public List<Client> getClients() {
        return this.getClientLoans().stream().map(client -> client.getClient()).collect(toList());}

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAmount=" + maxAmount +
                ", payments=" + payments +
                '}';
    }
}
