package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.model.room.Suite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuiteRepository {
    private static SuiteRepository instance = new SuiteRepository();
    private SuiteRepository(){}

    public static SuiteRepository getInstance() {
        return instance;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS suite " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "roomNo DECIMAL, " +
                "roomFloor DECIMAL, "+
                "price DECIMAL, " +
                "noOfRooms DECIMAL, " +
                "availability VARCHAR(10));";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSuite(Suite room) {
        String insert = "INSERT INTO suite(roomNo, roomFloor, price, noOfRooms, availability) VALUES(?, ?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(1, room.getRoomNo());
            preparedStatement.setInt(2, room.getRoomFloor());
            preparedStatement.setDouble(3, room.getPrice());
            preparedStatement.setInt(4, room.getNoOfRooms());
            preparedStatement.setString(5, String.valueOf(room.isAvailable()));

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Suite> getSuites(){
        List<Suite> suites = new ArrayList<>();
        String sql = "SELECT * FROM suite";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            ResultSet result = statement.executeQuery();
            while(result.next()){
                suites.add(new Suite(result.getInt(2),
                        result.getInt(3),
                        result.getDouble(4),
                        result.getBoolean(6),
                        result.getInt(5)
                ));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return suites;
    }

    public void updateSuite(String availability, int id) {
        String updateNameSql = "UPDATE suite SET availability = ? WHERE id = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, availability);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}