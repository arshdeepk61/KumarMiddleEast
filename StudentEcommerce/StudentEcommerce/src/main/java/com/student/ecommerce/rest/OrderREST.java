package com.student.ecommerce.rest;

import com.student.ecommerce.dao.OrderDAO;
import com.student.ecommerce.model.Order;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Order REST API
 * Base URL: /api/orders
 *
 * AI Prompt: "Add order total calculation with tax (13% HST for Ontario)"
 * AI Prompt: "Add email notification when order status changes"
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderREST {

    private OrderDAO orderDAO = new OrderDAO();

    /**
     * POST /api/orders
     * Create a new order
     */
    @POST
    public Response createOrder(Order order) {
        if (order.getClientName() == null || order.getClientName().trim().isEmpty()) {
            return Response.status(400).entity("{\"error\":\"Client name is required\"}").build();
        }
        if (order.getItemName() == null || order.getItemName().trim().isEmpty()) {
            return Response.status(400).entity("{\"error\":\"Item name is required\"}").build();
        }
        if (order.getAmount() <= 0) {
            return Response.status(400).entity("{\"error\":\"Amount must be greater than 0\"}").build();
        }

        int orderId = orderDAO.createOrder(order);
        if (orderId > 0) {
            return Response.status(201)
                .entity("{\"message\":\"Order created\",\"orderId\":" + orderId + "}")
                .build();
        }
        return Response.status(500).entity("{\"error\":\"Order creation failed\"}").build();
    }

    /**
     * GET /api/orders/student/{studentId}
     * Get all orders for a student
     */
    @GET
    @Path("/student/{studentId}")
    public Response getStudentOrders(@PathParam("studentId") int studentId) {
        List<Order> orders = orderDAO.getOrdersByStudent(studentId);
        return Response.ok(orders).build();
    }

    /**
     * GET /api/orders/{id}
     * Get a specific order
     */
    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") int id) {
        Order order = orderDAO.getOrderById(id);
        if (order != null) return Response.ok(order).build();
        return Response.status(404).entity("{\"error\":\"Order not found\"}").build();
    }

    /**
     * PUT /api/orders/{id}/status
     * Update order status (PENDING, PAID, CANCELLED)
     */
    @PUT
    @Path("/{id}/status")
    public Response updateStatus(@PathParam("id") int id, @QueryParam("status") String status) {
        if (status == null || status.trim().isEmpty()) {
            return Response.status(400).entity("{\"error\":\"Status is required\"}").build();
        }
        boolean updated = orderDAO.updateStatus(id, status);
        if (updated) return Response.ok("{\"message\":\"Order status updated to " + status + "\"}").build();
        return Response.status(404).entity("{\"error\":\"Order not found\"}").build();
    }

    /**
     * GET /api/orders
     * Get all orders (admin only)
     */
    @GET
    public Response getAllOrders() {
        List<Order> orders = orderDAO.getAllOrders();
        return Response.ok(orders).build();
    }
}
