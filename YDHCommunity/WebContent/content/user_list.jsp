<%@page import="java.util.Comparator"%>
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
<link rel="stylesheet" href="/YDHCommunity/css/user_list.css" />
</head>
<%@ include file="/header.jsp"%> 
<div class="user-list-box">
	<%
		List<User> list = new ArrayList(userManager.getUsers());
		Collections.sort(list, new Comparator<User>() {
			@Override
			public int compare(User u1, User u2) {
				switch ((String) request.getParameter("sort")) {
				case "name": {
					return (u1.getName() == null ? "" : u1.getName())
							.compareTo(u2.getName() == null ? "" : u2.getName());
				}
				case "email": {
					return u1.getEmail().compareTo(u2.getEmail());
				}
				case "introduce": {
					return (u1.getIntroduce() == null ? "" : u1.getIntroduce())
							.compareTo(u2.getIntroduce() == null ? "" : u2.getIntroduce());
				}
				case "point": {
					return ((Integer) u2.getPoint()).compareTo((Integer) u1.getPoint());
				}
				default: {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("ko", "KR"));
					Long v1 = Long.valueOf(format.format(u1.getLastConnectTime().getTime()));
					Long v2 = Long.valueOf(format.format(u2.getLastConnectTime().getTime()));
					return v2.compareTo(v1);
				}
				}
			}
		});
		String sortType = (String)request.getParameter("sort") == null ? "time" : (String)request.getParameter("sort");
	%>
	<table width="800px" border="1" align="center">
		<tr class="type">
			<th><a class="link <%= sortType.equals("name") %>"
				href="/YDHCommunity/content/user_list.jsp?sort=name">닉네임</a></th>
			<th><a class="link <%= sortType.equals("introduce") %>"
				href="/YDHCommunity/content/user_list.jsp?sort=introduce">상태 메세지</a></th>
			<th><a class="link <%= sortType.equals("point") %>"
				href="/YDHCommunity/content/user_list.jsp?sort=point">포인트</a></th>
			<th><a class="link <%= sortType.equals("time") %>"
				href="/YDHCommunity/content/user_list.jsp?sort=time">마지막 접속 시각</a></th>
			<th>상태</th>
		</tr>
		<%
			SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 a h시 mm분", new Locale("ko", "KR"));
			if (list != null) {
				for (User data : list) {
					if (data.getName() != null && !data.getName().isEmpty()) {
						String time;
						try {
							time = format.format(data.getLastConnectTime().getTime());
						} catch (Exception e) {
							time = "알 수 없음";
						}
		%>
		<tr>
			<td><a class="link"
				href="/YDHCommunity/content/user_view.jsp?user=<%=data.getEmail()%>"><%=data.getName()%></a></td>
			<td><%=data.getIntroduce() == null ? "" : data.getIntroduce()%></td>
			<td><%=data.getPoint()%></td>
			<td><%=time%></td>
			<%
				if (!userManager.getOnlines().contains(data.getEmail())) {
								out.println("<td>오프라인</td>");
							} else {
								out.println("<td>온라인</td>");
							}
			%>
		</tr>
		<%
			}
				}
			}
		%>
	</table>
</div>
<%@ include file="/footer.jsp"%>