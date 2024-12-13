package model;

public class CardReader {
    private String cardNumber;
    private String tempPassword;
    private int cash;

    public CardReader(String cardNumber, String tempPassword) {
        this.cardNumber = cardNumber;
        this.tempPassword = tempPassword;
        this.cash = 1000;
    }

    public int getCash() {
        return cash;
    }




}
