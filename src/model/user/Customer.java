package model.user;

public class Customer extends User{
    public Customer(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
