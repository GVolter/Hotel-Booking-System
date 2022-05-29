package com.company.model.user;

import com.company.model.hotel.Hotel;

public class HotelManager extends User{
    private Hotel hotel;

    public HotelManager(String firstName, String lastName, String username, String email, String password, Hotel hotel) {
        super(firstName, lastName, username, email, password);
        this.hotel = hotel;
    }

    public HotelManager(String firstName, String lastName, String username, String email, String password, boolean isBlocked, Hotel hotel) {
        super(firstName, lastName, username, email, password, isBlocked);
        this.hotel = hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Hotel Manager{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", hotel='" + hotel + '\'' +
                '}';
    }
}
