package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.room.Room;
import com.company.model.room.RoomComparator;
import com.company.model.user.Admin;
import com.company.model.user.Customer;
import com.company.model.user.HotelManager;
import com.company.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class LoginService {
    private static LoginService instance = new LoginService();

    private LoginService(){}

    public static LoginService getInstance() {
        return instance;
    }

    AuditService auditService = AuditService.getInstance();

    private final Scanner scanner  = new Scanner(System.in);

    private List<User> users = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<HotelManager> hotelManagers = new ArrayList<>();
    private List<Admin> admins = new ArrayList<>();
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

    public void displayMenu(){
        int option;
        while(true) {
            System.out.println("Select Option:\n1. Login\n2. Signup\n3. Exit");
            try {
                option = scanner.nextInt();
            }
            catch(Exception e) {
                System.out.println("Invalid option");
                scanner.nextLine();
                continue;
            }
            switch (option){
                case 1:
                    int tries = 0;
                    while(true) {
                        if (tries <= 2) {
                            System.out.println("Username: ");
                            String username = scanner.next();
                            System.out.println("Password: ");
                            String password = scanner.next();
                            User user = login(username);
                            if (user == null) {
                                while (true) {
                                    System.out.println("The username and password combination entered doesn't match.");
                                    System.out.println("1. Go back\n2. Try more");
                                    int input;
                                    try {
                                        input = scanner.nextInt();
                                        scanner.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("Invalid option");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    if (input == 1)
                                        displayMenu();
                                    else if(input == 2)
                                        break;
                                }
                            }
                            else if(user.isBlocked())
                            {
                                System.out.println("User blocked");
                                break;
                            }
                            else {
                                if (user.getPassword().equals(password)) {
                                    auditService.logMessage("Logged in user" + user.getUsername());
                                    if (user instanceof Admin) {
                                        AdminService adminService = AdminService.getInstance();
                                        adminService.displayMenu();
                                        break;
                                    }
                                    else if (user instanceof HotelManager) {
                                        HotelManagerService hotelManagerService = HotelManagerService.getInstance();
                                        hotelManagerService.displayMenu((HotelManager) user);
                                        break;
                                    }
                                    else if (user instanceof Customer) {
                                        CustomerService customerService = CustomerService.getInstance();
                                        customerService.displayMenu((Customer) user);
                                        break;
                                    }
                                }
                                else if (tries == 2) {
                                    System.out.println("Too many attempts. User got blocked.");
                                    user.setBlocked(true);
                                    break;
                                }
                                else {
                                    System.out.println("Wrong password");
                                    tries++;
                                    System.out.printf("%d more attempt(s)\n", 3-tries);
                                    System.out.println("1. Go back\n2. Try more");
                                    int input;
                                    while (true)
                                    {
                                        try {
                                            input = scanner.nextInt();
                                            break;
                                        }
                                        catch(Exception e) {
                                            System.out.println("Invalid option");
                                            scanner.nextLine();
                                        }
                                    }
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
                    try {
                        option2 = scanner.nextInt();
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid option");
                        scanner.nextLine();
                        continue;
                    }
                    if (option2 >= 1 && option2 <= 2) {
                        signUp(option2);
                    }
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private User login(String username){
        for(User user: getUsers()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void signUp(int type) {
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

        boolean taken = false;

        switch (type) {
            case 1:
                Customer customer = new Customer(firstName, lastName, username, email, password);
                for(User user: getUsers())
                {
                    if(user.getUsername().equals(customer.getUsername()) || user.getEmail().equals(customer.getEmail()))
                    {
                        System.out.println("Username or email already taken");
                        taken = true;
                        break;
                    }
                    else
                    {
                        continue;
                    }

                }
                if(!taken) {
                    System.out.println("Creating user...Done");
                    auditService.logMessage("Signed up user" + customer.getUsername());
                    CustomerService customerService = CustomerService.getInstance();
                    customerService.write(customer);
                    getCustomers().add(customer);
                    getUsers().add(customer);
                }
                break;
            case 2:
                RoomService roomService = RoomService.getInstance();
                TreeSet<Room> rooms = new TreeSet<>(new RoomComparator());
                System.out.println("Enter your hotel information:");
                System.out.println("Name");
                String name = scanner.next();
                System.out.println("How many rooms do you want to add?");
                int noRooms;
                while (true)
                {
                    try {
                        noRooms = scanner.nextInt();
                        break;
                    }
                    catch(Exception e) {
                        System.out.println("Invalid option");
                        scanner.nextLine();
                    }
                }
                for(int i = 1; i <= noRooms; i++)
                {
                    Room room = roomService.createRoom(i);
                    rooms.add(room);
                }
                Hotel hotel = new Hotel(name, rooms);
                HotelManager hotelManager = new HotelManager(firstName, lastName, username, email, password, hotel);
                for(User user: getUsers())
                {
                    if(user.getUsername().equals(hotelManager.getUsername()) || user.getEmail().equals(hotelManager.getEmail()))
                    {
                        System.out.println("Username or email already taken");
                        taken = true;
                        break;
                    }
                    else
                    {
                        continue;
                    }

                }
                if(!taken) {
                    HotelService hotelService = HotelService.getInstance();
                    HotelManagerService hotelManagerService = HotelManagerService.getInstance();
                    hotelService.getHotels().add(hotel);
                    hotelService.write(hotel);
                    auditService.logMessage("Hotel added");
                    getHotelManagers().add(hotelManager);
                    hotelManagerService.write(hotelManager);
                    getUsers().add(hotelManager);
                    auditService.logMessage("HotelManager Signed up");
                    System.out.println("Hotel added to reservation system");

                }
                break;
            default:
                System.out.println("Invalid option");
        }
    }
}
