package com.idiot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditBook extends HttpServlet{
	static String query="select BookName,BookEdition,BookPrice from BookList where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=resp.getWriter();
		resp.setContentType("text/html");
		out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		int id=Integer.parseInt(req.getParameter("id"));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BookData","Servlet","123456");

			PreparedStatement psmt=con.prepareStatement(query);
			psmt.setInt(1, id);
			ResultSet rs=psmt.executeQuery();
			rs.next();
			out.println("<div class='card' style='margin:auto;width:400px;margin-top:100px;'>");
		
			out.println("<form action='Editupdate?id="+id+"'method='post'>");
			out.println("<table class='table table-hover'>");
			out.println("<tr>");
			out.println("<td>BookName</td>");
			out.println("<td><input type='text' name='bookName' value='"+rs.getString(1)+"'</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>BookEdition</td>");
			out.println("<td><input type='text' name='bookEdition' value='"+rs.getString(2)+"'</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>BookPrice</td>");
			out.println("<td><input type='text' name='bookPrice' value='"+rs.getFloat(3)+"'</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td><button type='submit' class='btn btn-outline-success'>Edit</td>");
			out.println("<td><button type='reset' class='btn btn-outline-danger'>Cancel</td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</form>");
		
			
		} catch (Exception e) {
			out.println("<h2 class='bg-danger text-light text-center'>"+e.getMessage()+"</h2>");
		}
		
		out.println("<a href='Home.html' <button class='btn btn-outline-success d-block'> Home</button></a>");
		out.println("<br>");
		out.println("<a href='showlist' <button class='btn btn-outline-secondary d-block'>Back</button></a>");
	
		out.println("</div");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(req, resp);
	}

}
