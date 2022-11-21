package com.github.javapsg.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.javapsg.user.User;
import com.github.javapsg.user.UserDataManager;

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
		
		String accountData = getAccountData(request.getCookies());
		Cookie cookie = null;

		UserDataManager manager = UserDataManager.getInstance();
		if (request.getParameter("theme").equals("dark")) {
			if (accountData != null) {
				User user = manager.getUser(manager.getEmail(UUID.fromString(accountData)));
				if (user != null) {
					user.setWhiteTheme(false);
					manager.updateMember(user);
				}
			} else {
				cookie = new Cookie("ydhcommunity_theme", "white");
				cookie.setMaxAge(3600 * 24 * 365);
				response.addCookie(cookie);
			}
		} else {
			if (accountData != null) {
				User user = manager.getUser(manager.getEmail(UUID.fromString(accountData)));
				if (user != null) {
					user.setWhiteTheme(true);
					manager.updateMember(user);
				}
			} else {
				cookie = new Cookie("ydhcommunity_theme", "dark");
				cookie.setMaxAge(3600 * 24 * 365);
				response.addCookie(cookie);
			}
		}
		response.sendRedirect(request.getParameter("url"));
	}

	public String getAccountData(Cookie[] cookies) {
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equalsIgnoreCase("ydhcommunity_account")) {
					return c.getValue();
				}
			}
		}
		return null;
	}
}
