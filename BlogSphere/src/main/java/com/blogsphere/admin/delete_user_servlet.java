package com.blogsphere.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


public class delete_user_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));

        Connection con = null;
        PreparedStatement pst = null;

        try {
            // Establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere", "root", "root");

            // Delete user query
            String query = "DELETE FROM Users WHERE ID = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            pst.executeUpdate();

            System.out.println("Deleted user ID: " + userId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        response.sendRedirect("admin_panel_servlet");
	}

}
