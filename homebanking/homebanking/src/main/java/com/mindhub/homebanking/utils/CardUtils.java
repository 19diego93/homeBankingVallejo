package com.mindhub.homebanking.utils;

import java.util.Random;

public final class CardUtils {

    private CardUtils() {}

    public static String createCardNumber() {
        Random random = new Random();
        int number1 = random.nextInt(10000);
        String formatNumber1 = String.format("%04d", number1);
        int number2 = random.nextInt(10000);
        String formatNumber2 = String.format("%04d", number2);
        int number3 = random.nextInt(10000);
        String formatNumber3 = String.format("%04d", number3);
        int number4 = random.nextInt(10000);
        String formatNumber4 = String.format("%04d", number4);

       String cardNumber= formatNumber1 + "-" + formatNumber2 + "-" + formatNumber3 + "-" + formatNumber4;
        return cardNumber;
    }
    public static String createCvv() {
        Random random = new Random();
        int number = random.nextInt(1000);
        return String.format("%03d", number);
    }


}
