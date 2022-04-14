package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.reservation.Reservation;
import com.company.model.room.Room;
import com.company.model.user.Customer;

import java.util.*;

public class ReservationService {
    private static ReservationService instance = new ReservationService();

    private ReservationService() {}

    public static ReservationService getInstance() {
        return instance;
    }

    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    private final Scanner scanner  = new Scanner(System.in);

    CustomerService customerService = CustomerService.getInstance();

    public void reserve(Customer customer, Hotel hotel) {
        System.out.println("Show Rooms");
        for (Room room : hotel.getRooms()) {
            if (room.isAvailable())
                System.out.println(room);
        }
        System.out.println("Pick duration (number of days)");
        int days = scanner.nextInt();
        Map<Room, Integer> roomsToBook = new HashMap<>();
        for(;;) {
            System.out.println("Enter room you want to book");
            int roomNo = scanner.nextInt();
            for (Room r : hotel.getRooms()) {
                if (r.getRoomNo() == roomNo && r.isAvailable()) {
                    roomsToBook.put(r, days);
                    r.setAvailable(false);
                    System.out.println("Room booked " + roomNo);
                    break;
                }
                else {
                    System.out.println("Room can't be booked");
                    break;
                }
            }
            System.out.println("Add another room? (Y/N)");
            String input = scanner.next();
            if(input.equals("N"))
                break;
        }
        Reservation reservation = new Reservation(hotel, customer, roomsToBook);
        double price = calculateReservationPrice(reservation);
        while(true) {
            System.out.println("Price for reservation will be " + price);
            System.out.println("Create reservation? (Y/N)");
            String option = scanner.next();
            if (option.equals("Y")) {
                getReservations().add(reservation);
                customerService.displayMenu(customer);
            } else if (option.equals("N")) {
                System.out.println("Reservation canceled");
                for (Map.Entry<Room, Integer> entry : reservation.getBookedRooms().entrySet()) {
                    entry.getKey().setAvailable(true);
                }
                customerService.displayMenu(customer);
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
