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
<title>양디고 커뮤니티 - 게시물 작성
</title>
<link rel="stylesheet" href="/YDHCommunity/css/style.css" />
<link rel="stylesheet" href="/YDHCommunity/css/font.css" />
<link rel="stylesheet" href="/YDHCommunity/css/header.css" />
<link rel="stylesheet" href="/YDHCommunity/css/menu.css" />
<link rel="stylesheet" href="/YDHCommunity/css/post_write.css" />
</head>
<%@ include file="/header.jsp"%> 
<div class="post-write-box">
	<%
		User data = userManager.getUser(request.getCookies());
	%> 
	<div class="info">
		<form action="/YDHCommunity/PostWrite" method="post" border="2"
			align="center">
			<tr>
				<td>제목</td>
				<td><input type="text" name="title"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><input type="text" name="content"></td>
			</tr>
			<tr>
				<td colspan="2"><input class="button" type="submit"
					value="  게시  "></td>
			</tr>
		</form>
	</div>
</div>
<%@ include file="/footer.jsp"%>