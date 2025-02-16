<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forget Password</title>
    <link rel="stylesheet" href="css/signup+in.css">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
        <!-- Forgot Password Form -->
        <h2>Forgot Password</h2>
        <p>Enter your registered email to receive an OTP.</p>
        <form action="forgot_pwd_servlet" method="post">
            <div class="input-group">
                <input type="email" id="signin-email" name="email" placeholder="Enter your email" required>
            </div>
            <button type="submit" class="btn btn-primary">Send OTP</button>
        </form>
    </div>
</body>
</html>