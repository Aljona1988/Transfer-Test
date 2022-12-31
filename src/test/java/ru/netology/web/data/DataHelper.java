package ru.netology.web.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

        public static VerificationCode getVerificationCode() {
            return new VerificationCode("12345");
        }


    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static CardData getFirstCard() {

        return new CardData("5559 0000 0000 0001", "firstCard");
    }

    public static CardData getSecondCard() {
        return new CardData("5559 0000 0000 0002", "secondCard");
    }

    public static int validSum(int balance) {
        return new Random().nextInt(balance) + 1;
    }

    public static int inValidSum(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000);
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class CardData {
        String cardNum;
        String Id;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;

}

}


