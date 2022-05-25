package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.model.hotel.Hotel;
import com.company.model.room.Room;
import com.company.model.room.RoomComparator;
import com.company.services.RoomService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class HotelRepository {
    private static HotelRepository instance = new HotelRepository();
    private HotelRepository(){}

    public static HotelRepository getInstance() {
        return instance;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS hotel " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "hotelName VARCHAR(30), " +
                "rooms VARCHAR(30));";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertHotel(Hotel hotel) {
            String insert = "INSERT INTO hotel(hotelName, rooms) VALUES(?, ?);";
            Connection connection = DatabaseConfiguration.getDatabaseConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
                preparedStatement.setString(1, hotel.getName());
                StringBuilder rooms = new StringBuilder();
                for(Room room: hotel.getRooms())
                {
                    if(room == hotel.getRooms().last())
                        rooms.append(room.getId());
                    else
                        rooms.append(room.getId()).append(",");
                }
                preparedStatement.setString(2, rooms.toString());

                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public List<Hotel> getHotels(){
        RoomService roomService = RoomService.getInstance();
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotel";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String[] roomsId = result.getString(3).split(",");
                TreeSet<Room> rooms = new TreeSet<>(new RoomComparator());
                for (String s : roomsId) {
                    int id = Integer.parseInt(s);
                    roomService.getRooms().stream().filter(room -> room.getId() == id).findFirst().ifPresent(rooms::add);
                }
                hotels.add(new Hotel(result.getString(2), rooms));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return hotels;
    }
}