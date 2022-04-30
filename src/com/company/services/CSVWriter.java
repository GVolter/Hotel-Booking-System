package com.company.services;


import com.company.model.hotel.Hotel;
import com.company.model.room.Room;
import com.company.model.room.StandardRoom;
import com.company.model.room.Suite;
import com.company.model.user.Customer;
import com.company.model.user.HotelManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.TreeSet;

public class CSVWriter<T> {
    private Path path;

    public CSVWriter(Path path) {
        this.path = path;
    }

    public void write(T object) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
            String objectFile = path.toString().split("\\\\")[1];
            String message;
            switch (objectFile) {
                case "customers.csv": {
                    message = writeCustomer((Customer) object);
                    break;
                }
                case "standardRooms.csv": {
                    message = writeStandardRoom((StandardRoom) object);
                    break;
                }
                case "suites.csv": {
                    message = writeSuite((Suite) object);
                    break;
                }
                case "hotels.csv":{
                    message = writeHotel((Hotel) object);
                    break;
                }
                case "hotelManagers.csv": {
                    message = writeHotelManager((HotelManager) object);
                    break;
                }
                default:
                    message = "";
            }
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String writeCustomer(Customer customer) {
        return customer.getFirstName() + ","
                + customer.getLastName() + ","
                + customer.getUsername() + ","
                + customer.getEmail() + ","
                + customer.getPassword();
    }

    private String writeStandardRoom(StandardRoom room) {
        return room.getId() + ","
                + room.getRoomNo() + ","
                + room.getRoomFloor() + ","
                + room.getPrice() + ","
                + room.getType() + ","
                + room.isAvailable();
    }

    private String writeSuite(Suite room)  {
        return room.getId() + ","
                + room.getRoomNo() + ","
                + room.getRoomFloor() + ","
                + room.getPrice() + ","
                + room.getNoOfRooms() + ","
                + room.isAvailable();
    }

    private String writeHotel(Hotel hotel) {
        StringBuilder output = new StringBuilder(hotel.getName() + ",");
        TreeSet<Room> rooms = hotel.getRooms();
        for(Room room: rooms)
        {
            if(room == rooms.last())
                output.append(room.getId());
            else
                output.append(room.getId()).append(";");
        }
        return output.toString();
    }

    private String writeHotelManager(HotelManager hotelManager) {
        return hotelManager.getFirstName() + ","
                + hotelManager.getLastName() + ","
                + hotelManager.getUsername() + ","
                + hotelManager.getEmail() + ","
                + hotelManager.getPassword() + ","
                + hotelManager.getHotel().getId();
    }

}
