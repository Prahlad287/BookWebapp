package com.idiot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteBook extends HttpServlet{

	static String query="update BookList set BookName=?,BookEdition=?,BookPrice=? where id=?";
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			// TODO Auto-generated method stub
			PrintWriter out=resp.getWriter();
			resp.setContentType("text/html");
			out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
			int id=Integer.parseInt(req.getParameter("id"));
			String bookName=req.getParameter("bookName");
			String bookEdition=req.getParameter("bookEdition");
			Float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BookData","Servlet","123456");

				PreparedStatement psmt=con.prepareStatement(query);
				psmt.setString(1, bookName);
				psmt.setString(2, bookEdition);
				psmt.setFloat(3, bookPrice);
				psmt.setInt(4, id);
				int count=psmt.executeUpdate();
				out.println("<div class='card' style='margin:auto;width:300px;margin-top:100px;'>");
				if (count>0) {
					out.println("<h2 class='bg-success text-light text-center'>Record is successfully updated</h2>");
					
				}else {
					out.println("<h2 class='bg-danger text-light text-center'>Record is not updated</h2>");
				}
				
			} catch (Exception e) {
				out.println("<h2 class='bg-danger text-light text-center'>"+e.getMessage()+"</h2>");
			}
			out.println("<br>");
			out.println("<a href='Home.html' <button class='btn btn-outline-success d-block'> Home</button></a>");
			out.println("<br>");
			out.println("<a href='showlist' <button class='btn btn-outline-success d-block'> Show List</button></a>");
			out.println("</div");
		}
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
		}
}
