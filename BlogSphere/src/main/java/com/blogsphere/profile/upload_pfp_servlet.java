package com.blogsphere.profile;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	    maxFileSize = 1024 * 1024 * 10,      // 10MB
	    maxRequestSize = 1024 * 1024 * 50   // 50MB
)

public class upload_pfp_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection con = null;
		String referer = request.getHeader("Referer");
        referer = referer.substring(referer.lastIndexOf("/") + 1);
		
		Part filePart = request.getPart("pfp");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		System.out.println("File name : " + fileName);
		
		// Define where to save the file
        String uploadPath = "C:\\Users\\DEBOSMIT\\eclipse-workspace\\BlogSphere\\src\\main\\webapp\\images\\pfp";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        System.out.println("Upload path : " + uploadPath);
        
        // Save the file
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);
        System.out.println("File path : " + filePath);
        
        // Save file path in the database
        String uid = (String) session.getAttribute("id");
        System.out.println("UID : " + uid);
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogsphere","root","root");
			PreparedStatement pst = con.prepareStatement("UPDATE Users SET PFP = ? WHERE ID = ?");
			pst.setString(1, fileName);
            pst.setString(2, uid);
            pst.executeUpdate();
			
			/* session.setAttribute("pfp", fileName); */
		} catch (Exception e) {
			e.printStackTrace();
		}
        response.sendRedirect(referer);
	}

}
