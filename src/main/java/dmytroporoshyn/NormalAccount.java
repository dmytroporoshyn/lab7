package dmytroporoshyn;

public class NormalAccount extends Account {

    private static final String NORMAL_ACCOUNT_TYPE_NAME = "normal";

    private static final double DAYS_OVERDRAWN_CHARGE_COEFFICIENT = 1.75;

    private static final double NORMAL_ACCOUNT_OVERDRAFT_FEE = 0.20;

    private static final int NORMAL_ACCOUNT_OVERDRAFT_FEE_DISCOUNT = 1;

    private static final int NORMAL_ACCOUNT_OVERDRAFT_FEE_DISCOUNT_COEFFICIENT =
            1;

    private static final int NO_DAYS_OVERDRAWN = 0;

    public NormalAccount(int daysOverdrawn) {
        setDaysOverdrawn(daysOverdrawn);
        setOverdraftFeeDiscount(NORMAL_ACCOUNT_OVERDRAFT_FEE_DISCOUNT);
    }

    public NormalAccount() {
        this(NO_DAYS_OVERDRAWN);
    }

    @Override
    public double getOverdraftFee() {
        return NORMAL_ACCOUNT_OVERDRAFT_FEE;
    }

    @Override
    public String getAccountTypeName() {
        return NORMAL_ACCOUNT_TYPE_NAME;
    }

    @Override
    public double overdraftCharge() {
        return getDaysOverdrawn() * DAYS_OVERDRAWN_CHARGE_COEFFICIENT;
    }

    @Override
    public double getOverdraftFeeDiscountCoefficient() {
        return NORMAL_ACCOUNT_OVERDRAFT_FEE_DISCOUNT_COEFFICIENT;
    }
}
