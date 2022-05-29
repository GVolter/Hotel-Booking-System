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
        boolean exist = true;
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                exist = false;
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
                    message = writeCustomer((Customer) object, exist);
                    break;
                }
                case "standardRooms.csv": {
                    message = writeStandardRoom((StandardRoom) object, exist);
                    break;
                }
                case "suites.csv": {
                    message = writeSuite((Suite) object, exist);
                    break;
                }
                case "hotels.csv":{
                    message = writeHotel((Hotel) object, exist);
                    break;
                }
                case "hotelManagers.csv": {
                    message = writeHotelManager((HotelManager) object, exist);
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

    private String writeCustomer(Customer customer, boolean exist) {
        String header = "firstName,lastName,username,email,password\n";
        if(!exist)
            return header + customer.getFirstName() + ","
                    + customer.getLastName() + ","
                    + customer.getUsername() + ","
                    + customer.getEmail() + ","
                    + customer.getPassword();
        else
            return customer.getFirstName() + ","
                    + customer.getLastName() + ","
                    + customer.getUsername() + ","
                    + customer.getEmail() + ","
                    + customer.getPassword();
    }

    private String writeStandardRoom(StandardRoom room, boolean exist) {
        String header = "id,roomNo,roomFloor,price,type,availability\n";
        if(!exist)
            return header + room.getId() + ","
                    + room.getRoomNo() + ","
                    + room.getRoomFloor() + ","
                    + room.getPrice() + ","
                    + room.getType() + ","
                    + room.isAvailable();
        else
            return room.getId() + ","
                    + room.getRoomNo() + ","
                    + room.getRoomFloor() + ","
                    + room.getPrice() + ","
                    + room.getType() + ","
                    + room.isAvailable();
    }

    private String writeSuite(Suite room, boolean exist)  {
        String header = "id,roomNo,roomFloor,price,noOfRooms,availability\n";
        if(!exist)
            return header + room.getId() + ","
                    + room.getRoomNo() + ","
                    + room.getRoomFloor() + ","
                    + room.getPrice() + ","
                    + room.getNoOfRooms() + ","
                    + room.isAvailable();
        else
            return room.getId() + ","
                    + room.getRoomNo() + ","
                    + room.getRoomFloor() + ","
                    + room.getPrice() + ","
                    + room.getNoOfRooms() + ","
                    + room.isAvailable();
    }

    private String writeHotel(Hotel hotel, boolean exist) {
        String header = "name,rooms\n";
        StringBuilder output = new StringBuilder(hotel.getName() + ",");
        TreeSet<Room> rooms = hotel.getRooms();
        for(Room room: rooms)
        {
            if(room == rooms.last())
                output.append(room.getId());
            else
                output.append(room.getId()).append(";");
        }
        if(!exist) {
            return header + output;
        }
        else
            return output.toString();
    }

    private String writeHotelManager(HotelManager hotelManager, boolean exist) {
        if(!exist) {
            String header = "firstName,lastName,username,email,password,hotel\n";
            return header + hotelManager.getFirstName() + ","
                    + hotelManager.getLastName() + ","
                    + hotelManager.getUsername() + ","
                    + hotelManager.getEmail() + ","
                    + hotelManager.getPassword() + ","
                    + hotelManager.getHotel().getId();
        }
        else
            return hotelManager.getFirstName() + ","
                    + hotelManager.getLastName() + ","
                    + hotelManager.getUsername() + ","
                    + hotelManager.getEmail() + ","
                    + hotelManager.getPassword() + ","
                    + hotelManager.getHotel().getId();
    }

}
