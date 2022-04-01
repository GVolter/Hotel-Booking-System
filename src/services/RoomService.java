package services;

import model.hotel.Hotel;
import model.room.OneRoom;
import model.room.OneRoomType;
import model.room.Room;
import model.room.Suite;

import java.util.Scanner;

public class RoomService {
    private static RoomService instance = new RoomService();

    private RoomService() {}

    public static RoomService getInstance() {
        return instance;
    }

    Scanner scanner = new Scanner(System.in);

    public Room createRoom(Hotel hotel) {
        System.out.println("Enter Room Information: ");
        System.out.println("Enter Number of Room:");
        int noOfRoom = scanner.nextInt();
        System.out.println("Enter number of beds: ");
        int noOfBeds = scanner.nextInt();
        System.out.println("Enter floor number: ");
        int roomFloor = scanner.nextInt();
        System.out.println("Enter price: ");
        double price = scanner.nextDouble();
        OneRoomType type = null;
        System.out.println("Enter type for room of kind OneRoom or skip if it is a Suite (1, 2, 3, 4):");
        System.out.println("1. Single");
        System.out.println("2. Double");
        System.out.println("3. Triple");
        System.out.println("4. Skip");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                type = OneRoomType.SINGLE;
                break;
            case 2:
                type = OneRoomType.DOUBLE;
                break;
            case 3:
                type = OneRoomType.TRIPLE;
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid option");
        }
        if (type == null) {
            System.out.println("Enter number of rooms for Suite:");
            int noOfRooms = scanner.nextInt();
            System.out.println("Room Added");
            return new Suite(noOfRoom, noOfBeds, roomFloor, price, noOfRooms);
        }
        else {
            System.out.println("Room Added");
            return new OneRoom(noOfRoom, noOfBeds, roomFloor, price, type);
        }
    }

    public void removeRoom(Hotel hotel, int noOfRoom) {
        if(hotel.getRooms() != null) {
            Room rr;
            for (Room r : hotel.getRooms()) {
                if (r.getNoOfRoom() == noOfRoom) {
                    rr = r;
                    hotel.getRooms().remove(rr);
                    System.out.println("Room deleted!");
                    return;
                }
            }
            System.out.println("Room not found!");
        }
        System.out.println("There are no rooms to be removed");
    }
}
