package com.student.ecommerce.util;

import com.student.ecommerce.dao.OrderDAO;
import com.student.ecommerce.model.Order;
import com.student.ecommerce.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OrderServlet - Handles purchase form submission
 * AI Prompt: "Integrate PayPal sandbox API here after order is created"
 * AI Prompt: "Send order confirmation email using Jakarta Mail after saving to database"
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check session
        Student student = (Student) request.getSession().getAttribute("student");
        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        String clientName = request.getParameter("clientName");
        String itemName   = request.getParameter("itemName");
        String amountStr  = request.getParameter("amount");
        String qtyStr     = request.getParameter("quantity");

        // Validation
        if (clientName == null || clientName.trim().isEmpty() ||
            itemName == null || itemName.trim().isEmpty() ||
            amountStr == null || amountStr.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/pages/purchase.jsp").forward(request, response);
            return;
        }

        double amount;
        int quantity;
        try {
            amount   = Double.parseDouble(amountStr);
            quantity = Integer.parseInt(qtyStr != null ? qtyStr : "1");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid amount or quantity");
            request.getRequestDispatcher("/pages/purchase.jsp").forward(request, response);
            return;
        }

        if (amount <= 0) {
            request.setAttribute("error", "Amount must be greater than zero");
            request.getRequestDispatcher("/pages/purchase.jsp").forward(request, response);
            return;
        }

        // Save order
        Order order = new Order(student.getId(), clientName.trim(), itemName.trim(), quantity, amount);
        int orderId = orderDAO.createOrder(order);

        if (orderId > 0) {
            // TODO: Process payment here
            // AI Prompt: "Call PayPal createOrder API here and redirect to PayPal approval URL"
            orderDAO.updateStatus(orderId, "PAID");
            request.setAttribute("success",
                "✅ Order #" + orderId + " placed successfully! Amount: $" + String.format("%.2f", amount));
            request.getRequestDispatcher("/pages/purchase.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Order failed. Please try again.");
            request.getRequestDispatcher("/pages/purchase.jsp").forward(request, response);
        }
    }
}
