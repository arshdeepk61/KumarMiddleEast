# 🎓 StudentEcommerce — NetBeans Project

A complete student e-commerce web application built with:
- **NetBeans 30** (IDE)
- **Java DB / Apache Derby** (built-in database — no install needed!)
- **Jakarta EE** (Servlets, JSP, REST API)
- **Jeddict AI** (AI-friendly structure for code generation)

---

## 📁 Project Structure

```
StudentEcommerce/
├── src/main/java/com/student/ecommerce/
│   ├── model/          → Student, Product, Order, Payment
│   ├── dao/            → Database operations (StudentDAO, OrderDAO)
│   ├── rest/           → REST API endpoints
│   └── util/           → DatabaseUtil, Servlets, AppInitializer
├── src/main/webapp/
│   ├── pages/          → JSP pages (login, register, purchase)
│   ├── css/            → Stylesheet
│   └── WEB-INF/        → web.xml config
├── pom.xml             → Maven dependencies
└── AI_PROMPTS.md       → Ready-to-use Jeddict AI prompts
```

---

## 🚀 How to Import in NetBeans

1. Open **NetBeans 30**
2. Go to **File → Open Project**
3. Browse to the `StudentEcommerce` folder
4. Click **Open Project**
5. Right-click project → **Build** (downloads Maven dependencies)

---

## 🗄️ Database Setup (Built-in Java DB)

1. Press **Ctrl+5** → Open Services tab
2. Expand **Databases → Java DB**
3. Right-click **Java DB** → **Start Server**
4. Run the project — tables are **created automatically!**

---

## 🌐 Running the App

1. Right-click project → **Run**
2. NetBeans will deploy to GlassFish automatically
3. Open browser: **http://localhost:8080/StudentEcommerce/**

---

## 🤖 Using Jeddict AI

Open the **AI_PROMPTS.md** file for ready-to-use prompts to:
- Add PayPal payment
- Add JWT authentication
- Add email notifications
- Generate more features

---

## 📋 REST API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/students/register | Register student |
| POST | /api/students/login | Login |
| GET  | /api/students/{id} | Get student |
| POST | /api/orders | Create order |
| GET  | /api/orders/student/{id} | Get my orders |
| PUT  | /api/orders/{id}/status | Update status |

---

## 🗃️ Database Tables

- **STUDENT** — id, name, email, password, role
- **PRODUCT** — id, name, description, price, stock_quantity
- **ORDERS** — id, student_id, client_name, item_name, quantity, amount, status
- **PAYMENT** — id, order_id, amount, method, status, transaction_id
