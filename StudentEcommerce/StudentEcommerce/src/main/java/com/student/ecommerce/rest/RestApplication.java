package com.student.ecommerce.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * REST Application Configuration
 * All REST endpoints available at: http://localhost:8080/StudentEcommerce/api/
 *
 * AI Prompt: "Add CORS filter to allow requests from frontend on different port"
 * AI Prompt: "Add authentication filter to protect all endpoints except /login and /register"
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
}
