package com.company;

import com.company.config.DatabaseConfiguration;
import com.company.model.room.*;
import com.company.model.hotel.*;
import com.company.model.user.*;
import com.company.repository.*;
import com.company.services.*;

import java.util.*;

public class Main {

    public static void main(String[] args){
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        customerRepository.createTable();
        SuiteRepository suiteRepository = SuiteRepository.getInstance();
        suiteRepository.createTable();
        SRRepository srRepository = SRRepository.getInstance();
        srRepository.createTable();
        HMRepository hmRepository = HMRepository.getInstance();
        hmRepository.createTable();
        HotelRepository hotelRepository = HotelRepository.getInstance();
        hotelRepository.createTable();

        LoginService loginService = LoginService.getInstance();
        HotelService hotelService = HotelService.getInstance();
        CustomerService customerService = CustomerService.getInstance();
        HotelManagerService hotelManagerService = HotelManagerService.getInstance();
        RoomService roomService = RoomService.getInstance();

        List<StandardRoom> standardRooms = srRepository.getStandardRooms();
        if(standardRooms.isEmpty())
        {
            standardRooms = roomService.readStandardRooms();
            for(StandardRoom room: standardRooms)
                srRepository.insertStandardRoom(room);
        }

        List<Suite> suites = suiteRepository.getSuites();
        if(suites.isEmpty())
        {
            suites = roomService.readSuites();
            for(Suite suite: suites)
                suiteRepository.insertSuite(suite);
        }

        List<Room> rooms = new ArrayList<>();
        rooms.addAll(standardRooms);
        rooms.addAll(suites);
        roomService.setRooms(rooms);

        List<Hotel> hotels = hotelRepository.getHotels();
        if(hotels.isEmpty())
        {
            hotels = hotelService.readHotels();
            for(Hotel hotel: hotels)
                hotelRepository.insertHotel(hotel);
        }

        HotelComparator hotelComparator = new HotelComparator();
        hotels.sort(hotelComparator);
        hotelService.setHotels(hotels);

        List<User> users = new ArrayList<>();

        List<Customer> customers = customerRepository.getCustomers();
        if(customers.isEmpty())
        {
            customers = customerService.readCustomers();
            for(Customer customer: customers)
                customerRepository.insertCustomer(customer);
        }

        List<HotelManager> hotelManagers = hmRepository.getHotelManagers();
        if(hotelManagers.isEmpty())
        {
            hotelManagers = hotelManagerService.readHotelManagers();
            for(HotelManager hotelManager: hotelManagers)
                hmRepository.insertHM(hotelManager);
        }

        Admin A = new Admin("Admin", "admin", "admin", "admin@email.com", "passadmin");

        loginService.setCustomers(customers);

        loginService.setHotelManagers(hotelManagers);

        users.addAll(customers);
        users.addAll(hotelManagers);
        users.add(A);
        loginService.setUsers(users);

        DatabaseConfiguration.closeDatabaseConnection();

        loginService.displayMenu();
    }
}
