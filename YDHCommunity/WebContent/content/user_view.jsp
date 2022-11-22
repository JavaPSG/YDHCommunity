<%@page import="com.github.javapsg.ydhcommunity.user.User"%>
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
<title>양디고 커뮤니티 - 프로필 - <%=userE%>
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
	<div>
		<div class="name">
			<h2><%=data.getName()%> 님의 프로필</h2> 
		</div> 
		<div class="info">
			<table width="700px" border="1" align="center">
				<tr>
					<th>상태 메세지</th>
					<td><%=data.getIntroduce() == null ? "" : data.getIntroduce()%></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><%=data.getEmail()%>@y-y.hs.kr</td>
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
						} else {
							out.println("<td>온라인</td>");
						}
					%>
				</tr>
			</table>
		</div>
		<div class="under">
			<%
				User thisUser = userManager.getUser(request.getCookies());
				if (thisUser != null && thisUser.getName() != null && !thisUser.getName().isEmpty() && thisUser.getEmail().equals(data.getEmail())) {
			%>
			<form action="/YDHCommunity/content/user_edit.jsp" method="post" border="1"
				align="center">
				<input class="button" type="submit" value="  수정  ">
			</form>
			<%
				}
			%>
		</div>
	</div>

</div>
<%@ include file="/footer.jsp"%>