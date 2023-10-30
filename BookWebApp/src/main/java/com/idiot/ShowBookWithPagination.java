package com.idiot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowBookWithPagination extends HttpServlet {
	static String query = "SELECT id, BookName, BookEdition, BookPrice FROM BookList LIMIT ?, ?";
	static int recordsPerPage = 4; // Number of records to display per page
	
	// Connection method
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BookData", "Servlet", "123456");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the current page number from the request parameter
		int currentPage = 1; // Default to page 1 if not provided

		String pageParam = req.getParameter("page");
		if (pageParam != null && !pageParam.isEmpty()) {
			currentPage = Integer.parseInt(pageParam);
		}

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		out.println("<marquee><h2 class='text-primary'>Book Data</h2></marquee>");

		String queryString = "select * from BookList";
		PreparedStatement smt;
		try {
			smt = getConnection().prepareStatement(queryString);
			// Calculate total number of records
			ResultSet rSet = smt.executeQuery();
			int totalrecord = 0;
			while (rSet.next()) {
				totalrecord++;
			}
			// Calculate the start based on the current page and recordsPerPage
			int start = (currentPage - 1) * recordsPerPage;

			PreparedStatement psmt = getConnection().prepareStatement(query);
			psmt.setInt(1, start);
			psmt.setInt(2, recordsPerPage);

			out.println("<div class='card' style='margin:auto;width:600px;margin-top:50px;'>");
			ResultSet rs = psmt.executeQuery();
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
				out.println("<td>" + rs.getInt(1) + "</td>");
				out.println("<td>" + rs.getString(2) + "</td>");
				out.println("<td>" + rs.getString(3) + "</td>");
				out.println("<td>" + rs.getFloat(4) + "</td>");
				out.println("<td><a href='Edit?id=" + rs.getInt(1)
						+ "'><button class='btn btn-outline-success'>Edit</button></a></th>");
				out.println("<td><a href='deleteurl?id=" + rs.getInt(1)
						+ "'><button class='btn btn-outline-danger'>delete</button></a></th>");
				out.println("</tr>");
			}
			out.println("</table>");
			// Calculate total number of pages
			int totalPages = totalrecord / recordsPerPage;
			int remiander = totalrecord % recordsPerPage;
			if (remiander > 0) {
				totalPages++;
			}
			// Display pagination links
			out.println("<ul class='pagination'>");
			for (int i = 1; i <= totalPages; i++) {
				out.println("<li><a href='showlist?page=" + i + "'>" + i + "&nbsp &nbsp;</a></li>");
			}
			out.println("</ul>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
		}

		out.println("<br>");
		out.println("<a href='Home.html' <button class='btn btn-outline-success d-block'> Home</button></a>");
		out.println("</div");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
