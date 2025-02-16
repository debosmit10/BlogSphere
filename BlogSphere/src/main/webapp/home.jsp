<%@page import="jakarta.servlet.jsp.tagext.TryCatchFinally"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("id") == null) {
		response.sendRedirect("sign_in.jsp");
	}
%>
<%@ page import="java.util.*" %>
<%@ page import="com.blogsphere.home.Blog"%>
<%@ page import="java.sql.*" %>
<%
	String role = (String) session.getAttribute("role");
	/* String uId = (String) session.getAttribute("id"); */
	List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
	List<Blog> topBlogs = (List<Blog>) request.getAttribute("topBlogs");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>BlogSphere | Home</title>
	<link rel="stylesheet" href="css/home.css">
	<link rel="stylesheet" href="css/button.css">
	<link rel="stylesheet" href="css/header.css">
	<link rel="stylesheet" href="css/footer.css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
    <div class="container">
        <!------------------- LEFT SIDE ------------------->
        <div class="left-bar">
        	<% if("user".equals(role)) { %>
            	<div class="button-container">
                	<a href="write.jsp">
                    	<div class="circle-button">
                        	<span class="material-icons">create</span>
                    	</div>
                	</a>
                	<span class="button-text">Write</span>
            	</div>
            <% } %>
            <div class="button-container">
                <a href="#notifications">
                    <div class="circle-button">
                        <span class="material-icons">notifications</span>
                    </div>
                </a>
                <span class="button-text">Notifications</span>
            </div>
        </div>
        <!------------------- MIDDLE FEED ------------------->
        <div class="feed">
            <div class="feed-header">
                <h1>Blogs</h1>
            </div>
            <!------------------- POSTS ------------------->
            <% if (blogs != null) { %>
            <% for(Blog blog : blogs) {
            	int blogId = blog.getBlogId();
            	String BlogUID = blog.getUserId();
                String userId = (String) session.getAttribute("id");
                boolean hasLiked = false;
                Connection con = null;
            	
                //Like status
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
            <div class="blog">
                <div class="blog-left">
                    <div id="pic-name">
	                    <div class="pfp">
	                        <img src="images/pfp/<%= blog.getPfp() %>" alt="PFP">
	                    </div>
	                    <div class="username">
	                    	<a href="user_profile_servlet?userId=<%= BlogUID %>">
	                    		<%= blog.getUsername() %>
	                    	</a>
	                    </div>
                    </div>
                    <div class="topic">
                    	<a href="blog_details_servlet?id=<%= blog.getBlogId() %>">
                    		<%= blog.getTopic() %>
                    	</a>
                    </div>
                    <div class="content">
                    	<a href="blog_details_servlet?id=<%= blog.getBlogId() %>" class="blog-link">
                    		<%= blog.getBody().substring(0, Math.min(blog.getBody().length(), 100)) %>...
                    	</a>
                    </div>
                    <div class="blog-footer">
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
                    </div>
                </div>
                <div class="blog-right">
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
                	
                	<% if (blog.getImage() != null) { %>
                    	<img src="images/post-image/<%= blog.getImage() %>" alt="Image not loaded">
                    <% } %>
                </div>
            </div>
            <% } %>
            <% } else { %>
            	<p>No blogs available.</p>
            <% } %>
        </div>
        <!------------------- RIGHT SIDE ------------------->
        <div class="right-bar">
        	<!------------------- TOP PICKS ------------------->
            <h1>Top Picks</h1>
	            <div>
		            <% if (topBlogs != null) { %>
		            	<% for (Blog blog : topBlogs) { %>
			                <div class="top-pick" id="top1">
			                	<% if (blog.getImage() != null) { %>
				                    <div class="image">
				                        <img src="images/post-image/<%= blog.getImage() %>" alt="Image not loaded">
				                    </div>
			                    <% } %>
			                    <div class="topic">
			                    	<a href="blog_details_servlet?id=<%= blog.getBlogId() %>">
			                    		<%= blog.getTopic() %>
			                    	</a>
			                    </div>
			                </div>
		                <% } %>
		            <% } else { %>
		            	<p>No top blogs to display.</p>
		            <% } %>
	            </div>
            <div>
                <!--MORE CONTENT-->
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
        	const blogRight = document.querySelectorAll('.blog-right');
        	
        	blogRight.forEach((container) => {
        		const more_horiz = container.querySelector('.more_horiz')
        		const menu = container.querySelector('.post-menu');
        		
        		//Toggle visibility when button is clicked
        		more_horiz.addEventListener('click', (event) => {
        			event.stopPropagation(); //To prevent click from bubbling up to document
                	menu.classList.toggle('active');
        		});
        	});
        	
        	//Close all dropdown menus when clicking outside
        	document.addEventListener('click', () => {
        		const openMenu = document.querySelectorAll('.post-menu.active');
        		openMenu.forEach((menu) => menu.classList.remove('active'));
        	});
        });
    </script>
</body>
</html>