package com.student.ecommerce.dao;

import com.student.ecommerce.model.Student;
import com.student.ecommerce.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO - Database operations for Student
 * AI Prompt: "Add a method to update student password with old password verification"
 * AI Prompt: "Add email verification token field and method to this DAO"
 */
public class StudentDAO {

    /**
     * Register a new student
     * AI Prompt: "Add password hashing using BCrypt before saving"
     */
    public boolean register(Student student) {
        String sql = "INSERT INTO STUDENT (NAME, EMAIL, PASSWORD, ROLE) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPassword()); // TODO: hash password
            ps.setString(4, student.getRole() != null ? student.getRole() : "student");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Register error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Login - check email and password
     * AI Prompt: "Add login attempt counter and account lockout after 5 failed attempts"
     */
    public Student login(String email, String password) {
        String sql = "SELECT * FROM STUDENT WHERE EMAIL = ? AND PASSWORD = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapStudent(rs);
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get student by ID
     */
    public Student getById(int id) {
        String sql = "SELECT * FROM STUDENT WHERE ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapStudent(rs);
        } catch (SQLException e) {
            System.err.println("GetById error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all students (admin only)
     */
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM STUDENT";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapStudent(rs));
        } catch (SQLException e) {
            System.err.println("GetAll error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Check if email already exists
     */
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM STUDENT WHERE EMAIL = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("EmailExists error: " + e.getMessage());
        }
        return false;
    }

    // Helper to map ResultSet to Student object
    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("ID"));
        s.setName(rs.getString("NAME"));
        s.setEmail(rs.getString("EMAIL"));
        s.setPassword(rs.getString("PASSWORD"));
        s.setRole(rs.getString("ROLE"));
        return s;
    }
}
