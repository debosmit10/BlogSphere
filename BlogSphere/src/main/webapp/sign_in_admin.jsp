<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BlogSphere | Signin</title>
	<link rel="stylesheet" href="css/signup+in.css">
</head>
<body>
	<div class="container">
        <!-- Sign In Form -->
        <div class="form-box" id="sign-in-form">
            <h2>Welcome Back Admin!</h2>
            <form action="signin_admin_servlet" method="post">
                <div class="input-group">
                    <input type="email" id="signin-email" name="email" placeholder="Enter you name" required>
                </div>
                <div class="input-group">
                    <input type="password" id="signin-password" name="pwd" placeholder="Enter you email" required>
                </div>
                <button type="submit" class="btn btn-primary">Sign in</button>
            </form>
            <div class="form-links">
                <a href="#">Don't have an account?</a>
                <!-- <a href="/forgot-password">Forgot password?</a> -->
                <a href="sign_in.jsp">Login as User?</a>
            </div>
        </div>
    </div>
</body>
</html>