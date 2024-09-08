package bank.management.system;
// DatabaseHandler.java

import java.sql.*;

public class DatabaseHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/DB";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection conn;

    public DatabaseHandler() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createTable() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users ( Username VARCHAR(50),Father_Name VARCHAR(50), Mother_Name VARCHAR(50), Present_Address VARCHAR(50), Permanent_address VARCHAR(50), Date_of_Birth VARCHAR(50), Phone_Number decimal(10,3), Email VARCHAR(50), Unique_Id VARCHAR(50) PRIMARY KEY , Password_ VARCHAR(50),Balance VARCHAR(50))";
            Statement statement = conn.createStatement();
            statement.executeUpdate(createTableSQL);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean insertUser(String username, String fatherName, String motherName, String presentAddress, String permanentAddress, String dob, String phoneNumber, String email, String uniqueId, String password) {
        try {
            String sql = "INSERT INTO users (Username, Father_Name, Mother_Name, Present_Address, Permanent_address, Date_of_Birth, Phone_Number, Email, Unique_Id, Password_ ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, fatherName);
            pstmt.setString(3, motherName);
            pstmt.setString(4, presentAddress);
            pstmt.setString(5, permanentAddress);
            pstmt.setString(6, dob);
            pstmt.setString(7, phoneNumber);
            pstmt.setString(8, email);
            pstmt.setString(9, uniqueId);
            pstmt.setString(10, password);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getPasswordForUserId(String userId) {
        String password = null;
        try {
            String sql = "SELECT Password_ FROM users WHERE Unique_Id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                password = rs.getString("Password_");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return password;
    }

    public UserData getUserDataByPhoneNumber(String phoneNumber) {
        UserData userData = null;
        String query = "SELECT Username, Unique_Id, balance FROM users WHERE Phone_Number = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("Username");
                String uniqueId = resultSet.getString("Unique_Id");
                double balance = resultSet.getDouble("Balance");

                userData = new UserData(username, uniqueId, balance);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userData;
    }

    public UserData getUserDataByname(String phoneNumber) {
        UserData userData = null;
        String query = "SELECT Username, Unique_Id, balance FROM users WHERE Username LIKE ?";


        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + phoneNumber + "%");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("Username");
                String uniqueId = resultSet.getString("Unique_Id");
                double balance = resultSet.getDouble("Balance");

                userData = new UserData(username, uniqueId, balance);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userData;
    }
    public UserData getUserDataByemail(String phoneNumber) {
        UserData userData = null;
        String query = "SELECT Username, Unique_Id, balance FROM users WHERE Email LIKE ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + phoneNumber + "%");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("Username");
                String uniqueId = resultSet.getString("Unique_Id");
                double balance = resultSet.getDouble("Balance");

                userData = new UserData(username, uniqueId, balance);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userData;
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean updateBlockStatusById(String userId) {
        String updateQuery = "UPDATE users SET Block = ? WHERE Unique_Id = ?";
        PreparedStatement updateStmt = null;

        try {
            updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setBoolean(1, !checkBlockStatusById(userId)); // Get current value and negate
            updateStmt.setString(2, userId);
            int rowsUpdated = updateStmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            // Implement proper error handling (e.g., throw your own exception or log the error)
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (updateStmt != null) {
                    updateStmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean checkBlockStatusById(String userId) {
        String query = "SELECT Block FROM users WHERE Unique_Id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("Block");
            } else {
                System.out.println("User with userId " + userId + " not found.");
                return false; // Or throw an exception if user not found is an error
            }
        } catch (SQLException ex) {
            // Implement proper error handling (e.g., throw your own exception or log the error)
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}

class UserData {

    private String username;
    private String uniqueId;
    private double balance;

    public UserData(String username, String uniqueId, double balance) {
        this.username = username;
        this.uniqueId = uniqueId;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getuniqueId() {
        return uniqueId;
    }

    public double getbalance() {
        return balance;
    }

}
