package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.room.StandardRoom;
import com.company.model.room.StandardRoomType;
import com.company.model.room.Room;
import com.company.model.room.Suite;

import java.util.Scanner;

public class RoomService {
    private static RoomService instance = new RoomService();

    private RoomService() {}

    public static RoomService getInstance() {
        return instance;
    }

    Scanner scanner = new Scanner(System.in);

    public Room createRoom(int roomNo) {
        int option;
        while (true) {
            int roomFloor;
            double price;
            StandardRoomType type = null;
            try {
                System.out.println("Enter Room Information: ");
                System.out.println("Enter floor number: ");
                roomFloor = scanner.nextInt();
                System.out.println("Enter price: ");
                price = scanner.nextDouble();
                System.out.println("Enter type for room of kind StandardRoom or skip if it is a Suite (1, 2, 3, 4):");
                System.out.println("1. Single");
                System.out.println("2. Double");
                System.out.println("3. Triple");
                System.out.println("4. Skip");
                option = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid option");
                scanner.nextLine();
                continue;
            }
            switch (option) {
                case 1:
                    type = StandardRoomType.SINGLE;
                    break;
                case 2:
                    type = StandardRoomType.DOUBLE;
                    break;
                case 3:
                    type = StandardRoomType.TRIPLE;
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
                return new Suite(roomNo, roomFloor, price, noOfRooms);
            } else {
                System.out.println("Room Added");
                return new StandardRoom(roomNo, roomFloor, price, type);
            }
        }
    }

    public void makeRoomAvailable(Hotel hotel, int roomNo)
    {
        if(hotel.getRooms() != null) {
            Room rr;
            for (Room r : hotel.getRooms()) {
                if (r.getRoomNo() == roomNo && !r.isAvailable()) {
                    rr = r;
                    rr.setAvailable(false);
                    System.out.println("Room is now available");
                    return;
                }
            }
            System.out.println("Room not found!");
        }
        System.out.println("There are no rooms");
    }

    public void makeRoomUnavailable(Hotel hotel, int roomNo) {
        if(hotel.getRooms() != null) {
            Room rr;
            for (Room r : hotel.getRooms()) {
                if (r.getRoomNo() == roomNo && r.isAvailable()) {
                    rr = r;
                    rr.setAvailable(false);
                    System.out.println("Room is no more available");
                    return;
                }
            }
            System.out.println("Room not found!");
        }
        System.out.println("There are no rooms");
    }
}
