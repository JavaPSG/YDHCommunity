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
<title>양디고 커뮤니티 - 프로필 수정>
</title>
<link rel="stylesheet" href="/YDHCommunity/css/style.css" />
<link rel="stylesheet" href="/YDHCommunity/css/font.css" />
<link rel="stylesheet" href="/YDHCommunity/css/header.css" />
<link rel="stylesheet" href="/YDHCommunity/css/menu.css" />
<link rel="stylesheet" href="/YDHCommunity/css/user_edit.css" />
</head>
<%@ include file="/header.jsp"%>
<div class="user-edit-box">
	<%
		User data = userManager.getUser(request.getCookies());
	%> 
	<div class="info">
		<form action="/YDHCommunity/UserEdit" method="post" border="2"
			align="center">
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" placeholder="기존 값이 적용됨"
					value="<%=data.getName()%>"></td>
			</tr>
			<tr>
				<td>상태 메세지</td>
				<td><input type="text" name="introduce" placeholder="현재 기분은?"
					value="<%=data.getIntroduce() == null ? "" : data.getIntroduce()%>"></td>
			</tr>
			<tr>
				<td>기존 비밀번호</td>
				<td><input class="end" type="password" placeholder="정보 변경시 입력 필요!" name="password_old">
				</td>
			</tr>
			<tr>
				<td>새 비밀번호</td>
				<td><input class="end" type="password" placeholder="(선택 사항)" name="password">
				</td>
			</tr>
			<tr>
				<td>새 비밀번호 확인</td>
				<td><input class="end" type="password" name="password_check">
				</td>
			</tr>
			<tr>
				<td colspan="2"><input class="button" type="submit"
					value="  변경 저장  ">&nbsp;&nbsp; <input class="button"
					type="reset" value="  초기화  "><a href="/YDHCommunity/Leave">
				<input class="button-danger" value="  탈퇴  "></a></td>
			</tr>
		</form>
	</div>
</div>
<%@ include file="/footer.jsp"%>