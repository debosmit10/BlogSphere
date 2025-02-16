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
import java.sql.SQLException;

public class signin_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String UEMAIL = request.getParameter("email");
		String UPWD = request.getParameter("pwd");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			PreparedStatement pst = con.prepareStatement("SELECT * FROM Users WHERE UEMAIL = ? AND UPWD = ?");
			pst.setString(1, UEMAIL);
			pst.setString(2, UPWD);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				session.setAttribute("name", rs.getString("UNAME"));
				session.setAttribute("id", rs.getString("ID"));
				session.setAttribute("pfp", rs.getString("PFP"));
				session.setAttribute("role", "user");
				
				// Setting user status to active
				PreparedStatement updatePst = con.prepareStatement("UPDATE Users SET IS_ACTIVE = TRUE WHERE UEMAIL = ?");
				updatePst.setString(1, UEMAIL);
				updatePst.executeUpdate();
				
				//dispatcher = request.getRequestDispatcher("home.jsp");
				response.sendRedirect("home_feed_servlet");
			}else {
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("sign_in.jsp");
			}
			dispatcher.forward(request, response);
			
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