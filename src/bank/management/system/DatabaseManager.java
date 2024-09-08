/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank.management.system;

/**
 *
 * @author Md.Nahin
 */
import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseManager {
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/db";
    private String username = "root";
    private String password = "";
    private String Date_of_Birth;
    // Constructor to establish connection
    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to transfer balance from one Unique_Id to another
    public void transferBalance(String sourceUniqueId, String targetUniqueId, double transferAmount) {
        try {
            // Retrieve balance from source Unique_Id
            double balance = getBalanceByUniqueId(sourceUniqueId);

            // Check if source account has sufficient balance
            if (balance < transferAmount) {
                JOptionPane.showMessageDialog(null, " Insufficient balance for transfer!");
                return;
            }

            // Update balance for source Unique_Id
            updateBalance(sourceUniqueId, balance - transferAmount);

            // Add transferred balance to target Unique_Id
            updateBalance(targetUniqueId, getBalanceByUniqueId(targetUniqueId) + transferAmount);

            JOptionPane.showMessageDialog(null, " Transfer successfull");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get balance by Unique_Id
    private double getBalanceByUniqueId(String uniqueId) throws SQLException {
        double balance = 0;
        String query = "SELECT balance FROM users WHERE Unique_Id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uniqueId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                balance = resultSet.getDouble("balance");
            }
        }

        return balance;
    }

    // Method to update balance for a specific Unique_Id
    private void updateBalance(String uniqueId, double newBalance) throws SQLException {
        String query = "UPDATE users SET balance = ? WHERE Unique_Id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, newBalance);
            statement.setString(2, uniqueId);
            statement.executeUpdate();
           
        }
    }
        // Method to change password by Unique_Id
    public void changePassword(String uniqueId, String newPassword) {
        try {
            // Update password for the given Unique_Id
            updatePassword(uniqueId, newPassword);
                        JOptionPane.showMessageDialog(null, "Password changed successfully", ":)", JOptionPane.ERROR_MESSAGE); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update password for a specific Unique_Id
    private void updatePassword(String uniqueId, String newPassword) throws SQLException {
        String query = "UPDATE users SET Password_ = ? WHERE Unique_Id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPassword);
            statement.setString(2, uniqueId);
            statement.executeUpdate();
        }
    }

    // Close connection when done
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double accountinfo(String sourceUniqueId) {
    double balance = 0.0;
    Connection connection = this.connection; // Use the existing connection

    if (connection != null) {
        try {
            String query = "SELECT balance FROM users WHERE Unique_Id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, sourceUniqueId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        balance = resultSet.getDouble("balance");
                    } else {
                        System.out.println("No account found with UniqueId: " + sourceUniqueId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Connection to database failed.");
    }

    return balance;
}
    // Method to retrieve account information by Unique_Id
    public AccountInfoData accountInfo(String uniqueId) {
        AccountInfoData accountInfoData = null;
        try {
            // SQL query to retrieve account information
            String sql = "SELECT Username, Father_Name, Mother_Name, Present_Address, Permanent_address, Date_of_Birth, Phone_Number, Email " +
                         "FROM users WHERE Unique_Id = ?";

            // Creating prepared statement
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, uniqueId);
                
                // Executing query
                try (ResultSet rs = stmt.executeQuery()) {
                    // Checking if any data is retrieved
                    if (rs.next()) {
                        // Retrieving data from the result set
                        String username = rs.getString("Username");
                        String fatherName = rs.getString("Father_Name");
                        String motherName = rs.getString("Mother_Name");
                        String presentAddress = rs.getString("Present_Address");
                        String permanentAddress = rs.getString("Permanent_address");
                        String dob = rs.getString("Date_of_Birth");
                        String phoneNumber = rs.getString("Phone_Number");
                        String email = rs.getString("Email");

                        // Creating an AccountInfoData object
                        accountInfoData = new AccountInfoData(username, fatherName, motherName,
                                                               presentAddress, permanentAddress,
                                                               dob, phoneNumber, email,Date_of_Birth);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountInfoData;
    }
}

 class AccountInfoData {
    private String username;
    private String fatherName;
    private String motherName;
    private String presentAddress;
    private String permanentAddress;
    private String dob;
    private String phoneNumber;
    private String email;
    private String Date_of_Birth;

    public AccountInfoData(String username, String fatherName, String motherName,
                           String presentAddress, String permanentAddress,
                           String dob, String phoneNumber, String email,String Date_of_Birth) {
        this.username = username;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.presentAddress = presentAddress;
        this.permanentAddress = permanentAddress;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.Date_of_Birth=Date_of_Birth;
    }

    // Getter methods for each field
    public String getUsername() {
        return username;
    }
    public String getDateOfBirth() {
        return Date_of_Birth;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public String getDob() {
        return dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}

