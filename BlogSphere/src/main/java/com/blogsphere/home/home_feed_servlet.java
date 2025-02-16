package com.blogsphere.home;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class home_feed_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Blog> blogs = new ArrayList<>();
		List<Blog> topBlogs = new ArrayList<>();
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			String query = "SELECT b.*, u.ID AS uID, u.UNAME, u.PFP FROM Blogs b " + "JOIN Users u ON b.UID = u.ID ORDER BY TIME DESC";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("ID"));
                blog.setUserId(rs.getString("uID"));
                blog.setUsername(rs.getString("UNAME"));
                blog.setPfp(rs.getString("PFP"));
                blog.setTopic(rs.getString("TOPIC"));
                blog.setBody(rs.getString("BODY"));
                blog.setImage(rs.getString("IMG"));
                
                Timestamp postTime = rs.getTimestamp("TIME");
                LocalDateTime postDateTime = postTime.toLocalDateTime();
                LocalDateTime currentDateTime = LocalDateTime.now();
                Duration duration = Duration.between(postDateTime, currentDateTime);
                String time = formatDuration(duration);
                blog.setTime(time);
                
                blog.setLikes(rs.getInt("LIKES"));
                blogs.add(blog);
            }
			
			query = "SELECT b.ID, b.TOPIC, b.IMG, b.LIKES FROM Blogs b " +
	                "WHERE b.TIME >= DATE_SUB(NOW(), INTERVAL 30 DAY) " +
	                "ORDER BY b.LIKES DESC LIMIT 3";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Blog blog = new Blog();
				blog.setBlogId(rs.getInt("ID"));
                blog.setTopic(rs.getString("TOPIC"));
                blog.setImage(rs.getString("IMG"));
				/* blog.setLikes(rs.getInt("like_count")); */
                topBlogs.add(blog);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("blogs", blogs);
		request.setAttribute("topBlogs", topBlogs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        dispatcher.forward(request, response);
	}
	
	public String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + "s ago";
        } else if (seconds < 3600) {
            return (seconds / 60) + "m ago";
        } else if (seconds < 86400) {
            return (seconds / 3600) + "h ago";
        } else {
            return (seconds / 86400) + "d ago";
        }
    }
	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int blogId = Integer.parseInt(request.getParameter("blogId"));

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere", "root", "password")) {
            String sql = "UPDATE Blogs SET LIKES = LIKES + 1 WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, blogId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect back to the feed page
        response.sendRedirect("feed");
	}*/

}
