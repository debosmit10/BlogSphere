<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Blogsphere | Signup</title>
	<link rel="stylesheet" href="css/signup+in.css">
</head>
<body>
	<div class="container">
        <!-- Sign Up Form -->
        <div class="form-box" id="sign-up-form">
            <h2>Sign Up</h2>
            <form action="signup_servlet" method="post">
                <div class="input-group">
                    <input type="text" id="signup-name" name="name" placeholder="Enter you name" required>
                </div>
                <div class="input-group">
                    <input type="email" id="signup-email" name="email" placeholder="Enter you email" required>
                </div>
                <div class="input-group">
                    <input type="password" id="signup-password" name="pwd" placeholder="Enter password" required>
                </div>
                <div class="input-group">
                    <input type="password" id="signup-confirm-password" name="confirm_pwd" placeholder="Confirm password" required>
                </div>
                <button type="submit" class="btn btn-primary">Sign Up</button>
            </form>
            <div class="form-links">
                <a href="sign_in.jsp">Already have an account?</a>
            </div>
        </div>
    </div>
</body>
</html>