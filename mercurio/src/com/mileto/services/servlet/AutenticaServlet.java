package com.mileto.services.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AutenticaServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("Estou dentro do servlet");
		
		// Set response content type
		response.setContentType("text/html");

		//PrintWriter out = response.getWriter();
		String title = "Using GET Method to Read Form Data";

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
		
		System.out.println("Estou dentro do servletPOST");
		
		// Set response content type
		response.setContentType("text/html");

		//PrintWriter out = response.getWriter();
		String title = "Using GET Method to Read Form Data";
	}

	
}
