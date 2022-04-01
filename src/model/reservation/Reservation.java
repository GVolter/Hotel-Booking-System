package model.reservation;

import model.hotel.*;
import model.room.*;
import model.user.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class Reservation {
    private static int ID = 0;
    private int id;
    private Hotel hotel;
    private Customer customer;
    private Map<Room, Integer> bookedRooms;

    public Reservation(Hotel hotel, Customer customer, Map<Room, Integer> bookedRooms) {
        ID++;
        this.id = ID;
        this.hotel = hotel;
        this.customer = customer;
        this.bookedRooms = bookedRooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<Room, Integer> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(Map<Room, Integer> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", customer=" + customer +
                ", bookedRooms=" + bookedRooms +
                '}';
    }
}
