<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Login - EduShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="logo">🎓 EduShop</div>
        <h2>Student Login</h2>

        <!-- Error message display -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>
        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>

        <!-- Login Form -->
        <form action="${pageContext.request.contextPath}/LoginServlet" method="POST" onsubmit="return validateLogin()">
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" placeholder="your@email.com" required>
                <span class="error-msg" id="emailError"></span>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Min 6 characters" required>
                <span class="error-msg" id="passwordError"></span>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>

        <div class="link-group">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/pages/register.jsp">Register here</a></p>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/validation.js"></script>
<script>
    function validateLogin() {
        let valid = true;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        document.getElementById('emailError').textContent = '';
        document.getElementById('passwordError').textContent = '';

        if (!email.includes('@')) {
            document.getElementById('emailError').textContent = 'Please enter a valid email';
            valid = false;
        }
        if (password.length < 6) {
            document.getElementById('passwordError').textContent = 'Password must be at least 6 characters';
            valid = false;
        }
        return valid;
    }
</script>
</body>
</html>
