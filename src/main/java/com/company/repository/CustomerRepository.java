package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.model.user.Customer;
import com.company.services.AuditService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private static CustomerRepository instance = new CustomerRepository();
    private CustomerRepository(){}

    public static CustomerRepository getInstance() {
        return instance;
    }

    AuditService auditService = AuditService.getInstance();

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS customer " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "firstName VARCHAR(30), " +
                "lastName VARCHAR(30), " +
                "username VARCHAR(30), " +
                "email VARCHAR(100), " +
                "password VARCHAR(30), " +
                "blocked VARCHAR(30));";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute(createTableSql);
            auditService.logMessage("Table customer created");
            DatabaseConfiguration.closeDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCustomer(Customer customer) {
        String insert = "INSERT INTO customer(firstName, lastName, username, email, password, blocked) VALUES(?, ?, ?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getUsername());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getPassword());
            preparedStatement.setString(6, String.valueOf(customer.isBlocked()));

            preparedStatement.executeUpdate();
            auditService.logMessage("Customer inserted");
            DatabaseConfiguration.closeDatabaseConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers(){
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            ResultSet result = statement.executeQuery();
            while(result.next()){
                customers.add(new Customer(result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getBoolean(7)
                ));
            }
            auditService.logMessage("Customers read");
            DatabaseConfiguration.closeDatabaseConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    public void updateCustomer(String blocked, int id) {
        String updateNameSql = "UPDATE customer SET blocked = ? WHERE id = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, blocked);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            auditService.logMessage("Customer updated");
            DatabaseConfiguration.closeDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id){
        String sql = "DELETE FROM customer WHERE id = ?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {

            statement.setInt(1, id);
            statement.executeUpdate();
            auditService.logMessage("Customer deleted");
            DatabaseConfiguration.closeDatabaseConnection();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
