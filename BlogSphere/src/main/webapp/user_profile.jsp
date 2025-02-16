<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.blogsphere.home.Blog"%>
<%@ page import="java.sql.*" %>
<%
    String role = (String) session.getAttribute("role");
	String profileUID = (String) request.getAttribute("id");
	String userId = (String) session.getAttribute("id");
	List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog Sphere | Profile</title>
    <link rel="stylesheet" href="css/user_profile.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="css/button.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
        <div class="banner">
            <img src="images/pfp/<%= request.getAttribute("pfp") %>" alt="Profile Picture">
            <span><%= request.getAttribute("name") %></span>
        </div>
        <div class="content">
            <div class="left-bar">
            	<% if(profileUID.equals(userId)) {%>
            	<div class="button-container">
            		<button class="circle-button" onclick="showSection('blogs')">
            			<span class="material-icons">view_timeline</span>
            		</button>
            		<span class="button-text">Blogs</span>
            	</div>
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
            	<div class="button-container">
            		<button class="circle-button" onclick="showSection('settings')">
            			<span class="material-icons">settings</span>
            		</button>
            		<span class="button-text">Settings</span>
            	</div>
            	<% } %>
            </div>
            <div class="right-side">
                <section id="blogs" class="section">
                    <!------------------- POSTS ------------------->
                    <% if (blogs != null) { %>
                    	<% for(Blog blog : blogs) {
                    		int blogId = blog.getBlogId();
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
	                <div class="blog">
	                    <div class="blog-left">
	                        <div class="pic-name">
	                        	<div class="pfp">
	                        		<img src="images/pfp/<%= blog.getPfp() %>" alt="PFP">
	                        	</div>
	                        	<div class="username"><%= blog.getUsername() %></div>
	                        </div>
	                        <div class="topic">
	                        	<a href="blog_details_servlet?id=<%= blog.getBlogId() %>">
	                        		<%= blog.getTopic() %>
	                        	</a>
	                        </div>
	                        <div class="body">
	                        	<a href="blog_details_servlet?id=<%= blog.getBlogId() %>">
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
	                        <nav class="post-menu" id="post-menu">
                				<a href="delete_blog_servlet?blogId=<%= blogId %>" id="del">Delete</a>
                			</nav>
	                        <% if (blog.getImage() != null) { %>
	                        	<img src="images/post-image/<%= blog.getImage() %>" alt="Image not loaded">
	                        <% } %>
	                    </div>
	               	</div>
	               	<% } %>
                    <% } else { %>
                    	<p>No blogs available.</p>
                <% } %>
                </section>
                <section id="settings" class="section">
                    <form action="update_username_servlet" method="post" class="uname">
                        <label for="change-username">Change your username : </label>
                        <div>
                            <input type="text" class="input upper-input" id="change-username" name="name" placeholder="Enter new username">
                            <input type="submit" class="submit" value="Confirm">
                        </div>
                    </form>
                    <form action="upload_pfp_servlet" method="post" enctype="multipart/form-data" class="pfp">
                        <label for="change-pfp">Change your profile picture : </label>
                        <div>
                            <input type="file" class="pfp-upload" id="change-pfp" name="pfp" accept="image/*" required>
                            <input type="submit" class="submit" value="Confirm" >
                        </div>
                    </form>
                    <form action="update_pwd_servlet" method="post" class="pwd">
                        <label for="change-pwd">Change your password : </label>
                        <div>
                            <input type="password" class="input upper-input" id="change-pwd" name="pwd" placeholder="Enter current password">
                            <input type="password" class="input" id="change-pwd" name="new-pwd" placeholder="Enter new password">
                            <input type="password" class="input" id="change-pwd" name="cnf-new-pwd" placeholder="Re-enter new password">
                            <input type="submit" class="submit" value="Confirm">
                        </div>
                    </form>
                </section>

            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
    <script>
	    /* document.addEventListener('DOMContentLoaded', () => {
	        const more_horiz = document.getElementById('more_horiz');
	        const menu = document.getElementById('post-menu');
	
	        more_horiz.addEventListener('click', () => {
	            menu.classList.toggle('active');
	        });
	    }); */
	    
	    //JavaScript for post dropdown menus
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
    	
        // JavaScript function to toggle sections
        function showSection(sectionId) {
            const sections = document.querySelectorAll('.section');
            sections.forEach(section => {
                section.style.display = 'none';
            });

            const activeSection = document.getElementById(sectionId);
            if (activeSection) {
                activeSection.style.display = 'block';
            }
        }

        // Initial display
        document.addEventListener('DOMContentLoaded', function () {
            showSection('blogs');
        });
    </script>
</body>
</html>