<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>양디고 커뮤니티 - 회원가입</title>
<link rel="stylesheet" href="/YDHCommunity/css/font.css" />
<link rel="stylesheet" href="/YDHCommunity/css/style.css" />
<link rel="stylesheet" href="/YDHCommunity/css/menu.css" />
<link rel="stylesheet" href="/YDHCommunity/css/header.css" />
<link rel="stylesheet" href="/YDHCommunity/css/login_register.css" />
</head>
<%@ include file="/header.jsp"%> 
<div class="login-box">
	<form action="/YDHCommunity/Register" method="post" border="2" align="center">
		<tr>
			<td>닉네임</td>
			<td> <input type="text" name="name"> </td>
		</tr>
		<tr>
			<td>이메일</td>
			<td> <input type="text" name="email" placeholder="로그인에서 사용됨"> </td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="password" placeholder="로그인에서 사용됨"> </td>
		</tr>
		<tr>
			<td>비밀번호 확인</td>
			<td><input class="end" type="password" name="password_check"> </td>
		</tr>
		<tr> 
			<td colspan="2">
			<input class="button" type="submit" value="  가입  ">&nbsp;&nbsp;
			<input class="button" type="reset" value="  초기화  "></td>
		</tr>
	</form>
</div>
<%@ include file="/footer.jsp"%>

		