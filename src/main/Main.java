package main;

import model.app.App;
import model.reservation.Reservation;
import model.room.*;
import model.hotel.*;
import model.user.*;
import services.LoginService;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        App app = App.getInstance();
        LoginService loginService = LoginService.getInstance();

        List<Hotel> hotelsApp = new ArrayList<>();
        List<User> usersApp = new ArrayList<>();
        List<Customer> customersApp = new ArrayList<>();
        List<HotelManager> hotelManagersApp = new ArrayList<>();
        List<Admin> adminsApp = new ArrayList<>();

        OneRoom r1 = new OneRoom(2, 2, 1, 150, OneRoomType.DOUBLE);
        Suite r2 = new Suite(1, 5, 1, 250, 2);

        TreeSet<Room> rooms = new TreeSet<>(new RoomComparator());
        rooms.add(r1);
        rooms.add(r2);

        Hotel h1 = new Hotel("Malibu");
        Hotel h2 = new Hotel("Maldive", rooms);

        hotelsApp.add(h1);
        hotelsApp.add(h2);
        HotelComparator hotelComparator = new HotelComparator();
        Collections.sort(hotelsApp, hotelComparator);
        app.setHotels(hotelsApp);

        Admin A = new Admin("Admin", "admin", "admin", "admin@email.com", "passadmin");

        adminsApp.add(A);
        app.setAdmins(adminsApp);

        Customer c1 = new Customer("Alexandra", "Stanciu", "alexS", "alexandraSt@email.com", "passC1");
        Customer c2 = new Customer("Andreea", "Stanciu", "gvolter", "a_stanciu01@emai.com", "passC");

        customersApp.add(c1);
        customersApp.add(c2);
        app.setCustomers(customersApp);

        HotelManager hm1 = new HotelManager("Hotel", "Manager", "hotelM1", "h1@email.com", "passH1", h1);
        HotelManager hm2 = new HotelManager("Manager", "Hotel", "hotelM2", "h2@email.com", "passH2", h2);

        hotelManagersApp.add(hm1);
        hotelManagersApp.add(hm2);
        app.setHotelManagers(hotelManagersApp);

        usersApp.addAll(customersApp);
        usersApp.addAll(hotelManagersApp);
        usersApp.addAll(adminsApp);
        app.setUsers(usersApp);

        loginService.displayMenu(app);
    }
}
