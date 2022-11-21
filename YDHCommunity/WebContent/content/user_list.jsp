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
<title>양디고 커뮤니티 - 로그인</title>
<link rel="stylesheet" href="/YDHCommunity/css/font.css" />
<link rel="stylesheet" href="/YDHCommunity/css/style.css" />
<link rel="stylesheet" href="/YDHCommunity/css/menu.css" />
<link rel="stylesheet" href="/YDHCommunity/css/header.css" />
<link rel="stylesheet" href="/YDHCommunity/css/login_register.css" />
</head> 
<%@ include file="/header.jsp"%>
<div class="user-list-box">
	<%
		List<User> list = new ArrayList(manager.getUsers());
		Collections.sort(list);
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 a h시 mm분", new Locale("ko", "KR"));
	%>
	<table width="800px" border="1" align="center">
		<tr>
			<th>닉네임</th>
			<th>이메일</th>
			<th>마지막 접속 시각</th>
			<th>상태</th>
		</tr>
		<%
			if (list != null) {
				for (User data : list) {
					String time; 
					try {
						time = format.format(data.getLastConnectTime().getTime());
					} catch (Exception e) {
						time = "알 수 없음";
					}
		%>
		<tr>
			<td><%=data.getName()%></td>
			<td><%=data.getEmail()%></td>
			<td><%=time%></td>
			<%
				if (!manager.getOnlines().contains(data.getEmail())) {
							out.println("<td>오프라인</td>");
						} else {
							out.println("<td>로그인됨</td>");
						}
			%>
		</tr>
		<%
			}
			}
		%>
	</table>
</div>
<%@ include file="/footer.jsp"%>