package dmytroporoshyn;

import java.util.Currency;

public abstract class Account {

    private static final Currency DEFAULT_CURRENCY =
            Currency.getInstance("EUR");

    private static final double NO_MONEY = 0.0;

    private static final double BASE_BANKCHARGE = 4.5;

    // 1 means 100%
    private static final int DEFAULT_OVERDRAFT_FEE_DISCOUNT_COEFFICIENT = 1;

    private String iban;

    private Money balance;

    private int daysOverdrawn;

    private Customer customer;

    private double overdraftFee;

    private double overdraftFeeDiscount;

    private double overdraftFeeDiscountCoefficient;

    public Account() {
        setOverdraftFeeDiscountCoefficient(
                DEFAULT_OVERDRAFT_FEE_DISCOUNT_COEFFICIENT
        );
        setBalance(new Money(DEFAULT_CURRENCY, NO_MONEY));
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public int getDaysOverdrawn() {
        return daysOverdrawn;
    }

    public void setDaysOverdrawn(int daysOverdrawn) {
        this.daysOverdrawn = daysOverdrawn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(double overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public double getOverdraftFeeDiscount() {
        return overdraftFeeDiscount;
    }

    public void setOverdraftFeeDiscount(double overdraftFeeDiscount) {
        this.overdraftFeeDiscount = overdraftFeeDiscount;
    }

    public double getOverdraftFeeDiscountCoefficient() {
        return overdraftFeeDiscountCoefficient;
    }

    public void setOverdraftFeeDiscountCoefficient(double overdraftFeeDiscountCoefficient) {
        this.overdraftFeeDiscountCoefficient = overdraftFeeDiscountCoefficient;
    }

    public double bankcharge() {
        double result = BASE_BANKCHARGE;

        result += overdraftCharge();

        return result;
    }

    /**
     * Performs a withdraw bank operation depending on the current balance.
     * If the balance is positive,
     * {@link #calculateMoneyAfterWithdraw just the sum is withdrawn},
     * else {@link #calculateMoneyAfterWithdrawCredit additional fee is kept
     * from the operation}.
     *
     * @param money money to be withdrawn from the {@link #balance balance}.
     * @throws IllegalArgumentException if the currencies of the {@link #balance balance}
     *                                  and passed money are different.
     * @see #calculateMoneyAfterWithdraw
     * @see #calculateMoneyAfterWithdrawCredit
     */
    public void withdraw(Money money) {
        if (!getBalance().getCurrency().equals(money.getCurrency())) {
            throw new IllegalArgumentException(
                    "Can't extract withdraw "
                            + money.getCurrency().getCurrencyCode()
            );
        }
        getBalance().setMoney(
                getBalance().getMoney() < 0
                        ? calculateMoneyAfterWithdrawCredit(money.getMoney())
                        : calculateMoneyAfterWithdraw(money.getMoney())
        );
    }

    protected abstract String getAccountTypeName();

    protected abstract double overdraftCharge();

    private double calculateMoneyAfterWithdraw(double sum) {
        return getBalance().getMoney() - sum;
    }

    private double calculateMoneyAfterWithdrawCredit(double sum) {
        return (getBalance().getMoney() - sum)
                - (sum
                * getOverdraftFee()
                * getOverdraftFeeDiscount()
                * getOverdraftFeeDiscountCoefficient());
    }
}
