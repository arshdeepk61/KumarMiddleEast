# 🤖 AI Prompts Guide — StudentEcommerce Project
# Use these prompts inside Jeddict AI Assistant in NetBeans

## ============================================
## GETTING STARTED
## ============================================

### Start the database (run once):
"Initialize the database tables for the StudentEcommerce project using Java DB Derby"

### Generate sample data:
"Insert 5 sample student records and 10 sample products into the StudentEcommerce database"

## ============================================
## FEATURE ADDITIONS
## ============================================

### Add password hashing:
"Add BCrypt password hashing to the StudentDAO register and login methods"

### Add JWT authentication:
"Add JWT token generation to the StudentREST login endpoint using the jjwt library"

### Add PayPal payment:
"Integrate PayPal sandbox REST API into the OrderServlet to process payments. 
Use client ID sandbox mode and redirect to PayPal approval page"

### Add email notifications:
"Add Jakarta Mail email notification to OrderServlet that sends order 
confirmation to student email after successful purchase"

### Add product listing page:
"Generate a JSP page that shows all products from the PRODUCT table 
with add-to-cart buttons and price display"

### Add shopping cart:
"Add a shopping cart using HttpSession that stores selected products 
before checkout in the StudentEcommerce web app"

### Add admin dashboard:
"Create an admin JSP page that shows total students, total orders, 
total revenue from the StudentEcommerce database"

### Add search functionality:
"Add a search endpoint GET /api/products/search?name=keyword 
to the ProductREST class"

### Add order history page:
"Generate a JSP page showing all orders for the logged-in student 
with status badges (PENDING/PAID/CANCELLED)"

## ============================================
## DATABASE QUERIES (paste in SQL Editor)
## ============================================

# View all students:
SELECT * FROM STUDENT;

# View all orders with student name:
SELECT O.ID, S.NAME, O.CLIENT_NAME, O.ITEM_NAME, O.AMOUNT, O.STATUS 
FROM ORDERS O JOIN STUDENT S ON O.STUDENT_ID = S.ID;

# Total revenue:
SELECT SUM(AMOUNT) AS TOTAL_REVENUE FROM ORDERS WHERE STATUS = 'PAID';

# Orders per student:
SELECT S.NAME, COUNT(O.ID) AS ORDER_COUNT, SUM(O.AMOUNT) AS TOTAL_SPENT
FROM STUDENT S LEFT JOIN ORDERS O ON S.ID = O.STUDENT_ID
GROUP BY S.ID, S.NAME;

## ============================================
## TESTING REST API (use browser or Postman)
## ============================================

# Register a student:
POST http://localhost:8080/StudentEcommerce/api/students/register
Body: {"name":"John Smith","email":"john@test.com","password":"pass123"}

# Login:
POST http://localhost:8080/StudentEcommerce/api/students/login
Body: {"email":"john@test.com","password":"pass123"}

# Create order:
POST http://localhost:8080/StudentEcommerce/api/orders
Body: {"studentId":1,"clientName":"John","itemName":"Java Book","quantity":1,"amount":56.49}

# Get student orders:
GET http://localhost:8080/StudentEcommerce/api/orders/student/1
