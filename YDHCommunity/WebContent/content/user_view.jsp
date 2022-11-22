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
<%
	String userE = (String) request.getParameter("user");
%> 
<meta charset="UTF-8">
<title>양디고 커뮤니티 - 프로필 - <%=userE%>>
</title>
<link rel="stylesheet" href="/YDHCommunity/css/style.css" />
<link rel="stylesheet" href="/YDHCommunity/css/font.css" />
<link rel="stylesheet" href="/YDHCommunity/css/header.css" />
<link rel="stylesheet" href="/YDHCommunity/css/menu.css" />
<link rel="stylesheet" href="/YDHCommunity/css/user_view.css" />
</head>
<%@ include file="/header.jsp"%>
<div class="user-view-box">
	<%
		User data = userManager.getUser(userE);
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 a h시 mm분", new Locale("ko", "KR"));
		String time;
		try {
			time = format.format(data.getLastConnectTime().getTime());
		} catch (Exception e) {
			time = "알 수 없음";
		}
	%>
	<div class="name">
		<h2><%=data.getName()%></h2>
	</div>
	<div class="info">
		<table width="800px" border="1" align="center">
			<tr>
				<th>상태 메세지</th>
				<td><%=data.getIntroduce()%></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><%=data.getEmail()%></td>
			</tr>
			<tr>
				<th>포인트</th>
				<td><%=data.getPoint()%></td>
			</tr>
			<tr>
				<th>마지막 접속 시각</th>
				<td><%=time%></td>
			</tr>
			<tr>
				<th>상태</th>
				<%
					if (!userManager.getOnlines().contains(data.getEmail())) {
						out.println("<td>오프라인</td>");
					} else if (false) {

					} else {
						out.println("<td>로그인됨</td>");
					}
				%>
			</tr>
			<tr>
			</tr>
		</table>
	</div>
</div>
<%@ include file="/footer.jsp"%>