<%@page import="com.github.javapsg.post.Post"%>
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
<title>양디고 커뮤니티 - 사용자 목록</title>
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
		Collections.sort(list);
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss", new Locale("ko", "KR"));
	%>
	<table width="800px" border="1" align="center">
		<tr>
			<th>제목</th>
			<th>작성자</th>
			<th>추천 수</th>
			<th>작성 시각</th>
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