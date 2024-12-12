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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }


}
