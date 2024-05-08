package com.mindhub.homebanking.models;

import jakarta.persistence.*;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double amount;

    private Integer payment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    public ClientLoan() {}

    public ClientLoan(double amount, Integer payment) {
        this.amount = amount;
        this.payment = payment;
    }

    public long getId() {return id;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public Integer getPayment() {return payment;}

    public void setPayment(Integer payment) {this.payment = payment;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public Loan getLoan() {return loan;}

    public void setLoan(Loan loan) {this.loan = loan;}
}
