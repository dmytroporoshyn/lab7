package dmytroporoshyn;

public abstract class Customer {

    private final String name;
    private final Account account;
    private String email;

    public Customer(String name, String email, Account account) {
        this.name = name;
        this.email = email;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getName() + " " + getEmail();
    }
}
