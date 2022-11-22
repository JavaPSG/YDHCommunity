<%@page import="com.github.javapsg.ydhcommunity.post.Post"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>양디고 커뮤니티</title>
<link rel="stylesheet" href="/YDHCommunity/css/style.css" />
<link rel="stylesheet" href="/YDHCommunity/css/font.css" />
<link rel="stylesheet" href="/YDHCommunity/css/header.css" />
<link rel="stylesheet" href="/YDHCommunity/css/menu.css" />
</head>
<%@ include file="/header.jsp"%>
<div class="main-box">
	<div class="post-box">
		<div class="name">
			<h2>추천 게시물</h2>
		</div>
		<div class="info">
			<%
				List<Post> list1 = new ArrayList(postManager.getPosts());
				SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss", new Locale("ko", "KR"));
				Collections.sort(list1, new Comparator<Post>() {
					@Override
					public int compare(Post p1, Post p2) {

						return ((Integer) p2.getRec().size()).compareTo(((Integer) p1.getRec().size()));
					}
				});
			%>
			<table width="500px" border="1" align="center">
				<tr>
					<th><a>제목</a></th>
					<th><a>작성자</a></th>
					<th><a>추천 수</a></th>
				</tr>
				<%
				if (list1 != null) {
					for (int i = 0; i < list1.size(); i++) {
						Post data = list1.get(i);
				%>
				<tr>
					<td><%=data.getTitle()%></td>
					<td><%=userManager.getUser(data.getWriter()).getName()%></td>
					<td><%=data.getRec().size()%></td>
				</tr>
				<%
					}
					}
				%>
			</table>
		</div>
	</div>
	<div class="user-box">
		<div class="name">
			<h2>유저 순위</h2>
		</div>
		<div class="info">
			<%
				List<User> list2 = new ArrayList(userManager.getUsers());
				Collections.sort(list2, new Comparator<User>() {
					@Override
					public int compare(User u1, User u2) {

						return ((Integer) u2.getPoint()).compareTo((Integer) u1.getPoint());

					}
				});
				String sortType = (String) request.getParameter("sort") == null ? "time"
						: (String) request.getParameter("sort");
			%>
			<table width="500px" border="1" align="center">
				<tr class="type">
					<th><a href="/YDHCommunity/content/user_list.jsp?sort=name">닉네임</a></th>
					<th><a
						href="/YDHCommunity/content/user_list.jsp?sort=introduce">상태
							메세지</a></th>
					<th><a href="/YDHCommunity/content/user_list.jsp?sort=point">포인트</a></th>
				</tr>
				<%
					if (list2 != null) {
						for (int i = 0; i < list2.size(); i++) {
							User data = list2.get(i);
							if (data.getName() != null && !data.getName().isEmpty()) {
				%>
				<tr>
					<td><a class="link"
						href="/YDHCommunity/content/user_view.jsp?user=<%=data.getEmail()%>"><%=data.getName()%></a></td>
					<td><%=data.getIntroduce() == null ? "" : data.getIntroduce()%></td>
					<td><%=data.getPoint()%></td>
				</tr>
				<%
					}
						}
					}
				%>
			</table>
		</div>

	</div>

</div>
<%@ include file="/footer.jsp"%>