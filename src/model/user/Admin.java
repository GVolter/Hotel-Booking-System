package model.user;

public class Admin extends User{
    public Admin(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
