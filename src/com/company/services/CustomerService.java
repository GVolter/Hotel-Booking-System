package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.user.Customer;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class CustomerService {
    private static CustomerService instance = new CustomerService();

    private CustomerService() {}

    public static CustomerService getInstance() {
        return instance;
    }

    private final Path PATH = Path.of("resources/customers.csv");

    private CSVReader<Customer> customerCSVReader = new CSVReader<>();
    private CSVWriter<Customer> customerCSVWriter = new CSVWriter<>(PATH);

    private final Scanner scanner  = new Scanner(System.in);
    LoginService loginService = LoginService.getInstance();
    HotelService hotelService = HotelService.getInstance();

    public void displayMenu(Customer customer) {
        System.out.println("Logged in as Customer");
        int option;
        while(true) {
            System.out.println("1.View Hotels");
            System.out.println("2.Reserve");
            System.out.println("3.Log Out");
            System.out.println("4.Delete account");
            List<Hotel> hotels  = hotelService.getHotels();
            try {
                option = scanner.nextInt();
            }
            catch(Exception e) {
                System.out.println("Invalid option");
                scanner.nextLine();
                continue;
            }
            switch (option) {
                case 1:
                    for (Hotel hotel : hotels)
                        System.out.println(hotel);
                    break;
                case 2:
                    while (true) {
                        System.out.println("Choose Hotel");
                        String chosenHotel = scanner.next();
                        for (Hotel hotel : hotelService.getHotels()) {
                            if (chosenHotel.equals(hotel.getName())) {
                                ReservationService reservationService = ReservationService.getInstance();
                                reservationService.reserve(customer, hotel);
                                break;
                            }
                        }
                        System.out.println("No hotel was found with this name");
                    }
                case 3:
                    loginService.displayMenu();
                    break;
                case 4:
                    System.out.println("Are you sure you want to delete your account? (Y/N)");
                    String input = scanner.next();
                    if (input.equals("Y")) {
                        loginService.getUsers().remove(customer);
                        loginService.getCustomers().remove(customer);
                        System.out.println("Account deleted");
                        loginService.displayMenu();
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

    public List<Customer> readCustomers() {
        return customerCSVReader.read(PATH);
    }

    public void write(Customer customer) {
        customerCSVWriter.write(customer);
    }
}
