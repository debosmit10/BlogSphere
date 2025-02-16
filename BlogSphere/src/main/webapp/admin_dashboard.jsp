<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.blogsphere.admin.admin_panel_servlet.User"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/admin_dashboard.css?version-1">
    <link rel="stylesheet" href="css/header.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="dashboard">
        <!-- Sidebar -->
        <div class="sidebar">
            <h3>Admin Dashboard</h3>
            <ul>
                <li><button class="button" onclick="showSection('overview')">Overview</button></li>
                <li><button class="button" onclick="showSection('user-management')">User Management</button></li>
                <li><button class="button" onclick="showSection('user-activity')">User Activity</button></li>
                <li><button class="button" onclick="showSection('reports-received')">Reports Received</button></li>
                <li><a href="home_feed_servlet"><button class="button">Home</button></a></li>
            </ul>
        </div>
        <!-- Main Content -->
        <div class="main-content">
            <div class="head">
                <h1>Welcome, <%= session.getAttribute("aname") %></h1>
                <p>Manage your platform effectively</p>
            </div>

            <section id="overview" class="overview section">
                <div class="card">
                    <h3>Total Users</h3>
                    <p><%= request.getAttribute("totalUsers") %></p>
                </div>
                <div class="card">
                    <h3>Active Users</h3>
                    <p><%= request.getAttribute("activeUsers") %></p>
                </div>
                <div class="card">
                    <h3>Warnings Issued</h3>
                    <p>0</p>
                </div>
            </section>

            <section id="user-management" class="user-management section">
                <h2>User Management</h2>
                <table>
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<% 
                			ArrayList<User> userList = (ArrayList<User>) request.getAttribute("userList");
                			for (User user : userList) { 
            			%>
                        <tr>
                            <td><%= user.getId() %></td>
                            <td class="user-link">
                            	<a href="user_profile_servlet?userId=<%= user.getId() %>">
                            		<%= user.getUname() %>
                            	</a>
                            </td>
                            <td><%= user.getUemail() %></td>
                            <td>
                                <form action="warn_user_servlet" method="post" style="display:inline;">
                        			<input type="hidden" name="userId" value="<%= user.getId() %>">
                        			<button type="submit" class="warn-btn">Warn</button>
                    			</form>
                                <form action="delete_user_servlet" method="post" style="display:inline;">
                        			<input type="hidden" name="userId" value="<%= user.getId() %>">
                        			<button type="submit" class="delete-btn">Delete</button>
                    			</form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </section>

            <section id="user-activity" class="user-activity section">
                <h2>User Activity</h2>
                <p>View and manage user activities here.</p>
            </section>

            <section id="reports-received" class="reports-received section">
                <h2>Reports Received</h2>
                <p>View the reports received from users here.</p>
            </section>
        </div>
    </div>
    <script>
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

        // Initially display the Overview section
        document.addEventListener('DOMContentLoaded', function () {
            showSection('overview');
        });
    </script>
</body>
</html>