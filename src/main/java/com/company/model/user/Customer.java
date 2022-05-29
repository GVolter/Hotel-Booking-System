package com.company.model.user;

public class Customer extends User{
    public Customer(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
    }

    public Customer(String firstName, String lastName, String username, String email, String password, boolean isBlocked) {
        super(firstName, lastName, username, email, password, isBlocked);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
