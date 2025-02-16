<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OTP Verification</title>
    <link rel="stylesheet" href="css/signup+in.css">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
        <h2>OTP Verification</h2>
        <span>Username : *username*</span>
        <p>An OTP was sent to you registered email account.<br>Verify to reset your password.</p>
        <form action="#" method="post">
            <div class="input-group">
                <input type="text" id="signin-email" name="otp" placeholder="Enter OTP" required>
            </div>
            <button type="submit" class="btn btn-primary">Verify</button>
            <button type="button" id="btn2" class="btn btn-primary">Resend OTP</button>
        </form>
        <div class="form-links">
            <a href="forgot_password.html">Wrong email?</a>
        </div>
    </div>
</body>
</html>