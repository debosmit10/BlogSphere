package com.blogsphere.auth;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
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
import java.util.Properties;
import java.util.Random;

public class forgot_pwd_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		RequestDispatcher dispatcher = null;
		HttpSession mySession = request.getSession();
		int otp = 0;
		
		if(email != null || !email.equals("")) {
			//sending OTP
			Random rand = new Random();
			otp = rand.nextInt(2366761);
			
			String to = email; //receiver changed accordingly
			//getting session object
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "jakarta.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			Session session = Session.getDefaultInstance(props, new jakarta.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("blogsphere2024@gmail.com", "kgulohqvmfycoclm"); //sender email & app pwd
				}
			});
			
			//compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email)); //receiver changed accordingly
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("BlogSphere New Password OTP");
				message.setText("Your OTP is: " + otp);
				//send message
				Transport.send(message);
				System.out.println("message sent successfully");
				
			} catch (Exception e) {
				throw new RuntimeException(e);
				
			}
			dispatcher = request.getRequestDispatcher("verify_otp.jsp");
			request.setAttribute("message", "OTP is sent to your email id");
			//request.setAttribute("connection", con);
			mySession.setAttribute("otp", otp);
			mySession.setAttribute("email", email);
			dispatcher.forward(request, response);
			//request.setAttribute("status", "success");
			
		}
		
	}
}