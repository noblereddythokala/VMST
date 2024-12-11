package org.example.vmst;

import java.sql.*;

public class UserDAO {

    // Method to authenticate user during login (no password checking)
    public User authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password")); // Still retrieve password hash but won't check it
                user.setRole(rs.getString("role"));
                user.setName(rs.getString("name"));
                user.setContactInfo(rs.getString("contact_info"));

                // Verify the role for authentication
                if (user.getRole().equalsIgnoreCase("vehicle owner") ||
                        user.getRole().equalsIgnoreCase("service technician") ||
                        user.getRole().equalsIgnoreCase("administrator")) {
                    return user;  // Return user if role matches
                } else {
                    System.out.println("Invalid role for user.");
                }
            } else {
                System.out.println("User not found.");
            }
        }
        return null;  // Return null if no user matches or role is invalid
    }

    // Add a new user
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (username, password, role, name, contact_info) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());  // Store password as entered (no hashing)
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getName());
            stmt.setString(5, user.getContactInfo());
            stmt.executeUpdate();
        }
    }

    // Update an existing user
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET username = ?, password = ?, role = ?, name = ?, contact_info = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());  // Update with the password as entered
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getName());
            stmt.setString(5, user.getContactInfo());
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();
        }
    }

    // Delete a user
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}
