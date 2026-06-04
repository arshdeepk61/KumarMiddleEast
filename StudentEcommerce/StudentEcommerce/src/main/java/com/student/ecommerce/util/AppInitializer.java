package com.student.ecommerce.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * AppInitializer - Runs when the application starts
 * Automatically creates database tables on first run
 *
 * AI Prompt: "Add application configuration loading from properties file here"
 */
@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("====================================");
        System.out.println("  Student E-Commerce App Starting  ");
        System.out.println("====================================");
        DatabaseUtil.initializeDatabase();
        System.out.println("App ready at: http://localhost:8080/StudentEcommerce/");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseUtil.closeConnection();
        System.out.println("App stopped.");
    }
}
