package com.blogsphere.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class admin_panel_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public class User {
    	private int id;
    	private String uname;
    	private String uemail;
    	
    	public User(int id, String uname, String uemail) {
    		this.id = id;
    		this.uname = uname;
    		this.uemail = uemail;
    	}
    	
    	public int getId() {
            return id;
        }

        public String getUname() {
            return uname;
        }

        public String getUemail() {
            return uemail;
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		// Variables to store the statistics
        int totalUsers = 0;
        int activeUsers = 0;
        ArrayList<User> userList = new ArrayList<>();
        
       try {
    	   Class.forName("com.mysql.cj.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere", "root", "root");
           
        // Query for total users and active users
           String countQuery = "SELECT COUNT(*) FROM Users";
           pst = con.prepareStatement(countQuery);
           rs = pst.executeQuery();
           if (rs.next()) {
               totalUsers = rs.getInt(1);  // Total users count
           }

           String activeQuery = "SELECT COUNT(*) FROM Users WHERE IS_ACTIVE = TRUE";
           pst = con.prepareStatement(activeQuery);
           rs = pst.executeQuery();
           if (rs.next()) {
               activeUsers = rs.getInt(1);  // Active users count
           }
           
        // Query to fetch user data (ID, UNAME, UEMAIL)
           String userQuery = "SELECT ID, UNAME, UEMAIL FROM Users";
           pst = con.prepareStatement(userQuery);
           rs = pst.executeQuery();

           while (rs.next()) {
               userList.add(new User(rs.getInt("ID"), rs.getString("UNAME"), rs.getString("UEMAIL")));
           }
           
        // Set attributes to forward data to JSP
           request.setAttribute("totalUsers", totalUsers);
           request.setAttribute("activeUsers", activeUsers);
           request.setAttribute("userList", userList);

           // Forward to the JSP page
           RequestDispatcher dispatcher = request.getRequestDispatcher("admin_dashboard.jsp");
           dispatcher.forward(request, response);
           
       } catch (Exception e) {
    	   e.printStackTrace();
       } finally {
    	   try {
               if (rs != null) rs.close();
               if (pst != null) pst.close();
               if (con != null) con.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
	}
}