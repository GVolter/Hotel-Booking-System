package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.room.*;
import com.company.model.user.Customer;
import com.company.model.user.HotelManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class CSVReader<T> {
    public List<T> read(Path path) {
        List<T> objects = new ArrayList<>();
        String objectFile = path.toString().split("\\\\")[1];
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            objects = parseObjects(reader, objectFile);
        } catch (NoSuchFileException e) {
            System.out.println("The file with the name " + path + " doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
        return objects;
    }

    public List<T> parseObjects(BufferedReader reader, String objectFile) throws IOException {
        List<T> objects = new ArrayList<>();
        try {
            switch (objectFile) {
                case "customers.csv": {
                    objects = (List<T>) readCustomers(reader);
                    break;
                }
                case "standardRooms.csv": {
                    objects = (List<T>) readStandardRooms(reader);
                    break;
                }
                case "suites.csv": {
                    objects = (List<T>) readSuites(reader);
                    break;
                }
                case "hotels.csv": {
                    objects = (List<T>) readHotels(reader);
                    break;
                }
                case "hotelManagers.csv": {
                    objects = (List<T>) readHotelManagers(reader);
                    break;
                }
                default:
                    System.out.println("This file can't be read");
            }
        } catch (ClassCastException e) {
            System.out.println("Wrong file association");
        }

        return objects;
    }

    private List<Customer> readCustomers(BufferedReader reader) throws IOException {
        List<Customer> customers = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] information = line.split(",");
            Customer customer = new Customer(information[0], information[1], information[2], information[3], information[4]);
            customers.add(customer);
        }
        return customers;
    }

    private List<StandardRoom> readStandardRooms(BufferedReader bufferedReader) throws IOException {
        List<StandardRoom> standardRooms = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            StandardRoomType type;
            String[] information = line.split(",");
            int roomNo = Integer.parseInt(information[1]);
            int roomFloor = Integer.parseInt(information[2]);
            double price = Double.parseDouble(information[3]);
            if (information[4].equals("SINGLE"))
                type = StandardRoomType.SINGLE;
            else if (information[4].equals("DOUBLE"))
                type = StandardRoomType.DOUBLE;
            else
                type = StandardRoomType.TRIPLE;
            StandardRoom room = new StandardRoom(roomNo, roomFloor, price, type);
            standardRooms.add(room);
        }
        return standardRooms;
    }

    private List<Suite> readSuites(BufferedReader bufferedReader) throws IOException
    {
        List<Suite> suites = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] information = line.split(",");
            int roomNo = Integer.parseInt(information[1]);
            int roomFloor = Integer.parseInt(information[2]);
            double price = Double.parseDouble(information[3]);
            int noOfRooms = Integer.parseInt(information[4]);
            Suite suite = new Suite(roomNo, roomFloor, price, noOfRooms);
            suites.add(suite);
        }
        return suites;
    }

    private List<Hotel> readHotels(BufferedReader bufferedReader) throws IOException
    {
        RoomService roomService = RoomService.getInstance();
        List<Hotel> hotels = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] information = line.split(",");
            String name = information[0];
            String[] roomsId = information[1].split(";");
            TreeSet<Room> rooms = new TreeSet<>(new RoomComparator());
            for (String s : roomsId) {
                int id = Integer.parseInt(s);
                roomService.getRooms().stream().filter(room -> room.getId() == id).findFirst().ifPresent(rooms::add);
            }
            Hotel hotel = new Hotel(name, rooms);
            hotels.add(hotel);
        }
        return hotels;
    }

    private List<HotelManager> readHotelManagers (BufferedReader bufferedReader) throws IOException {
        HotelService hotelService = HotelService.getInstance();
        List<HotelManager> hotelManagers = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] information = line.split(",");
            int hotelId = Integer.parseInt(information[5]);
            Hotel hotelManaged = hotelService.getHotels().stream().filter(hotel -> hotel.getId() == hotelId).findFirst().orElse(null);
            HotelManager hotelManager = new HotelManager(information[0], information[1], information[2], information[3], information[4], hotelManaged);
            hotelManagers.add(hotelManager);
        }
        return hotelManagers;
    }
}
