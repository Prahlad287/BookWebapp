package com.idiot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.protocol.Resultset;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowBook extends HttpServlet {
	static String query="select id, BookName,BookEdition,BookPrice from BookList";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=resp.getWriter();
		resp.setContentType("text/html");
		out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		out.println("<marquee><h2 class='text-primary'>Book Data</h2></marquee>");
	
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BookData","Servlet","123456");

			PreparedStatement psmt=con.prepareStatement(query);
			
			out.println("<div class='card' style='margin:auto;width:600px;margin-top:50px;'>");
			ResultSet rs=psmt.executeQuery();
			out.println("<table class='table table-hover table-striped'>");
			out.println("<tr>");
			out.println("<th>BookID</th>");
			out.println("<th>BookName</th>");
			out.println("<th>BookEdition</th>");
			out.println("<th>BookPrice</th>");
			out.println("<th>Edit</th>");
			out.println("<th>Delete</th>");
			out.println("</tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>"+rs.getInt(1)+"</td>");
				out.println("<td>"+rs.getString(2)+"</td>");
				out.println("<td>"+rs.getString(3)+"</td>");
				out.println("<td>"+rs.getFloat(4)+"</td>");
				out.println("<td><a href='Edit?id="+rs.getInt(1)+"'><button class='btn btn-outline-success'>Edit</button></a></th>");
				out.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'><button class='btn btn-outline-danger'>delete</button></a></th>");
			//	out.println("<td><a href='Edit?id="+rs.getInt(1)+"'>edit</a></td>");
				//out.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>delete</a></td>");
			
				out.println("</tr>");
			}
			out.println("</table>");
		
			
		} catch (Exception e) {
			out.println("<h2 class='bg-danger text-light text-center'>"+e.getMessage()+"</h2>");
		}
		out.println("<br>");
		out.println("<a href='Home.html' <button class='btn btn-outline-success d-block'> Home</button></a>");
		
		out.println("</div");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(req, resp);
	}

}
