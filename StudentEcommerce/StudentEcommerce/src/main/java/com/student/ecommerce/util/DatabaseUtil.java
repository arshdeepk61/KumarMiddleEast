package com.student.ecommerce.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseUtil - Manages Java DB (Apache Derby) connection
 * This uses NetBeans built-in Java DB — no external database needed!
 *
 * AI Prompt: "Change this to use MySQL by updating the DRIVER and URL below"
 * AI Prompt: "Add connection pooling to this utility class"
 */
public class DatabaseUtil {

    // =====  DB (Built into SQL) =====
 private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
private static final String URL    = "jdbc:mysql://localhost:3306/StudentEcommerceDB";
private static final String USER   = "root";
private static final String PASS   = "root123";


    // ===== TO SWITCH TO MYSQL (uncomment these lines) =====
    // private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // private static final String URL    = "jdbc:mysql://localhost:3306/StudentEcommerceDB";
    // private static final String USER   = "root";
    // private static final String PASS   = "yourpassword";

    private static Connection connection = null;

    /**
     * Get database connection
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("✅ Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Create all tables if they don't exist
     * Run this once when the application starts
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Create STUDENT table
            stmt.executeUpdate(
                "CREATE TABLE STUDENT (" +
                "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "NAME VARCHAR(100) NOT NULL, " +
                "EMAIL VARCHAR(100) NOT NULL UNIQUE, " +
                "PASSWORD VARCHAR(100) NOT NULL, " +
                "ROLE VARCHAR(20) DEFAULT 'student'" +
                ")"
            );
            System.out.println("✅ STUDENT table created");

            // Create PRODUCT table
            stmt.executeUpdate(
                "CREATE TABLE PRODUCT (" +
                "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "NAME VARCHAR(100) NOT NULL, " +
                "DESCRIPTION VARCHAR(500), " +
                "PRICE DECIMAL(10,2) NOT NULL, " +
                "STOCK_QUANTITY INT DEFAULT 0" +
                ")"
            );
            System.out.println("✅ PRODUCT table created");

            // Create ORDERS table
            stmt.executeUpdate(
                "CREATE TABLE ORDERS (" +
                "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "STUDENT_ID INT, " +
                "CLIENT_NAME VARCHAR(100) NOT NULL, " +
                "ITEM_NAME VARCHAR(100) NOT NULL, " +
                "QUANTITY INT DEFAULT 1, " +
                "AMOUNT DECIMAL(10,2) NOT NULL, " +
                "STATUS VARCHAR(20) DEFAULT 'PENDING', " +
                "ORDER_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            System.out.println("✅ ORDERS table created");

            // Create PAYMENT table
            stmt.executeUpdate(
                "CREATE TABLE PAYMENT (" +
                "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "ORDER_ID INT, " +
                "AMOUNT DECIMAL(10,2) NOT NULL, " +
                "METHOD VARCHAR(50) NOT NULL, " +
                "STATUS VARCHAR(20) DEFAULT 'PENDING', " +
                "TRANSACTION_ID VARCHAR(100), " +
                "PAYMENT_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            System.out.println("✅ PAYMENT table created");

            // Insert sample products
            stmt.executeUpdate(
                "INSERT INTO PRODUCT (NAME, DESCRIPTION, PRICE, STOCK_QUANTITY) VALUES " +
                "('Java Programming Book', 'Complete Java guide for students', 49.99, 100)"
            );
            stmt.executeUpdate(
                "INSERT INTO PRODUCT (NAME, DESCRIPTION, PRICE, STOCK_QUANTITY) VALUES " +
                "('Python Crash Course', 'Beginner Python programming book', 39.99, 50)"
            );
            stmt.executeUpdate(
                "INSERT INTO PRODUCT (NAME, DESCRIPTION, PRICE, STOCK_QUANTITY) VALUES " +
                "('USB Study Lamp', 'LED lamp for late night studying', 19.99, 200)"
            );
            System.out.println("✅ Sample products inserted");

        } catch (SQLException e) {
            // Tables may already exist — this is normal on restart
            System.out.println("ℹ️ Tables may already exist: " + e.getMessage());
        }
    }
}
