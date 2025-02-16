package com.blogsphere.home;

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

public class like_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		HttpSession session = request.getSession();
		
		int userId = Integer.parseInt(request.getParameter("userId"));
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        String referer = request.getHeader("Referer");
        referer = referer.substring(referer.lastIndexOf("/") + 1);
        System.out.println("referer : " + referer);
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			// Check if the user has already liked the post
            String checkQuery = "SELECT * FROM Likes WHERE UID = ? AND BID = ?";
            PreparedStatement pst = con.prepareStatement(checkQuery);
            pst.setInt(1, userId);
            pst.setInt(2, blogId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // Unlike: Remove the like
                String unlikeQuery = "DELETE FROM Likes WHERE UID = ? AND BID = ?";
                PreparedStatement unlikeStmt = con.prepareStatement(unlikeQuery);
                unlikeStmt.setInt(1, userId);
                unlikeStmt.setInt(2, blogId);
                unlikeStmt.executeUpdate();

                // Decrease the like count
                String decreaseQuery = "UPDATE Blogs SET LIKES = LIKES - 1 WHERE ID = ?";
                PreparedStatement decreaseStmt = con.prepareStatement(decreaseQuery);
                decreaseStmt.setInt(1, blogId);
                decreaseStmt.executeUpdate();
            } else {
                // Like: Add the like
                String likeQuery = "INSERT INTO Likes (UID, BID) VALUES (?, ?)";
                PreparedStatement likeStmt = con.prepareStatement(likeQuery);
                likeStmt.setInt(1, userId);
                likeStmt.setInt(2, blogId);
                likeStmt.executeUpdate();

                // Increase the like count
                String increaseQuery = "UPDATE Blogs SET LIKES = LIKES + 1 WHERE ID = ?";
                PreparedStatement increaseStmt = con.prepareStatement(increaseQuery);
                increaseStmt.setInt(1, blogId);
                increaseStmt.executeUpdate();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(referer.contains("home_feed_servlet")) {
        	response.sendRedirect(referer);
        } else if (referer.contains("user_profile_servlet")) {
        	response.sendRedirect(referer);
        } else {
        	response.sendRedirect(referer);
        }
	}
}
