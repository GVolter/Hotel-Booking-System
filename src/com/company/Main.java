package com.company;

import com.company.model.room.*;
import com.company.model.hotel.*;
import com.company.model.user.*;
import com.company.services.*;

import java.text.ParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParseException {
        LoginService loginService = LoginService.getInstance();
        HotelService hotelService = HotelService.getInstance();
        CustomerService customerService = CustomerService.getInstance();
        HotelManagerService hotelManagerService = HotelManagerService.getInstance();
        RoomService roomService = RoomService.getInstance();

        List<StandardRoom> standardRooms = roomService.readStandardRooms();
        List<Suite> suites = roomService.readSuites();
        List<Room> rooms = new ArrayList<>();
        rooms.addAll(standardRooms);
        rooms.addAll(suites);
        roomService.setRooms(rooms);

        List<Hotel> hotels = hotelService.readHotels();
        HotelComparator hotelComparator = new HotelComparator();
        Collections.sort(hotels, hotelComparator);
        hotelService.setHotels(hotels);

        List<User> users = new ArrayList<>();
        List<Customer> customers = customerService.readCustomers();
        List<HotelManager> hotelManagers = hotelManagerService.readHotelManagers();
        List<Admin> admins = new ArrayList<>();


        Admin A = new Admin("Admin", "admin", "admin", "admin@email.com", "passadmin");

        admins.add(A);

        loginService.setAdmins(admins);

        loginService.setCustomers(customers);

        loginService.setHotelManagers(hotelManagers);

        users.addAll(customers);
        users.addAll(hotelManagers);
        users.addAll(admins);
        loginService.setUsers(users);

        loginService.displayMenu();
    }
}
