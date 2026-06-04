package com.student.ecommerce.util;

import com.student.ecommerce.dao.StudentDAO;
import com.student.ecommerce.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RegisterServlet - Handles student registration
 * AI Prompt: "Add email verification before activating the account"
 * AI Prompt: "Add CAPTCHA verification to prevent bot registrations"
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name     = request.getParameter("name");
        String email    = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm  = request.getParameter("confirmPassword");

        // Validation
        if (name == null || name.trim().length() < 2) {
            request.setAttribute("error", "Name must be at least 2 characters");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            return;
        }
        if (email == null || !email.contains("@")) {
            request.setAttribute("error", "Please enter a valid email address");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            return;
        }
        if (password == null || password.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirm)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            return;
        }

        // Check if email already taken
        if (studentDAO.emailExists(email.trim())) {
            request.setAttribute("error", "This email is already registered");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            return;
        }

        // Register
        Student student = new Student(name.trim(), email.trim(), password, "student");
        boolean success = studentDAO.register(student);

        if (success) {
            request.setAttribute("success", "Account created! Please login.");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
        }
    }
}
