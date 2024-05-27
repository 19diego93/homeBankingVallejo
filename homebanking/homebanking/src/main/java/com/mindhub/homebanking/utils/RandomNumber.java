package com.mindhub.homebanking.utils;

import java.util.Random;

public class RandomNumber {

    public static String eightDigits() {
        Random random = new Random();
        int number = random.nextInt(100000000); // Genera un número entre 0 y 99999999
        return String.format("%08d", number);  // Completa con ceros a la izquierda si es necesario
    }

    public static String fourDigits() {
        Random random = new Random();
        int number = random.nextInt(10000);
        return String.format("%04d", number);
    }

    public static String threeDigits() {
        Random random = new Random();
        int number = random.nextInt(1000);
        return String.format("%03d", number);
    }
}
