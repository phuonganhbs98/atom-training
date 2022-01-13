package com.atom.training.utils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowErrorUtils {
	private static final String jspPath = Prop.getPropValue("jspPath");

	public static void showError(HttpServletRequest request, HttpServletResponse response, String message,
			ServletContext context) throws ServletException, IOException {
		request.setAttribute("message", message);
		RequestDispatcher dispatcher = context.getRequestDispatcher(jspPath + "error.jsp");
		dispatcher.forward(request, response);
	}
}
