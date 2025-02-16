<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Blog Sphere</title>
	<link rel="stylesheet" href="css/index.css?version-1">
	<link rel="stylesheet" href="css/footer.css">
</head>
<body>
  <div class="container">
    <!-- Left Side -->
    <div class="left-side">
      <h1 class="site-name">Blog Sphere</h1>
      <nav>
        <ul class="nav-list">
          <li><a href="home_feed_servlet">Home</a></li>
          <li><a href="aboutUs.jsp">About Us</a></li>
          <li><a href="contact.jsp">Contact</a></li>
          <li><a href="sign_in.jsp">Sign in</a></li>
        </ul>
      </nav>
      <a href="sign_up.jsp"><button class="get-started">Get Started</button></a>
    </div>
    <!-- Right Side -->
    <div class="right-side">
      <h1 class="overlay-title">Blog Sphere</h1>
      <video class="background-video" autoplay muted loop>
        <source src="videos/home_bg_loop_720p.mp4" type="video/mp4">
      </video>
    </div>
  </div>
  <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>