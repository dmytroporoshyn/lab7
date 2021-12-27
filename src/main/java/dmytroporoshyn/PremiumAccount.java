package dmytroporoshyn;

public class PremiumAccount extends Account {

    public static final int NO_OVERDRAFT_FEE_DISCOUNT = 1;

    private static final double PREMIUM_ACCOUNT_OVERDRAFT_FEE = 0.10;

    private static final String PREMIUM_ACCOUNT_TYPE_NAME = "premium";

    private static final int NO_OVERDRAFT_FEE_DISCOUNT_COEFFICIENT = 1;

    private static final int OVERDRAFT_CHARGE_BASE = 10;

    private static final int OVERDRAFT_CHARGE_BASE_DAYS_LENGTH = 7;

    public PremiumAccount(
            double overdraftFeeDiscount,
            double overdraftFeeDiscountCoefficient
    ) {
        setOverdraftFeeDiscount(overdraftFeeDiscount);
        setOverdraftFeeDiscountCoefficient(overdraftFeeDiscountCoefficient);
    }

    public PremiumAccount() {
        this(NO_OVERDRAFT_FEE_DISCOUNT, NO_OVERDRAFT_FEE_DISCOUNT_COEFFICIENT);
    }

    @Override
    public double overdraftCharge() {
        double result = OVERDRAFT_CHARGE_BASE;
        if (getDaysOverdrawn() > OVERDRAFT_CHARGE_BASE_DAYS_LENGTH)
            result += (getDaysOverdrawn() - OVERDRAFT_CHARGE_BASE_DAYS_LENGTH);
        return result;
    }

    @Override
    public double getOverdraftFee() {
        return PREMIUM_ACCOUNT_OVERDRAFT_FEE;
    }

    @Override
    public String getAccountTypeName() {
        return PREMIUM_ACCOUNT_TYPE_NAME;
    }
}
