package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDto {

    private long id;

    private long loan_id;

    private String name;

    private double amount;

    private int payments;

    public ClientLoanDto(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loan_id = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayment();
    }

    public long getId() {return id;}

    public long getLoan_id() {return loan_id;}

    public String getName() {return name;}

    public double getAmount() {return amount;}

    public int getPayments() {return payments;}
}
