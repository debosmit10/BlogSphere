<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog Sphere | Write</title>
    <link rel="stylesheet" href="css/write.css?version-1">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="container2">
        <!--  Blog Post Form  -->
        <form action="post_blog_servlet" method="post" id="blog-post-form" enctype="multipart/form-data">
            <input type="text" id="topic" name="topic" placeholder="Enter Topic Name" required>
            <textarea id="body" name="body" rows="20" cols="80" placeholder="Enter Body Text" required></textarea>
            <div>
                <input type="file" id="image" name="image">
                <input type="submit" value="Post" id="post">
            </div>
        </form>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>