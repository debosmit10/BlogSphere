package com.blogsphere.profile;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.blogsphere.home.Blog;

public class user_profile_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* HttpSession session = request.getSession(); */
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Blog> blogs = new ArrayList<>();
		String userId = (String) request.getParameter("userId");
		String name = "";
		String pfp = "";
		/* String userId = (String) session.getAttribute("id"); */
	    
	    try {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			String sql = """
					SELECT b.ID, b.UID, u.UNAME, u.PFP, b.TOPIC, b.BODY, b.IMG, b.TIME, b.LIKES
					FROM Blogs b
					JOIN Users u ON b.UID = u.ID
					WHERE b.UID = ?
					ORDER BY b.TIME DESC
					""";
			pst = con.prepareStatement(sql);
			pst.setString(1, userId);
	        rs = pst.executeQuery();
	        
	        while (rs.next()) {
	        	Blog blog = new Blog();
                blog.setBlogId(rs.getInt("ID"));
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
	        
	        sql = "SELECT * FROM Users WHERE ID = ?";
	        pst = con.prepareStatement(sql);
			pst.setString(1, userId);
	        rs = pst.executeQuery();
	        
	        if(rs.next()) {
	        	name = rs.getString("UNAME");
	        	pfp = rs.getString("PFP");
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    //data to be forwarded to jsp
	    request.setAttribute("id", userId);
	    request.setAttribute("name", name);
	    request.setAttribute("pfp", pfp);
        request.setAttribute("blogs", blogs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_profile.jsp");
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
