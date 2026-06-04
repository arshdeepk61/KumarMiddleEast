package com.student.ecommerce.model;

import java.util.Date;

/**
 * Order Model - Represents a purchase order
 * AI Prompt: "Add shipping address, discount code, or order status tracking to this class"
 */
public class Order {
    private int id;
    private int studentId;
    private String clientName;
    private String itemName;
    private int quantity;
    private double amount;
    private String status; // PENDING, PAID, CANCELLED
    private Date orderDate;

    public Order() {}

    public Order(int studentId, String clientName, String itemName, int quantity, double amount) {
        this.studentId = studentId;
        this.clientName = clientName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.amount = amount;
        this.status = "PENDING";
        this.orderDate = new Date();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
}
