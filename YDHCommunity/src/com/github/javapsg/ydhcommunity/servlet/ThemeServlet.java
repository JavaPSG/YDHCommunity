package com.github.javapsg.ydhcommunity.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.github.javapsg.ydhcommunity.user.User;
import com.github.javapsg.ydhcommunity.user.UserDataManager;

@WebServlet("/Theme")
public class ThemeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThemeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Cookie cookie = null;

		UserDataManager manager = UserDataManager.getInstance();
		User user = manager.getUser(request.getCookies());
		if (request.getParameter("theme").equals("dark")) {
			if (user != null) {
				user.setWhiteTheme(false);
				manager.updateMember(user);
			} else {
				cookie = new Cookie("ydhcommunity_theme", "white");
				cookie.setMaxAge(3600 * 24 * 365);
				response.addCookie(cookie);
			}
		} else {
			if (user != null) {
				user.setWhiteTheme(true);
				manager.updateMember(user);
			} else {
				cookie = new Cookie("ydhcommunity_theme", "dark");
				cookie.setMaxAge(3600 * 24 * 365);
				response.addCookie(cookie);
			}
		}
		response.sendRedirect(request.getParameter("url")
				.replace("user_list.jsp", "user_list.jsp?sort=" + request.getParameter("sort"))
				.replace("user_view.jsp", "user_view.jsp?user=" + request.getParameter("user")));
	}
}
