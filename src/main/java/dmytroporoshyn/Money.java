package dmytroporoshyn;

import java.util.Currency;

public class Money {

    private Currency currency;

    private double money;

    public Money(Currency currency, double money) {
        this.currency = currency;
        this.money = money;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
