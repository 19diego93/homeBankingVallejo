package com.mindhub.homebanking.utils;

import java.util.Random;

public final class AccountUtils {

    private AccountUtils() {}

    public static String createAccountNumber() {
        Random random = new Random();
        int number = random.nextInt(100000000); // Genera un número entre 0 y 99999999
        String accountNumber = String.format("%08d", number);  // Completa con ceros a la izquierda si es necesario

        return "VIN-"+accountNumber;
    }
}
