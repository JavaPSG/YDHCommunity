package com.github.javapsg.ydhcommunity.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String uuid = manager.getAccountData(request.getCookies());
		if (uuid != null) {
			manager.logout(UUID.fromString(uuid));
		}
		HttpSession session = request.getSession();
		session.removeAttribute("account");
		Cookie cookie = new Cookie("ydhcommunity_account", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		if (request.getParameter("sort") == null && request.getParameter("user") == null) {
			response.sendRedirect("/YDHCommunity/index.jsp");
		} else {
			response.sendRedirect(request.getParameter("url")
					.replace("user_list.jsp", "user_list.jsp?sort=" + request.getParameter("sort"))
					.replace("user_view.jsp", "user_view.jsp?user=" + request.getParameter("user")));
		}
	}
}
