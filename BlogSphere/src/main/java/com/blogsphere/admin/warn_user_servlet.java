package com.blogsphere.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class warn_user_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
        
        // Write logic to send warning (e.g., update database, send email, etc.)
        System.out.println("Warning sent to user ID: " + userId);

        // Redirect back to the admin dashboard
        response.sendRedirect("admin_panel_servlet");
	}

}
