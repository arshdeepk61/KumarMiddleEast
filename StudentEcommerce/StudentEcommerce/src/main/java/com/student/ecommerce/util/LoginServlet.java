package com.student.ecommerce.util;

import com.student.ecommerce.dao.StudentDAO;
import com.student.ecommerce.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginServlet - Handles login form submission
 * AI Prompt: "Add remember-me cookie functionality to this servlet"
 * AI Prompt: "Add login attempt tracking to prevent brute force attacks"
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Basic validation
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            return;
        }

        // Authenticate
        Student student = studentDAO.login(email.trim(), password);

        if (student != null) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("student", student);
            session.setAttribute("studentId", student.getId());
            session.setAttribute("studentName", student.getName());

            // Redirect based on role
            if ("admin".equals(student.getRole())) {
                response.sendRedirect(request.getContextPath() + "/pages/admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/pages/purchase.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid email or password. Please try again.");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
    }
}
