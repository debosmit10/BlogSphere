package com.blogsphere.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

public class reset_pwd_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String newPwd = request.getParameter("pwd");
		String confPwd = request.getParameter("confPwd");
		RequestDispatcher dispatcher = null;
		
		if (newPwd != null && confPwd != null && newPwd.equals(confPwd)) {

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Blogsphere", "root", "root");
				PreparedStatement pst = con.prepareStatement("UPDATE Users SET UPWD = ? WHERE UEMAIL = ? ");
				pst.setString(1, newPwd);
				pst.setString(2, (String) session.getAttribute("email"));

				int rowCount = pst.executeUpdate();
				if (rowCount > 0) {
					request.setAttribute("status", "Password Reset Success");
					dispatcher = request.getRequestDispatcher("sign_in.jsp");
				} else {
					request.setAttribute("status", "Password Reset Failed");
					dispatcher = request.getRequestDispatcher("sign_in.jsp");
				}
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
