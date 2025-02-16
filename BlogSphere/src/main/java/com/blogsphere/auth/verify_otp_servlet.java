package com.blogsphere.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class verify_otp_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int value = Integer.parseInt(request.getParameter("otp"));
		HttpSession session = request.getSession();
		int otp = (int)session.getAttribute("otp");
		
		RequestDispatcher dispatcher = null;
		
		if(value==otp) {
			request.setAttribute("email", request.getParameter("email"));
			request.setAttribute("status", "success");
			dispatcher = request.getRequestDispatcher("reset_pwd.jsp");
			dispatcher.forward(request, response);
		}
		else {
			request.setAttribute("message", "Wrong OTP");
			dispatcher = request.getRequestDispatcher("verify_otp.jsp");
			dispatcher.forward(request, response);
		}
	}
}