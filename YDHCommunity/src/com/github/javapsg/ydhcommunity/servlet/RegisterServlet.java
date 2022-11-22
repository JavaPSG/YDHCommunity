package com.github.javapsg.ydhcommunity.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.javapsg.ydhcommunity.user.User;
import com.github.javapsg.ydhcommunity.user.UserDataManager;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	public RegisterServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		UserDataManager manager = UserDataManager.getInstance();
		int result = 0;

		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("password_check");


		if (!password.equals(passwordCheck)) {
			out.println("<script> alert('비밀번호 확인이 일치하지 않습니다'); history.back(); </script>");
		} else if (manager.getEmails().contains(request.getParameter("email"))) {
			out.println("<script> alert('이미 사용된 이메일입니다'); history.back(); </script>");
		} else if (manager.getUsers().stream()
				.anyMatch(value -> value.getName().equals(request.getParameter("name")))) {
			out.println("<script> alert('이미 사용된 닉네임입니다'); history.back(); </script>");
		} else {
			User user = new User(request.getParameter("name"), "", request.getParameter("email"),
					request.getParameter("password"), false, format.format(new Date()), 0, Arrays.asList());
			result = manager.insertMember(user);
			if (result > 0) {
				HttpSession session = request.getSession();
				session.setAttribute("login" + user.getEmail(), user);
				out.println("<script> alert('회원가입에 성공했습니다'); </script>");
			} else {
				out.println("<script> alert('회원가입에 실패했습니다'); </script>");
			}
			response.sendRedirect("/YDHCommunity/index.jsp");
			;
		}
	}
}
