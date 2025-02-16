<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.blogsphere.home.Blog"%>
<%@ page import="java.sql.*" %>
<%
    Blog blog = (Blog) request.getAttribute("blog");
	String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog Sphere | Blog</title>
    <link rel="stylesheet" href="css/blog.css?version-1">
    <link rel="stylesheet" href="css/header.css?version-2">
    <link rel="stylesheet" href="css/footer.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<%
			int blogId = blog.getBlogId();
			String BlogUID = blog.getUserId();
			System.out.println("BlogUID : " + BlogUID);
			String userId = (String) session.getAttribute("id");
			boolean hasLiked = false;
            Connection con = null;
			
            try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
    			String checkQuery = "SELECT * FROM Likes WHERE UID = ? AND BID = ?";
    			PreparedStatement pst = con.prepareStatement(checkQuery);
    			pst.setString(1, userId);
    	        pst.setInt(2, blogId);
    	        ResultSet rs = pst.executeQuery();
    	        if (rs.next()) {
    	            hasLiked = true;
    	        }
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
		%>
        <div class="middle">
        	<% if (blog.getImage() != null) { %>
            	<img src="images/post-image/<%= blog.getImage() %>" alt="Blog Image" id="blog-img">
            <% } %>
            <div class="topic"><%= blog.getTopic() %></div>
            <div class="info">
                <div id="pic-name">
                    <img src="images/pfp/<%= blog.getPfp() %>" alt="pfp">
                    <div class="username">
                        <a href="user_profile_servlet?userId=<%= BlogUID %>"><%= blog.getUsername() %></a>
                    </div>
                </div>
                <div id="time-likes">
                    <div class="time"><%= blog.getTime() %></div>
                    <div class="like-btn">
                        <form action="like_servlet" method="POST">
							<input type="hidden" name="blogId" value="<%= blogId %>">
							<input type="hidden" name="userId" value="<%= userId %>">
							<button type="submit" class="like-button <%= hasLiked ? "liked" : "" %>">
							    <span class="material-icons-round">
							        <%= hasLiked ? "thumb_up" : "thumb_up_off_alt" %>
							    </span>
							</button>
						</form>   
                    </div>
                    <div class="like-count"><%= blog.getLikes() %></div>
                    <button class="more_horiz" id="more_horiz">
                        <span class="material-icons-round">more_horiz</span>
                    </button>
                    <%
                		if(BlogUID.equals(userId) || "admin".equals(role)) {
                	%>
	                	<nav class="post-menu" id="post-menu">
	                		<a href="delete_blog_servlet?blogId=<%= blogId %>" id="del">Delete</a>
	                	</nav>
                	<% } %>
                </div>
            </div>
            <div class="body"><%= blog.getBody() %></div>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
    <script>
	    document.addEventListener('DOMContentLoaded', () => {
	        const more_horiz = document.getElementById('more_horiz');
	        const menu = document.getElementById('post-menu');
	
	        more_horiz.addEventListener('click', () => {
	            menu.classList.toggle('active');
	        });
	    });
    </script>
</body>
</html>