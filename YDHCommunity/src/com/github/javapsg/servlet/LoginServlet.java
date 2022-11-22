package com.github.javapsg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.javapsg.user.User;
import com.github.javapsg.user.UserDataManager;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String email, password;
		User user = null;
		PrintWriter out = response.getWriter();

		UserDataManager manager = UserDataManager.getInstance();
		email = request.getParameter("email");
		password = request.getParameter("password");
		user = manager.getUser(email);

		if (user == null || !password.equals(user.getPassword())) {
			out.println("<script> alert('이메일 또는 비밀번호가 틀렸습니다'); history.back(); </script>");
		} else {
			HttpSession session = request.getSession();
			UUID uuid = manager.login(user.getEmail());
			Cookie cookie = new Cookie("ydhcommunity_account", uuid.toString());
			cookie.setMaxAge(3600 * 24 * 365);
			response.addCookie(cookie);
			session.setAttribute("account-" + uuid.toString(), 0);
			response.sendRedirect("/YDHCommunity/index.jsp");
		}
	}
}
