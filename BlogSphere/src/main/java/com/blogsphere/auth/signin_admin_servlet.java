package com.blogsphere.auth;

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

public class signin_admin_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String AEMAIL = request.getParameter("email");
		String APWD = request.getParameter("pwd");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			PreparedStatement pst = con.prepareStatement("SELECT * FROM Admins WHERE AEMAIL = ? AND APWD = ?");
			pst.setString(1, AEMAIL);
			pst.setString(2, APWD);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				session.setAttribute("id", rs.getString("ID"));
				session.setAttribute("aname", rs.getString("ANAME"));
				session.setAttribute("role", "admin");
				/*dispatcher = request.getRequestDispatcher("home_feed_servlet");*/
				response.sendRedirect("home_feed_servlet");
			}else {
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("sign_in_admin.jsp");
			}
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}