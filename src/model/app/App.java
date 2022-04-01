package model.app;

import model.hotel.Hotel;
import model.reservation.Reservation;
import model.user.Admin;
import model.user.Customer;
import model.user.HotelManager;
import model.user.User;

import java.util.ArrayList;
import java.util.List;

public class App {
    private static App instance = new App();

    private List<User> users = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<HotelManager> hotelManagers = new ArrayList<>();
    private List<Admin> admins = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Hotel> hotels = new ArrayList<>();

    private App(){}

    public static App getInstance() {
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<HotelManager> getHotelManagers() {
        return hotelManagers;
    }

    public void setHotelManagers(List<HotelManager> hotelManagers) {
        this.hotelManagers = hotelManagers;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
