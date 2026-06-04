package com.student.ecommerce.dao;

import com.student.ecommerce.model.Order;
import com.student.ecommerce.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDAO - Database operations for Orders
 * AI Prompt: "Add a method to get orders by date range"
 * AI Prompt: "Add order cancellation with reason field"
 */
public class OrderDAO {

    /**
     * Create a new order
     */
    public int createOrder(Order order) {
        String sql = "INSERT INTO ORDERS (STUDENT_ID, CLIENT_NAME, ITEM_NAME, QUANTITY, AMOUNT, STATUS) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getStudentId());
            ps.setString(2, order.getClientName());
            ps.setString(3, order.getItemName());
            ps.setInt(4, order.getQuantity());
            ps.setDouble(5, order.getAmount());
            ps.setString(6, "PENDING");
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) {
            System.err.println("CreateOrder error: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Get all orders for a student
     */
    public List<Order> getOrdersByStudent(int studentId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM ORDERS WHERE STUDENT_ID = ? ORDER BY ORDER_DATE DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapOrder(rs));
        } catch (SQLException e) {
            System.err.println("GetOrders error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Get order by ID
     */
    public Order getOrderById(int id) {
        String sql = "SELECT * FROM ORDERS WHERE ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapOrder(rs);
        } catch (SQLException e) {
            System.err.println("GetOrderById error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Update order status
     */
    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE ORDERS SET STATUS = ? WHERE ID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UpdateStatus error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get all orders (admin view)
     */
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM ORDERS ORDER BY ORDER_DATE DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapOrder(rs));
        } catch (SQLException e) {
            System.err.println("GetAllOrders error: " + e.getMessage());
        }
        return list;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("ID"));
        o.setStudentId(rs.getInt("STUDENT_ID"));
        o.setClientName(rs.getString("CLIENT_NAME"));
        o.setItemName(rs.getString("ITEM_NAME"));
        o.setQuantity(rs.getInt("QUANTITY"));
        o.setAmount(rs.getDouble("AMOUNT"));
        o.setStatus(rs.getString("STATUS"));
        o.setOrderDate(rs.getTimestamp("ORDER_DATE"));
        return o;
    }
}
