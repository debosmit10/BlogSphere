package com.blogsphere.blog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class delete_blog_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int blogId = Integer.parseInt(request.getParameter("blogId"));
		System.out.println("blogId : " + blogId);
		String referer = request.getHeader("Referer");
        referer = referer.substring(referer.lastIndexOf("/") + 1);
		
		Connection con = null;
        PreparedStatement pst = null;
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere", "root", "root");

            // Delete likes of that blog from Likes table
            String query = "DELETE FROM Likes WHERE BID = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, blogId);
            pst.executeUpdate();
            
            // Delete blog from Blogs table
            query = "DELETE FROM Blogs WHERE ID = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, blogId);
            pst.executeUpdate();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if(referer.contains("user_profile_servlet")) {
        	response.sendRedirect(referer);
        } else {
        	response.sendRedirect("home_feed_servlet");
        }
	}
}