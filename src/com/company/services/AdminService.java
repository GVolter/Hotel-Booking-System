package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.reservation.Reservation;
import com.company.model.user.Customer;
import com.company.model.user.HotelManager;
import com.company.model.user.User;

import java.util.Scanner;

public class AdminService {
    private static AdminService instance = new AdminService();

    private AdminService() {}

    public static AdminService getInstance() {
        return instance;
    }

    private final Scanner scanner = new Scanner(System.in);

    LoginService loginService = LoginService.getInstance();

    public void displayMenu() {
        System.out.println("Logged in as Admin");
        int choice;
        while(true) {
            System.out.println("1.List of all Users.");
            System.out.println("2.List of Customers.");
            System.out.println("3.List of Hotel Managers");
            System.out.println("4.List of Hotels");
            System.out.println("5.List of Reservations");
            System.out.println("6.BLocked Users.");
            System.out.println("7.Log Out");
            try {
                choice = scanner.nextInt();
            }
            catch(Exception e) {
                System.out.println("Invalid option");
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    for (User user: loginService.getUsers()) {
                        System.out.println(user);
                    }
                    break;
                case 2:
                    for (Customer customer: loginService.getCustomers()) {
                        System.out.println(customer);
                    }
                    break;
                case 3:
                    for (HotelManager hotelManager: loginService.getHotelManagers()) {
                        System.out.println(hotelManager);
                    }
                    break;
                case 4:
                    HotelService hotelService = HotelService.getInstance();
                    for (Hotel hotel: hotelService.getHotels())
                        System.out.println(hotel);
                    break;
                case 5:
                    ReservationService reservationService = ReservationService.getInstance();
                    for (Reservation reservation: reservationService.getReservations()) {
                        System.out.println(reservation);
                    }
                    break;
                case 6:
                    for (User user: loginService.getUsers()) {
                        if (user.isBlocked())
                            unblockUser(user);
                    }
                    break;
                case 7:
                    loginService.displayMenu();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void unblockUser(User user) {
        System.out.printf("Do you want to unblock the user %s? Y/N\n", user.getUsername());
        String choice = scanner.next();
        if (choice.equals("Y"))
            user.setBlocked(false);
        else if (choice.equals("N"))
            return;
        else
            System.out.println("Invalid option");
    }
}
