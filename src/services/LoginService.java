package services;

import model.app.App;
import model.hotel.Hotel;
import model.user.Admin;
import model.user.Customer;
import model.user.HotelManager;
import model.user.User;

import java.util.Scanner;

public class LoginService {
    private static LoginService instance = new LoginService();

    private LoginService(){}

    public static LoginService getInstance() {
        return instance;
    }

    private final Scanner scanner  = new Scanner(System.in);

    public void displayMenu(App app){
        int option;
        for(;;) {
            System.out.println("Select Option:\n1. Login\n2. Signup\n3. Exit");
            option = scanner.nextInt();

            switch (option){
                case 1:
                    int tries = 0;
                    for(;;) {
                        if (tries <= 2) {
                            System.out.println("Username: ");
                            String username = scanner.next();
                            System.out.println("Password: ");
                            String password = scanner.next();
                            User user = login(username, app);
                            if (user == null) {
                                System.out.println("The username and password combination entered doesn't match.");
                                System.out.println("1. Go back\n2. Try more");
                                int input = scanner.nextInt();
                                if(input == 1)
                                    break;
                            }
                            else if(user.getAccess().equals("blocked"))
                            {
                                System.out.println("User blocked");
                                break;
                            }
                            else {
                                if (user.getPassword().equals(password)) {
                                    if (user instanceof Admin) {
                                        AdminService adminService = AdminService.getInstance();
                                        adminService.displayMenu(app);
                                        break;
                                    }
                                    else if (user instanceof HotelManager) {
                                        HotelManagerService hotelManagerService = HotelManagerService.getInstance();
                                        hotelManagerService.displayMenu(app, (HotelManager) user);
                                        break;
                                    }
                                    else if (user instanceof Customer) {
                                        CustomerService customerService = CustomerService.getInstance();
                                        customerService.displayMenu(app, (Customer) user);
                                        break;
                                    }
                                }
                                else if (tries == 2) {
                                    System.out.println("Too many attempts. User got blocked.");
                                    user.setAccess("blocked");
                                    break;
                                }
                                else {
                                    System.out.println("Wrong password");
                                    tries++;
                                    System.out.printf("%d more attempt(s)\n", 3-tries);
                                    System.out.println("1. Go back\n2. Try more");
                                    int input = scanner.nextInt();
                                    if(input == 1)
                                        break;
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    int option2;
                    System.out.println("1. Sign up as a customer");
                    System.out.println("2. Sign up as a hotel manager");
                    System.out.println("3. Back");
                    option2 = scanner.nextInt();
                    if (option2 >= 1 && option2 <= 2) {
                        signup(option2, app);
                    }
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private User login(String username, App app){
        for(User user: app.getUsers()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void signup(int type, App app) {
        System.out.println("First Name");
        String firstName = scanner.next();
        System.out.println("Last Name");
        String lastName = scanner.next();
        System.out.println("Email");
        String email = scanner.next();
        System.out.println("Username");
        String username = scanner.next();
        System.out.println("Password");
        String password = scanner.next();

        switch (type) {
            case 1:
                System.out.println("Creating user...Done");
                Customer customer = new Customer(firstName, lastName, email, username, password);
                app.getCustomers().add(customer);
                app.getUsers().add(customer);
                break;
            case 2:
                System.out.println("Enter your hotel information:");
                System.out.println("Name");
                String name = scanner.next();
                Hotel hotel = new Hotel(name);
                System.out.println("Hotel added. Login to add rooms for booking");
                app.getHotels().add(hotel);
                HotelManager hotelManager = new HotelManager(firstName, lastName, email, username, password, hotel);
                app.getHotelManagers().add(hotelManager);
                app.getUsers().add(hotelManager);
                break;
            default:
                System.out.println("Invalid option");
        }
    }
}
