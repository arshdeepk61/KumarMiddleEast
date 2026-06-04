<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.student.ecommerce.model.Student" %>
<%
    Student loggedIn = (Student) session.getAttribute("student");
    if (loggedIn == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Purchase - EduShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <span class="logo">🎓 EduShop</span>
    <span>Welcome, <%= loggedIn.getName() %></span>
    <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-small">Logout</a>
</nav>

<div class="container">
    <div class="card wide">
        <h2>🛒 Purchase Details</h2>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>
        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/OrderServlet" method="POST" onsubmit="return validateOrder()">

            <div class="form-row">
                <div class="form-group">
                    <label for="clientName">Client Name</label>
                    <input type="text" id="clientName" name="clientName"
                           value="<%= loggedIn.getName() %>" required>
                    <span class="error-msg" id="clientNameError"></span>
                </div>
                <div class="form-group">
                    <label for="itemName">Item / Product Name</label>
                    <input type="text" id="itemName" name="itemName"
                           placeholder="e.g. Java Programming Book" required>
                    <span class="error-msg" id="itemNameError"></span>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="quantity">Quantity</label>
                    <input type="number" id="quantity" name="quantity"
                           value="1" min="1" max="99" oninput="calculateTotal()">
                </div>
                <div class="form-group">
                    <label for="unitPrice">Unit Price ($)</label>
                    <input type="number" id="unitPrice" name="unitPrice"
                           placeholder="0.00" min="0.01" step="0.01" oninput="calculateTotal()">
                    <span class="error-msg" id="amountError"></span>
                </div>
            </div>

            <!-- Order Summary -->
            <div class="summary-box">
                <h3>Order Summary</h3>
                <div class="summary-row">
                    <span>Subtotal:</span>
                    <span id="subtotal">$0.00</span>
                </div>
                <div class="summary-row">
                    <span>HST (13%):</span>
                    <span id="tax">$0.00</span>
                </div>
                <div class="summary-row total">
                    <span>Total Amount:</span>
                    <span id="totalDisplay">$0.00</span>
                </div>
                <input type="hidden" id="amount" name="amount" value="0">
            </div>

            <!-- Payment Method -->
            <div class="form-group">
                <label>Payment Method</label>
                <div class="payment-options">
                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="PAYPAL" checked>
                        <span>💳 PayPal</span>
                    </label>
                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="STRIPE">
                        <span>💳 Stripe</span>
                    </label>
                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="CREDIT_CARD">
                        <span>💳 Credit Card</span>
                    </label>
                </div>
            </div>

            <button type="submit" class="btn btn-primary btn-large">Place Order & Pay</button>
        </form>
    </div>

    <!-- My Orders Section -->
    <div class="card wide">
        <h2>📦 My Orders</h2>
        <a href="${pageContext.request.contextPath}/MyOrdersServlet" class="btn btn-small">View All Orders</a>
    </div>
</div>

<script>
    function calculateTotal() {
        const qty = parseFloat(document.getElementById('quantity').value) || 0;
        const price = parseFloat(document.getElementById('unitPrice').value) || 0;
        const subtotal = qty * price;
        const tax = subtotal * 0.13;
        const total = subtotal + tax;
        document.getElementById('subtotal').textContent = '$' + subtotal.toFixed(2);
        document.getElementById('tax').textContent = '$' + tax.toFixed(2);
        document.getElementById('totalDisplay').textContent = '$' + total.toFixed(2);
        document.getElementById('amount').value = total.toFixed(2);
    }

    function validateOrder() {
        let valid = true;
        const clientName = document.getElementById('clientName').value.trim();
        const itemName = document.getElementById('itemName').value.trim();
        const amount = parseFloat(document.getElementById('amount').value);

        document.getElementById('clientNameError').textContent = '';
        document.getElementById('itemNameError').textContent = '';
        document.getElementById('amountError').textContent = '';

        if (clientName.length < 2) {
            document.getElementById('clientNameError').textContent = 'Client name is required';
            valid = false;
        }
        if (itemName.length < 2) {
            document.getElementById('itemNameError').textContent = 'Item name is required';
            valid = false;
        }
        if (!amount || amount <= 0) {
            document.getElementById('amountError').textContent = 'Enter a valid price';
            valid = false;
        }
        return valid;
    }
</script>
</body>
</html>
