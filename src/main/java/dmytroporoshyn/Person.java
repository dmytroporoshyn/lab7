package dmytroporoshyn;

public class Person extends Customer {

    private final String surname;

    public Person(String email, Account account, String name, String surname) {
        super(name, email, account);

        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return getName() + " " + getSurname() + " ";
    }
}
