package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.room.StandardRoom;
import com.company.model.room.StandardRoomType;
import com.company.model.room.Room;
import com.company.model.room.Suite;
import com.company.model.user.Customer;
import com.company.model.user.HotelManager;
import com.company.repository.CustomerRepository;
import com.company.repository.HMRepository;
import com.company.repository.SRRepository;
import com.company.repository.SuiteRepository;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomService {
    private static RoomService instance = new RoomService();

    private RoomService() {}

    public static RoomService getInstance() {
        return instance;
    }

    List<Room> rooms = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    AuditService auditService = AuditService.getInstance();

    private final Path SROOM_PATH = Path.of("resources/standardRooms.csv");

    private CSVReader<StandardRoom> standardRoomCSVReader = new CSVReader<>();
    private CSVWriter<StandardRoom> standardRoomCSVWriter = new CSVWriter<>(SROOM_PATH);

    private final Path SUITE_PATH = Path.of("resources/suites.csv");

    private CSVReader<Suite> suiteCSVReader = new CSVReader<>();
    private CSVWriter<Suite> suiteCSVWriter = new CSVWriter<>(SUITE_PATH);

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Room createRoom(int roomNo) {
        int option;
        SRRepository srRepository = SRRepository.getInstance();
        SuiteRepository suiteRepository = SuiteRepository.getInstance();
        while (true) {
            int roomFloor;
            double price;
            StandardRoomType type = null;
            System.out.println("Enter Room Information: ");
            System.out.println("Enter floor number: ");
            while (true)
            {
                try {
                    roomFloor = scanner.nextInt();
                    break;
                }
                catch(Exception e) {
                    System.out.println("Invalid option");
                    scanner.nextLine();
                }
            }
            System.out.println("Enter price: ");
            while (true)
            {
                try {
                    price = scanner.nextDouble();
                    break;
                }
                catch(Exception e) {
                    System.out.println("Invalid option");
                    scanner.nextLine();
                }
            }
            System.out.println("Enter type for room of kind StandardRoom or skip if it is a Suite (1, 2, 3, 4):");
            System.out.println("1. Single");
            System.out.println("2. Double");
            System.out.println("3. Triple");
            System.out.println("4. Skip");
            while (true)
            {
                try {
                    option = scanner.nextInt();
                    break;
                }
                catch(Exception e) {
                    System.out.println("Invalid option");
                    scanner.nextLine();
                }
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
                int noOfRooms;
                while (true)
                {
                    try {
                        noOfRooms = scanner.nextInt();
                        break;
                    }
                    catch(Exception e) {
                        System.out.println("Invalid option");
                        scanner.nextLine();
                    }
                }
                System.out.println("Room Added");
                Suite suite = new Suite(roomNo, roomFloor, price, noOfRooms);
                write(suite);
                auditService.logMessage("Room written to CSV file");
                suiteRepository.insertSuite(suite);
                return suite;
            } else {
                System.out.println("Room Added");
                StandardRoom standardRoom = new StandardRoom(roomNo, roomFloor, price, type);
                write(standardRoom);
                auditService.logMessage("Room written to CSV file");
                srRepository.insertStandardRoom(standardRoom);
                return standardRoom;
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
                    rr.setAvailable(true);
                    if(rr instanceof StandardRoom) {
                        SRRepository srRepository = SRRepository.getInstance();
                        srRepository.updateStandardRoom(String.valueOf(rr.isAvailable()), rr.getId());
                    }
                    else if(rr instanceof Suite) {
                        SuiteRepository suiteRepository = SuiteRepository.getInstance();
                        suiteRepository.updateSuite(String.valueOf(rr.isAvailable()), rr.getId());
                    }
                    auditService.logMessage("Room now availabe: " + rr.getRoomNo());
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
                    if(rr instanceof StandardRoom) {
                        SRRepository srRepository = SRRepository.getInstance();
                        srRepository.updateStandardRoom(String.valueOf(rr.isAvailable()), rr.getId());
                    }
                    else if(rr instanceof Suite) {
                        SuiteRepository suiteRepository = SuiteRepository.getInstance();
                        suiteRepository.updateSuite(String.valueOf(rr.isAvailable()), rr.getId());
                    }
                    auditService.logMessage("Room not availabe now: " + rr.getRoomNo());
                    System.out.println("Room is no more available");
                    return;
                }
            }
            System.out.println("Room not found!");
        }
        System.out.println("There are no rooms");
    }

    public List<StandardRoom> readStandardRooms() {
        return standardRoomCSVReader.read(SROOM_PATH);
    }

    public void write(StandardRoom standardRoom) {
        standardRoomCSVWriter.write(standardRoom);
    }

    public List<Suite> readSuites() {
        return suiteCSVReader.read(SUITE_PATH);
    }

    public void write(Suite suite) {
        suiteCSVWriter.write(suite);
    }
}
