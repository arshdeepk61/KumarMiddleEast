<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - EduShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <div class="logo">🎓 EduShop</div>
        <h2>Create Account</h2>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/RegisterServlet" method="POST" onsubmit="return validateRegister()">
            <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" placeholder="John Smith" required>
                <span class="error-msg" id="nameError"></span>
            </div>
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
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Repeat password" required>
                <span class="error-msg" id="confirmError"></span>
            </div>
            <button type="submit" class="btn btn-primary">Create Account</button>
        </form>

        <div class="link-group">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/pages/login.jsp">Login here</a></p>
        </div>
    </div>
</div>

<script>
    function validateRegister() {
        let valid = true;
        const name = document.getElementById('name').value.trim();
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirm = document.getElementById('confirmPassword').value;

        ['nameError','emailError','passwordError','confirmError'].forEach(id =>
            document.getElementById(id).textContent = '');

        if (name.length < 2) {
            document.getElementById('nameError').textContent = 'Name must be at least 2 characters';
            valid = false;
        }
        if (!email.includes('@')) {
            document.getElementById('emailError').textContent = 'Enter a valid email';
            valid = false;
        }
        if (password.length < 6) {
            document.getElementById('passwordError').textContent = 'Password must be at least 6 characters';
            valid = false;
        }
        if (password !== confirm) {
            document.getElementById('confirmError').textContent = 'Passwords do not match';
            valid = false;
        }
        return valid;
    }
</script>
</body>
</html>
