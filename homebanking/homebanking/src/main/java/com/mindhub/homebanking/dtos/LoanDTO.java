package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.Set;

public class LoanDTO {

    private String name;

    private double maxAmount;

    private Set<Integer> payments;

    public LoanDTO(Loan loan) {
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }
}
