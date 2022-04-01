package services;

import model.app.App;
import model.hotel.Hotel;
import model.reservation.Reservation;
import model.user.Admin;
import model.user.Customer;
import model.user.HotelManager;
import model.user.User;

import java.util.Scanner;

public class AdminService {
    private static AdminService instance = new AdminService();

    private AdminService() {}

    public static AdminService getInstance() {
        return instance;
    }

    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu(App app) {
        System.out.println("Logged in as Admin");
        for (; ; ) {
            System.out.println("1.List of all Users.");
            System.out.println("2.List of Customers.");
            System.out.println("3.List of Hotel Managers");
            System.out.println("4.List of Hotels");
            System.out.println("5.List of Reservations");
            System.out.println("6.BLocked Users.");
            System.out.println("7.Log Out");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    for (User user: app.getUsers()) {
                        System.out.println(user);
                    }
                    break;
                case 2:
                    for (Customer customer: app.getCustomers()) {
                        System.out.println(customer);
                    }
                    break;
                case 3:
                    for (HotelManager hotelManager: app.getHotelManagers()) {
                        System.out.println(hotelManager);
                    }
                    break;
                case 4:
                    for (Hotel hotel: app.getHotels())
                        System.out.println(hotel);
                    break;
                case 5:
                    for (Reservation reservation: app.getReservations()) {
                        System.out.println(reservation);
                    }
                    break;
                case 6:
                    for (User user: app.getUsers()) {
                        if (user.getAccess().equals("blocked"))
                            unblockUser(user);
                    }
                    break;
                case 7:
                    LoginService loginService = LoginService.getInstance();
                    loginService.displayMenu(app);
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
            user.setAccess("unblocked");
        else if (choice.equals("N"))
            return;
        else
            System.out.println("Invalid option");
    }
}
