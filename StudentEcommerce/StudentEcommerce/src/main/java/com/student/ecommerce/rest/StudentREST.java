package com.student.ecommerce.rest;

import com.student.ecommerce.dao.StudentDAO;
import com.student.ecommerce.model.Student;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Student REST API
 * Base URL: /api/students
 *
 * AI Prompt: "Add JWT token generation on successful login"
 * AI Prompt: "Add forgot password endpoint with email reset link"
 * AI Prompt: "Add input sanitization to prevent SQL injection"
 */
@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentREST {

    private StudentDAO studentDAO = new StudentDAO();

    /**
     * POST /api/students/register
     * Register a new student
     *
     * AI Prompt to test: "Generate a curl command to test this register endpoint"
     */
    @POST
    @Path("/register")
    public Response register(Student student) {
        // Validation
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return Response.status(400).entity("{\"error\":\"Name is required\"}").build();
        }
        if (student.getEmail() == null || !student.getEmail().contains("@")) {
            return Response.status(400).entity("{\"error\":\"Valid email is required\"}").build();
        }
        if (student.getPassword() == null || student.getPassword().length() < 6) {
            return Response.status(400).entity("{\"error\":\"Password must be at least 6 characters\"}").build();
        }

        // Check duplicate email
        if (studentDAO.emailExists(student.getEmail())) {
            return Response.status(409).entity("{\"error\":\"Email already registered\"}").build();
        }

        boolean success = studentDAO.register(student);
        if (success) {
            return Response.status(201)
                .entity("{\"message\":\"Student registered successfully\",\"email\":\"" + student.getEmail() + "\"}")
                .build();
        }
        return Response.status(500).entity("{\"error\":\"Registration failed\"}").build();
    }

    /**
     * POST /api/students/login
     * Login with email and password
     */
    @POST
    @Path("/login")
    public Response login(Student loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return Response.status(400).entity("{\"error\":\"Email and password required\"}").build();
        }

        Student student = studentDAO.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (student != null) {
            // TODO: Generate JWT token here
            // AI Prompt: "Replace this response with a JWT token using jjwt library"
            return Response.ok()
                .entity("{\"message\":\"Login successful\",\"id\":" + student.getId() +
                        ",\"name\":\"" + student.getName() +
                        "\",\"role\":\"" + student.getRole() + "\"}")
                .build();
        }
        return Response.status(401).entity("{\"error\":\"Invalid email or password\"}").build();
    }

    /**
     * GET /api/students/{id}
     * Get student profile
     */
    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") int id) {
        Student student = studentDAO.getById(id);
        if (student != null) {
            student.setPassword(null); // Never return password
            return Response.ok(student).build();
        }
        return Response.status(404).entity("{\"error\":\"Student not found\"}").build();
    }
}
