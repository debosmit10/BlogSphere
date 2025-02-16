<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String role = (String) session.getAttribute("role"); %>
<% String userId = (String) session.getAttribute("id"); %>
<header class="header">
	<div class="logo"><a href="index.jsp" id="logo-link">BlogSphere</a></div>
    <nav class="menu" id="menu">
    	<% if (session.getAttribute("id") != null) { %>
	    	<% if("admin".equals(role)) { %>
	        <a href="admin_panel_servlet">Dashboard</a>
	        <% } else { %>
	        <a href="user_profile_servlet?userId=<%= userId %>">My Profile</a>
	        <% } %>
        <% } %>
        
        <a href="home_feed_servlet">Home</a>
        <a href="aboutUs.jsp">About Us</a>
        <a href="contact.jsp">Contact</a>
        
        <% if (session.getAttribute("id") != null) { %>
        	<a href="signout_servlet">Logout</a>
        <% } %>
    </nav>
  	<div class="user">
  		<% if (session.getAttribute("id") != null) { %>
			<a href="user_profile_servlet?userId=<%= userId %>">
				<% if("user".equals(role)) { %>
			    <img src="images/pfp/<%= session.getAttribute("pfp") %>" alt="PFP" class="profile-pic">
			    <% } %>
			    <%= session.getAttribute(("user".equals(role)) ? "name" : "aname") %>
			</a>
		<% } %>
   	</div>
    <button class="hamburger" id="hamburger" aria-label="Menu">
        <span class="line"></span>
        <span class="line"></span>
        <span class="line"></span>
    </button>
</header>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const hamburger = document.getElementById('hamburger');
        const menu = document.getElementById('menu');

      	//Toggle visibility when button is clicked
        hamburger.addEventListener('click', () => {
        	event.stopPropagation();
            hamburger.classList.toggle('active');
            menu.classList.toggle('active');
        });
        
      	//Close dropdown menu when clicking outside
        document.addEventListener('click', () => {
        	const isClickInsideMenu = menu.contains(event.target);
            const isClickInsideHamburger = hamburger.contains(event.target);
            if (!isClickInsideMenu && !isClickInsideHamburger) {
                if (menu.classList.contains('active')) {
                    menu.classList.remove('active');
                    hamburger.classList.remove('active');
                }
            }
    	});
    });
</script>