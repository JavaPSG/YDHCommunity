<%@page import="java.util.Comparator"%>
<%@page import="com.github.javapsg.ydhcommunity.post.Post"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>양디고 커뮤니티 - 게시물 목록</title>
<link rel="stylesheet" href="/YDHCommunity/css/style.css" />
<link rel="stylesheet" href="/YDHCommunity/css/font.css" />
<link rel="stylesheet" href="/YDHCommunity/css/header.css" />
<link rel="stylesheet" href="/YDHCommunity/css/menu.css" />
<link rel="stylesheet" href="/YDHCommunity/css/post_list.css" />
</head>
<%@ include file="/header.jsp"%>
<div class="post-list-box">
	<%
		List<Post> list = new ArrayList(postManager.getPosts());
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss", new Locale("ko", "KR"));
		Collections.sort(list, new Comparator<Post>(){
					@Override
					public int compare(Post p1, Post p2) {
						switch ((String)request.getParameter("sort")){
						case "title": {
							return p1.getTitle().compareTo(p2.getTitle());
						}
						case "writer": {
							UserDataManager udm = UserDataManager.getInstance();
							return udm.getUser(p1.getWriter()).getName().compareTo(udm.getUser(p2.getWriter()).getName());
						}
						case "rec": {
							return ((Integer)p2.getRec().size()).compareTo(((Integer)p1.getRec().size()));
						}
						default: {
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("ko", "KR"));
							Long v1 = Long.valueOf(format.format(p1.getWriteTime().getTime()));
							Long v2 = Long.valueOf(format.format(p2.getWriteTime().getTime()));
							return v2.compareTo(v1);
						}
					}
				}
		});
		String sortType = (String)request.getParameter("sort") == null ? "time" : (String)request.getParameter("sort");
	%>
	<table width="800px" border="1" align="center">
		<tr>
			<th><a class="link <%= sortType.equals("title") %>"
				href="/YDHCommunity/content/post_list.jsp?sort=title">제목</a></th>
			<th><a class="link <%= sortType.equals("writer") %>"
				href="/YDHCommunity/content/post_list.jsp?sort=writer">작성자</a></th>
			<th><a class="link <%= sortType.equals("rec") %>"
				href="/YDHCommunity/content/post_list.jsp?sort=rec">추천 수</a></th>
			<th><a class="link <%= sortType.equals("time") %>" 
				href="/YDHCommunity/content/post_list.jsp?sort=time">작성 시각</a></th>
		</tr> 
		<%
			if (list != null) {
				for (Post data : list) {
					String time; 
					try {
						time = format.format(data.getWriteTime().getTime());
					} catch (Exception e) {
						time = "알 수 없음";
					}
		%>
		<tr>
			<td><%=data.getTitle()%></td>
			<td><%=userManager.getUser(data.getWriter()).getName()%></td>
			<td><%=data.getRec().size()%></td>
			<td><%=time%></td>
		</tr>
		<%
			}
			}
		%>
	</table>
</div>
<%@ include file="/footer.jsp"%>