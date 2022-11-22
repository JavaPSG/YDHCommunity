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

import com.github.javapsg.ydhcommunity.user.User;
import com.github.javapsg.ydhcommunity.user.UserDataManager;

@WebServlet("/Leave")
public class LeaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LeaveServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		UserDataManager manager = UserDataManager.getInstance();
		String uuid = manager.getAccountData(request.getCookies());
		User user = manager.getUser(request.getCookies());
		if (user != null) {
			user.setIntroduce("");
			user.setName("");
			user.setPassword("");
			user.setPoint(0);
			System.out.println("(Account) \"" + user.getEmail() + "\" 계정 탈퇴 완료");
			manager.updateMember(user);
		}
		if (uuid != null) {
			manager.logout(UUID.fromString(uuid));
		}
		System.out.println("?");
		HttpSession session = request.getSession();
		session.removeAttribute("account");
		Cookie cookie = new Cookie("ydhcommunity_account", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		response.sendRedirect("/YDHCommunity/index.jsp");
	}
}
