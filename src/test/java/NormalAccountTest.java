import dmytroporoshyn.NormalAccount;
import dmytroporoshyn.Person;
import dmytroporoshyn.PremiumAccount;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NormalAccountTest {

    @Test
    public void testBankchargePremiumLessThanAWeek() {
        PremiumAccount account = getPremiumAccountForPerson(5);
        assertThat(account.bankcharge(), is(14.5));
    }

    @Test
    public void testBankchargePremiumMoreThanAWeek() {
        PremiumAccount premiumAccount = getPremiumAccountForPerson(9);
        assertThat(premiumAccount.bankcharge(), is(16.5));
    }

    @Test
    public void testOverdraftFeePremium() {
        PremiumAccount premiumAccount = getPremiumAccountForCompany(9);
        assertThat(premiumAccount.getOverdraftFee(), is(0.10));
    }

    @Test
    public void testOverdraftFeeNotPremium() {
        NormalAccount account = getNormalAccount();
        assertThat(account.getOverdraftFee(), is(0.20));
    }

    @Test
    public void testPrintCustomer() {
        NormalAccount account = getNormalAccount();
        Person person = new Person("xxx@mail.com", account, "xxx", "xxx");
        account.setCustomer(person);
        assertThat(account.getCustomer().toString(), is("xxx xxx@mail.com"));
    }

    private NormalAccount getNormalAccount() {
        return new NormalAccount(9);
    }

    private PremiumAccount getPremiumAccountForPerson(int daysOverdrawn) {
        PremiumAccount premiumAccount = new PremiumAccount();
        premiumAccount.setDaysOverdrawn(daysOverdrawn);
        return premiumAccount;
    }

    private PremiumAccount getPremiumAccountForCompany(int daysOverdrawn) {
        PremiumAccount premiumAccount = new PremiumAccount(
            PremiumAccount.NO_OVERDRAFT_FEE_DISCOUNT,
            0.5
        );
        premiumAccount.setDaysOverdrawn(daysOverdrawn);
        return premiumAccount;
    }
}
