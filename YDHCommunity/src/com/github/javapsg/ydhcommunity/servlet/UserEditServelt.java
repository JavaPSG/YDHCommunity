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

import com.github.javapsg.ydhcommunity.user.User;
import com.github.javapsg.ydhcommunity.user.UserDataManager;

@WebServlet("/UserEdit")
public class UserEditServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserEditServelt() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		UserDataManager manager = UserDataManager.getInstance();
		String uuid = manager.getAccountData(request.getCookies());
		User user = manager.getUser(request.getCookies());

		if (uuid != null && user != null) {
			if (request.getParameter("name").isEmpty()) {
				out.println("<script> alert('공백은 불가능합니다'); history.back(); </script>");
			} else if (!request.getParameter("name").equals(user.getName()) && manager.getUsers().stream()
					.anyMatch(value -> value.getName().equals(request.getParameter("name")))) {
				out.println("<script> alert('이미 사용된 닉네임입니다'); history.back(); </script>");
			} else if (request.getParameter("password_old").isEmpty()) {
				out.println("<script> alert('정보를 변경하려면 비밀번호를 입력해야합니다'); history.back(); </script>");
			} else {
				if (request.getParameter("password_old").equals(user.getPassword())) {
					if (!request.getParameter("password").isEmpty() && !request.getParameter("password_check").isEmpty()
							&& !request.getParameter("password").equals(user.getPassword())) {
						if (request.getParameter("password").equals(request.getParameter("password_check"))) {
							manager.updateMember(update(user, request));
							out.println(
									"<script> alert('성공적으로 정보를 변경하였습니다!\n비밀번호가 변경되었으므로 보안을 위해 다시 로그인이 필요합니다'); </script>");
							manager.logout(UUID.fromString(uuid));
							response.sendRedirect("/YDHCommunity/content/login.jsp");
						} else {
							out.println("<script> alert('새 비밀번호 확인이 일치하지 않습니다'); history.back(); </script>");
						}
					} else {
						manager.updateMember(update(user, request));
						out.println("<script> alert('성공적으로 정보를 변경하였습니다!'); </script>");
						response.sendRedirect("/YDHCommunity/content/user_view.jsp?user=" + user.getEmail());
					}
				} else {
					out.println("<script> alert('비밀번호가 틀렸습니다'); history.back(); </script>");
				}
			}
		}
	}

	private User update(User user, HttpServletRequest request) {
		user.setIntroduce(request.getParameter("introduce"));
		user.setName(request.getParameter("name"));
		String pw = request.getParameter("password");
		if (!(pw == null || (pw != null && pw.isEmpty()))) {
			user.setPassword(pw);
		}
		return user;
	}
}
