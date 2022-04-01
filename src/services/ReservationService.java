package services;

import model.app.App;
import model.hotel.Hotel;
import model.reservation.Reservation;
import model.room.Room;
import model.user.Customer;

import java.util.*;

public class ReservationService {
    private static ReservationService instance = new ReservationService();

    private ReservationService() {}

    public static ReservationService getInstance() {
        return instance;
    }

    private final Scanner scanner  = new Scanner(System.in);

    CustomerService customerService = CustomerService.getInstance();

    public void reserve(App app, Customer customer, Hotel hotel) {
        System.out.println("Show Rooms");
        for (Room room : hotel.getRooms()) {
            if (room.getStatus().equals("Available"))
                System.out.println(room);
        }
        System.out.println("Pick duration (number of days)");
        int days = scanner.nextInt();
        Map<Room, Integer> roomsToBook = new HashMap<>();
        for(;;) {
            System.out.println("Enter room you want to book");
            int roomNo = scanner.nextInt();
            for (Room r : hotel.getRooms()) {
                if (r.getNoOfRoom() == roomNo && r.getStatus().equals("Available")) {
                    roomsToBook.put(r, days);
                    r.setStatus("Booked");
                    System.out.println("Room booked " + roomNo);
                    break;
                }
                else
                    System.out.println("Room can't be booked");
            }
            System.out.println("Add another room? (Y/N)");
            String input = scanner.next();
            if(input.equals("N"))
                break;
        }
        Reservation reservation = new Reservation(hotel, customer, roomsToBook);
        double price = calculateReservationPrice(reservation);
        for(;;) {
            System.out.println("Price for reservation will be " + price);
            System.out.println("Create reservation? (Y/N)");
            String option = scanner.next();
            if (option.equals("Y")) {
                app.getReservations().add(reservation);
                customerService.displayMenu(app, customer);
            } else if (option.equals("N")) {
                System.out.println("Reservation canceled");
                for (Map.Entry<Room, Integer> entry : reservation.getBookedRooms().entrySet()) {
                    entry.getKey().setStatus("Available");
                }
                customerService.displayMenu(app, customer);
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    public double calculateReservationPrice(Reservation reservation) {
        double sum = 0;
        for (Map.Entry<Room, Integer> entry : reservation.getBookedRooms().entrySet()) {
            sum += entry.getKey().getPrice() * entry.getValue();
        }
        return sum;
    }
}
