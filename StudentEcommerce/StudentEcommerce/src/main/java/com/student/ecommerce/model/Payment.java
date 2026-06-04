package com.student.ecommerce.model;

import java.util.Date;

/**
 * Payment Model - Represents a payment transaction
 * AI Prompt: "Add PayPal transaction ID, currency type, or refund status to this class"
 */
public class Payment {
    private int id;
    private int orderId;
    private double amount;
    private String method;       // PAYPAL, STRIPE, CREDIT_CARD
    private String status;       // SUCCESS, FAILED, PENDING, REFUNDED
    private String transactionId;
    private Date paymentDate;

    public Payment() {}

    public Payment(int orderId, double amount, String method) {
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.status = "PENDING";
        this.paymentDate = new Date();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
}
