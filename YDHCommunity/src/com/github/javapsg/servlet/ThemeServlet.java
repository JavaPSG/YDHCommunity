package com.github.javapsg.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Theme
 */
@WebServlet("/Theme")
public class ThemeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ThemeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Cookie cookie = null;

		if (request.getParameter("theme").equals("dark")) { 
			System.out.println("C");
			cookie = new Cookie("ydhcommunity_theme", "white");
		} else {
			System.out.println("D");
			cookie = new Cookie("ydhcommunity_theme", "dark");
		}
		cookie.setMaxAge(3600 * 24 * 365);
		response.addCookie(cookie);
		response.getWriter().print("<script> history.back() </script>");
	}

}
