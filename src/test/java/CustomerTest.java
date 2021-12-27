import dmytroporoshyn.*;
import org.junit.Test;
import dmytroporoshyn.AccountUtils;
import dmytroporoshyn.PersonUtils;

import java.util.Currency;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerTest {

    private static final double
        OVERDRAFT_FEE_DISCOUNT_COEFFICIENT_PREMIUM_COMPANIES = 0.5;

    private static final Currency
        EURO = Currency.getInstance("EUR");

    @Test
    public void testWithdrawPersonWithNormalAccount() {
        Account account = getAccountByTypeAndMoney(false, 34.0);
        Customer customer = getPersonCustomer(account);
        customer.getAccount().withdraw(createTenEuro());
        assertThat(account.getBalance().getMoney(), is(24.0));
    }

    @Test
    public void testWithdrawPersonWithNormalAccountAndOverdraft() {
        Account account = getAccountByTypeAndMoney(false, -10.0);
        Customer customer = getPersonCustomer(account);
        customer.getAccount().withdraw(createTenEuro());
        assertThat(account.getBalance().getMoney(), is(-22.0));
    }

    @Test
    public void testWithdrawPersonWithPremiumAccount() {
        Account account = getAccountByTypeAndMoney(true, 34.0);
        Customer customer = getPersonCustomer(account);
        customer.getAccount().withdraw(createTenEuro());
        assertThat(account.getBalance().getMoney(), is(24.0));
    }

    @Test
    public void testWithdrawPersonWithPremiumAccountAndOverdraft() {
        Account account = getAccountByTypeAndMoney(true, -10.0);
        Customer customer = getPersonCustomer(account);
        customer.getAccount().withdraw(createTenEuro());
        assertThat(account.getBalance().getMoney(), is(-21.0));
    }

    @Test
    public void testWithdrawCompanyWithNormalAccount() {
        Account account = getAccountByTypeAndMoney(false, 34);
        Customer customer = getCompanyCustomer(account);
        customer.getAccount().withdraw(createTenEuro());
        assertThat(account.getBalance().getMoney(), is(24.0));
    }

    @Test
    public void testWithdrawCompanyWithNormalAccountAndOverdraft() {

        Account account = getAccountByTypeAndMoney(false, -10);
        Customer customer = getCompanyCustomer(account);
        customer.getAccount().withdraw(createTenEuro());
        assertThat(account.getBalance().getMoney(), is(-21.0));
    }

    @Test
    public void testWithdrawCompanyWithPremiumAccount() {
        Account account = getAccountByTypeAndMoney(true, 34);
        Customer customer = getCompanyCustomer(account);
        customer.getAccount().withdraw(createTenEuro());
        assertThat(account.getBalance().getMoney(), is(24.0));
    }

    @Test
    public void testWithdrawCompanyWithPremiumAccountAndOverdraft() {
        Account account = getAccountByTypeAndMoney(true, -10);
        account.setOverdraftFeeDiscountCoefficient(
            OVERDRAFT_FEE_DISCOUNT_COEFFICIENT_PREMIUM_COMPANIES
        );
        Customer customer = getCompanyCustomer(account);
        customer.getAccount().withdraw(createTenEuro());
        assertThat(account.getBalance().getMoney(), is(-20.25));
    }

    @Test
    public void testPrintCustomerDaysOverdrawn() {
        Person person = getPersonWithAccount(false);
        assertThat(
            PersonUtils.toFullNameDaysOverdrawnString(person),
            is(
                "Dmytro Poroshyn Account: IBAN: UA903052992990004149123456789,"
                    + " Days Overdrawn: 9"
            )
        );
    }

    @Test
    public void testPrintCustomerMoney() {
        Person person = getPersonWithAccount(false);
        assertThat(
            PersonUtils.toPersonFullNameIbanMoneyString(person),
            is("Dmytro Poroshyn Account: IBAN: UA903052992990004149123456789, Money: 34.0")
        );
    }

    @Test
    public void testPrintCustomerAccountNormal() {
        Customer customer = getPersonWithAccount(false);
        assertThat(
            AccountUtils.toIbanMoneyTypeString(customer.getAccount()),
            is(
                "Account: IBAN: UA903052992990004149123456789, "
                    + "Money: 34.0, Account type: normal"
            )
        );
    }

    @Test
    public void testPrintCustomerAccountPremium() {
        Customer customer = getPersonWithAccount(true);
        assertThat(
            AccountUtils.toIbanMoneyTypeString(customer.getAccount()),
            is(
                "Account: IBAN: UA903052992990004149123456789, "
                    + "Money: 34.0, Account type: premium"
            )
        );
    }

    private Person getPersonWithAccount(boolean premium) {
        Account account = premium ? new PremiumAccount() : new NormalAccount();
        account.setDaysOverdrawn(9);
        Person person = getPersonCustomer(account);
        account.setIban("UA903052992990004149123456789");
        account.setBalance(
            new Money(EURO, 34.0)
        );
        return person;
    }

    private Account getAccountByTypeAndMoney(boolean premium, double money) {
        Account account = premium ? new PremiumAccount() : new NormalAccount();
        account.setDaysOverdrawn(9);
        account.setIban("UA903052992990004149123456789");
        account.getBalance().setMoney(money);
        account.getBalance().setCurrency(EURO);
        return account;
    }

    private Person getPersonCustomer(Account account) {
        Person person = new Person("dmytor.poroshyn@mail.com", account, "Dmytro", "Poroshyn");
        account.setCustomer(person);
        return person;
    }

    private Customer getCompanyCustomer(Account account) {
        Company company = new Company("company", "company@mail.com", account);
        account.setOverdraftFeeDiscount(0.50);
        account.setCustomer(company);
        return company;
    }

    private Money createTenEuro() {
        return new Money(EURO, 10);
    }
}
