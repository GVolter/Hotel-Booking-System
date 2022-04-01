package services;

import model.app.App;
import model.room.Room;
import model.user.HotelManager;

import java.util.Scanner;

public class HotelManagerService {
    private static HotelManagerService instance = new HotelManagerService();

    private HotelManagerService() {}

    public static HotelManagerService getInstance() {
        return instance;
    }

    private final Scanner scanner = new Scanner(System.in);
    RoomService roomService = RoomService.getInstance();

    public void displayMenu(App app, HotelManager hotelManager) {
        System.out.println("Logged in as Hotel Manager");
        for(;;) {
            System.out.println("1. Show rooms");
            System.out.println("2. Add room(s) for reservation");
            System.out.println("3. Remove room(s) from reservation");
            System.out.println("4. Log out");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println(hotelManager.getHotel().getRooms());
                    break;
                case 2:
                    Room room = roomService.createRoom(hotelManager.getHotel());
                    HotelService hotelService = HotelService.getInstance();
                    hotelService.addRoom(room, hotelManager.getHotel());
                    break;
                case 3:
                    System.out.println("Enter number of the room: ");
                    int roomNo = scanner.nextInt();
                    roomService.removeRoom(hotelManager.getHotel(), roomNo);
                    break;
                case 4:
                    LoginService loginService = LoginService.getInstance();
                    loginService.displayMenu(app);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
