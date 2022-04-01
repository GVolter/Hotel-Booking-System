package services;

import model.app.App;
import model.hotel.Hotel;
import model.reservation.Reservation;
import model.user.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerService {
    private static CustomerService instance = new CustomerService();

    private CustomerService() {}

    public static CustomerService getInstance() {
        return instance;
    }

    private final Scanner scanner  = new Scanner(System.in);
    LoginService loginService = LoginService.getInstance();

    public void displayMenu(App app, Customer customer) {
        System.out.println("Logged in as Customer");
        for(;;) {
            System.out.println("1.View Hotels");
            System.out.println("2.Reserve");
            System.out.println("3.Log Out");
            System.out.println("4.Delete account");
            List<Hotel> hotels  = app.getHotels();
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    for (Hotel hotel : hotels)
                        System.out.println(hotel);
                    break;
                case 2:
                    System.out.println("Choose Hotel");
                    String choosenHotel = scanner.next();
                    for(Hotel hotel: app.getHotels()) {
                        if(choosenHotel.equals(hotel.getName())) {
                            ReservationService reservationService = ReservationService.getInstance();
                            reservationService.reserve(app, customer, hotel);
                        }
                    }
                    System.out.println("No hotel was found with this name");
                    break;
                case 3:
                    loginService.displayMenu(app);
                    break;
                case 4:
                    System.out.println("Are you sure you want to delete your account? (Y/N)");
                    String input = scanner.next();
                    if (input.equals("Y")) {
                        app.getUsers().remove(customer);
                        app.getCustomers().remove(customer);
                        System.out.println("Account deleted");
                        loginService.displayMenu(app);
                    }
                    else if (input.equals("N"))
                        System.out.println("Account will not be deleted deleted");
                    else
                        System.out.println("Invalid input");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
