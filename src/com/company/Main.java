package com.company;

import com.company.model.room.*;
import com.company.model.hotel.*;
import com.company.model.user.*;
import com.company.services.HotelService;
import com.company.services.LoginService;

import java.text.ParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParseException {
        LoginService loginService = LoginService.getInstance();
        HotelService hotelService = HotelService.getInstance();

        List<Hotel> hotels = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<HotelManager> hotelManagers = new ArrayList<>();
        List<Admin> admins = new ArrayList<>();

        StandardRoom r1 = new StandardRoom(2, 1, 150, StandardRoomType.DOUBLE);
        Suite r2 = new Suite(1, 1, 250, 2);

        TreeSet<Room> rooms = new TreeSet<>(new RoomComparator());
        rooms.add(r1);
        rooms.add(r2);

        Hotel h1 = new Hotel("Malibu", rooms);
        Hotel h2 = new Hotel("Maldive", rooms);

        hotels.add(h1);
        hotels.add(h2);
        HotelComparator hotelComparator = new HotelComparator();
        Collections.sort(hotels, hotelComparator);
        hotelService.setHotels(hotels);

        Admin A = new Admin("Admin", "admin", "admin", "admin@email.com", "passadmin");

        admins.add(A);
        loginService.setAdmins(admins);

        Customer c1 = new Customer("Alexandra", "Stanciu", "alexS", "alexandraSt@email.com", "passC1");
        Customer c2 = new Customer("Andreea", "Stanciu", "gvolter", "a_stanciu01@emai.com", "passC");

        customers.add(c1);
        customers.add(c2);
        loginService.setCustomers(customers);

        HotelManager hm1 = new HotelManager("Hotel", "Manager", "hotelM1", "h1@email.com", "passH1", h1);
        HotelManager hm2 = new HotelManager("Manager", "Hotel", "hotelM2", "h2@email.com", "passH2", h2);

        hotelManagers.add(hm1);
        hotelManagers.add(hm2);
        loginService.setHotelManagers(hotelManagers);


        users.addAll(customers);
        users.addAll(hotelManagers);
        users.addAll(admins);
        loginService.setUsers(users);

        loginService.displayMenu();
    }
}
