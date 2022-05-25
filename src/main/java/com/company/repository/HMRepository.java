package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.model.hotel.Hotel;
import com.company.model.user.HotelManager;
import com.company.services.HotelService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HMRepository {
    private static HMRepository instance = new HMRepository();
    private HMRepository(){}

    public static HMRepository getInstance() {
        return instance;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS hotelManager " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "firstName VARCHAR(30), " +
                "lastName VARCHAR(30), " +
                "username VARCHAR(30), " +
                "email VARCHAR(100), " +
                "password VARCHAR(30), " +
                "blocked VARCHAR(10), " +
                "hotelId DECIMAL);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertHM(HotelManager hotelManager) {
        String insert = "INSERT INTO hotelManager(firstName, lastName, username, email, password, blocked, hotelId) VALUES(?, ?, ?, ?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, hotelManager.getFirstName());
            preparedStatement.setString(2, hotelManager.getLastName());
            preparedStatement.setString(3, hotelManager.getUsername());
            preparedStatement.setString(4, hotelManager.getEmail());
            preparedStatement.setString(5, hotelManager.getPassword());
            preparedStatement.setString(6, String.valueOf(hotelManager.isBlocked()));
            preparedStatement.setInt(7, hotelManager.getHotel().getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HotelManager> getHotelManagers(){
        HotelService hotelService = HotelService.getInstance();
        List<HotelManager> hotelManagers = new ArrayList<>();
        String sql = "SELECT * FROM hotelManager";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int hotelId = result.getInt(8);
                Hotel hotelManaged = hotelService.getHotels().stream().filter(hotel -> hotel.getId() == hotelId).findFirst().orElse(null);
                hotelManagers.add(new HotelManager(result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getBoolean(7),
                        hotelManaged
                ));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return hotelManagers;
    }

    public void updateHotelManager(String blocked, int id) {
        String updateNameSql = "UPDATE hotelManager SET blocked = ? WHERE id = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, blocked);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}