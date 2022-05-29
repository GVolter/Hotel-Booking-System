package com.company.services;
import com.company.model.hotel.Hotel;
import com.company.model.room.Room;
import com.company.model.user.HotelManager;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class HotelManagerService {
    private static HotelManagerService instance = new HotelManagerService();

    private HotelManagerService() {}

    public static HotelManagerService getInstance() {
        return instance;
    }

    private final Path PATH = Path.of("resources/hotelManagers.csv");

    private CSVReader<HotelManager> hotelManagerCSVReader = new CSVReader<>();
    private CSVWriter<HotelManager> hotelManagerCSVWriter = new CSVWriter<>(PATH);

    private final Scanner scanner = new Scanner(System.in);
    RoomService roomService = RoomService.getInstance();

    public void displayMenu(HotelManager hotelManager) {
        System.out.println("Logged in as Hotel Manager");
        int option;
        while(true) {
            System.out.println("1. Show rooms");
            System.out.println("2. Add room(s) for reservation");
            System.out.println("3. Remove room(s) from reservation");
            System.out.println("4. Log out");
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
                    System.out.println(hotelManager.getHotel().getRooms());
                    break;
                case 2:
                    System.out.println("Enter number of the room: ");
                    int roomNo = scanner.nextInt();
                    roomService.makeRoomAvailable(hotelManager.getHotel(), roomNo);
                    break;
                case 3:
                    System.out.println("Enter number of the room: ");
                    int noOfRoom = scanner.nextInt();
                    roomService.makeRoomUnavailable(hotelManager.getHotel(), noOfRoom);
                    break;
                case 4:
                    LoginService loginService = LoginService.getInstance();
                    loginService.displayMenu();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public List<HotelManager> readHotelManagers() {
        return hotelManagerCSVReader.read(PATH);
    }

    public void write(HotelManager hotelManager) {
        hotelManagerCSVWriter.write(hotelManager);
    }

}
