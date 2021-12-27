package dmytroporoshyn;

public class PersonUtils {

    public static String toFullNameDaysOverdrawnString(Person person) {
        return person.getFullName()
                + AccountUtils.toIbanDaysOverdrawnString(person.getAccount());
    }

    public static String toPersonFullNameIbanMoneyString(Person person) {
        return person.getFullName()
                + AccountUtils.toIbanMoneyString(person.getAccount());
    }
}
