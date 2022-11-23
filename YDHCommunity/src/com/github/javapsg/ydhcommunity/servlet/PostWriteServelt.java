package com.github.javapsg.ydhcommunity.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.javapsg.ydhcommunity.post.Post;
import com.github.javapsg.ydhcommunity.post.PostDataManager;
import com.github.javapsg.ydhcommunity.user.User;
import com.github.javapsg.ydhcommunity.user.UserDataManager;

@WebServlet("/PostWrite")
public class PostWriteServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	public PostWriteServelt() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		UserDataManager manager = UserDataManager.getInstance();
		PostDataManager manager2 = PostDataManager.getInstance();
		String uuidstr = manager.getAccountData(request.getCookies());
		User user = manager.getUser(request.getCookies());

		if (uuidstr != null && user != null) {
			if (request.getParameter("title").isEmpty()) {
				out.println("<script> alert('제목을 입력해주세요'); history.back(); </script>");
			} else {
				UUID uuid = manager2.insertPost(update(user, request));
				manager.updateMember(update(user, uuid, request));
				response.sendRedirect("/YDHCommunity/content/post_list.jsp?sort=time");
			}
		}
	}

	private Post update(User user, HttpServletRequest request) {
		Post post = new Post(user.getEmail(), request.getParameter("title"), request.getParameter("content"), Arrays.asList(), format.format(new Date()));
		return post;
	}
	
	private User update(User user, UUID uuid, HttpServletRequest request) {
		user.addPost(uuid);
		return user;
	}
}
