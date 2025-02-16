package com.blogsphere.blog;

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

import com.blogsphere.home.Blog;

public class blog_details_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int blogId = Integer.parseInt(request.getParameter("id"));
		Connection con = null;
		Blog blog = new Blog();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			String query = "SELECT b.ID, b.TOPIC, b.BODY, b.TIME, b.IMG, b.LIKES, u.ID AS uID, u.UNAME, u.PFP FROM Blogs b JOIN Users u ON b.UID = u.ID WHERE b.ID = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, blogId);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
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
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Blog not found");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("blog", blog);
        RequestDispatcher dispatcher = request.getRequestDispatcher("blog.jsp");
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
}
