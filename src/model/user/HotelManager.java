package model.user;

import model.hotel.Hotel;

public class HotelManager extends User{
    private Hotel hotel;

    public HotelManager(String firstName, String lastName, String username, String email, String password, Hotel hotel) {
        super(firstName, lastName, username, email, password);
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
        return "HotelManager{" +
                "hotel=" + hotel +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
