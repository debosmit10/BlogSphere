package com.blogsphere.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class signout_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection con = null;
		
		try {
			if (session != null) {
                // Retrieve the user's email from the session
                String userId = (String) session.getAttribute("id");
                session.invalidate();

                // Update IS_ACTIVE status in the database if userEmail exists
                if (userId != null) {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere", "root", "root");

                    PreparedStatement pst = con.prepareStatement("UPDATE Users SET IS_ACTIVE = FALSE WHERE ID = ?");
                    pst.setString(1, userId);
                    pst.executeUpdate();
                }
            }
			// Redirect user
            response.sendRedirect("index.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}