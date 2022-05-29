package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.model.room.StandardRoom;
import com.company.model.room.StandardRoomType;
import com.company.services.AuditService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SRRepository {
    private static SRRepository instance = new SRRepository();
    private SRRepository(){}

    public static SRRepository getInstance() {
        return instance;
    }

    AuditService auditService = AuditService.getInstance();

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS standardRoom " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "roomNo DECIMAL, " +
                "roomFloor DECIMAL, "+
                "price DECIMAL, " +
                "roomType VARCHAR(10), " +
                "availability VARCHAR(10));";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute(createTableSql);
            auditService.logMessage("Table standardRoom created");
            DatabaseConfiguration.closeDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStandardRoom(StandardRoom room) {
        String insert = "INSERT INTO standardRoom(roomNo, roomFloor, price, roomType, availability) VALUES(?, ?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(1, room.getRoomNo());
            preparedStatement.setInt(2, room.getRoomFloor());
            preparedStatement.setDouble(3, room.getPrice());
            preparedStatement.setString(4, room.getType().toString());
            preparedStatement.setString(5, String.valueOf(room.isAvailable()));

            preparedStatement.executeUpdate();
            auditService.logMessage("StandardRoom inserted");
            DatabaseConfiguration.closeDatabaseConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<StandardRoom> getStandardRooms(){
        List<StandardRoom> standardRooms = new ArrayList<>();
        String sql = "SELECT * FROM standardRoom";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            ResultSet result = statement.executeQuery();
            while(result.next()){
                StandardRoomType type;
                if (result.getString(5).equals("SINGLE"))
                    type = StandardRoomType.SINGLE;
                else if (result.getString(5).equals("DOUBLE"))
                    type = StandardRoomType.DOUBLE;
                else
                    type = StandardRoomType.TRIPLE;
                standardRooms.add(new StandardRoom(result.getInt(2),
                        result.getInt(3),
                        result.getDouble(4),
                        result.getBoolean(6),
                        type
                ));
            }
            auditService.logMessage("StandardRooms read");
            DatabaseConfiguration.closeDatabaseConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return standardRooms;
    }

    public void updateStandardRoom(String availability, int id) {
        String updateNameSql = "UPDATE standardroom SET availability = ? WHERE id = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, availability);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            auditService.logMessage("StandardRoom updated");
            DatabaseConfiguration.closeDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}