package com.blogsphere.profile;

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

public class update_pwd_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection con = null;
		String pwd = request.getParameter("pwd");
		String newPwd = request.getParameter("new-pwd");
		String cnfNewPwd = request.getParameter("cnf-new-pwd");
		String id = (String) session.getAttribute("id");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			PreparedStatement pst = con.prepareStatement("SELECT * FROM Users WHERE ID = ? AND UPWD = ?");
			pst.setString(1, id);
			pst.setString(2, pwd);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next() && newPwd.equals(cnfNewPwd)) {
				pst.close();
				pst = con.prepareStatement("UPDATE Users SET UPWD = ? WHERE ID = ?");
				pst.setString(1, newPwd);
				pst.setString(2, id);
				int isUpdated = pst.executeUpdate();
				if(isUpdated > 0) {
					System.out.println("PWD Updated");
				} else {
					System.out.println("PWD not updated");
				}
			} else {
				System.out.println("Wrong current PWD / new PWDs do not match");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("user_profile_servlet?userId=" + session.getAttribute("id"));
	}
}
