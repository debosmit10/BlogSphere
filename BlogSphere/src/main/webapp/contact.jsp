<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BlogSphere | Contact</title>
    <link rel="stylesheet" href="css/contact.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
        <div class="heading">
            <h1>Contact Us</h1>
            <p>Have questions, feedback, or just want to say hello? We'd love to hear from you!</p>
        </div>
        <div class="container2">
            <div class="content">
                <div class="contact-form">
                    <h2>Send Us a Message</h2>
                    <form action="https://api.web3forms.com/submit" method="POST">
                        <input type="hidden" name="access_key" value="315b95c0-7fc9-42b9-baab-149cca4ae377">
                        <div class="form-group">
                            <!-- <label for="name">Your Name</label> -->
                            <input type="text" id="name" name="name" placeholder="Enter your name" required>
                        </div>
                        <div class="form-group">
                            <!-- <label for="email">Your Email</label> -->
                            <input type="email" id="email" name="email" placeholder="Enter your email" required>
                        </div>
                        <div class="form-group">
                            <!-- <label for="message">Your Message</label> -->
                            <textarea id="message" name="message" placeholder="Write your message here" rows="5" required></textarea>
                        </div>
                        <button type="submit" class="submit-btn">Send Message</button>
                    </form>
                </div>
                <div class="contact-info">
                    <h2>Reach Out to Us</h2>
                    <p><strong>Email:</strong> blogsphere2024@gmail.com</p>
                    <!-- <p><strong>Phone:</strong> +1 234 567 8900</p> -->
                    <!-- <p><strong>Address:</strong> 123 Blog Lane, Web City, Internet</p> -->
                    <div class="social-links">
                        <a href="#">Twitter</a> | <a href="#">Facebook</a> | <a href="#">LinkedIn</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>