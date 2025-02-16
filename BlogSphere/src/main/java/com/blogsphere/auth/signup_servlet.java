package com.blogsphere.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

public class signup_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String UNAME = request.getParameter("name");
		String UEMAIL = request.getParameter("email");
		String UPWD = request.getParameter("pwd");
		String cnfPwd = request.getParameter("confirm_pwd");
		if (UPWD.equals(cnfPwd)) {
		RequestDispatcher dispatcher = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			PreparedStatement pst = con.prepareStatement("INSERT INTO Users (UNAME,UEMAIL,UPWD) VALUES (?,?,?)");
			pst.setString(1, UNAME);
			pst.setString(2, UEMAIL);
			pst.setString(3, UPWD);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("sign_in.jsp");
			if(rowCount > 0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request,  response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		} else {
			response.sendRedirect("sign_up.jsp");
		}
	}

}