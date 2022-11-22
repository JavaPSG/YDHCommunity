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
import javax.servlet.http.HttpSession;

import com.github.javapsg.ydhcommunity.user.User;
import com.github.javapsg.ydhcommunity.user.UserDataManager;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		UserDataManager manager = UserDataManager.getInstance();
		String accountData = getAccountData(request.getCookies());
		if (accountData != null) {
			manager.logout(UUID.fromString(accountData));
		}
		HttpSession session = request.getSession();
		session.removeAttribute("account");
		Cookie cookie = new Cookie("ydhcommunity_account", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		response.sendRedirect(request.getParameter("url"));
	}

	public String getAccountData(Cookie[] cookies) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie c = cookies[i];
			if (c.getName().equalsIgnoreCase("ydhcommunity_account")) {
				return c.getValue();
			}
		}
		return null;
	}
}
