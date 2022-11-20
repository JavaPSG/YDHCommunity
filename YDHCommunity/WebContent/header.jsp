<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	UserDataManager.getInstance().init();
	Theme theme = ThemeUtil.getTheme(request.getCookies());
	System.out.println(theme.getIcon() + "/" + theme.getId() + "/" + theme.getName());
	String result = theme.getId().split("-")[1];
%>
<body class="<%=theme.getThemeClass()%>">
	<div id="wrapper">
		<header id="header" class="alt">
			<h1>
				<a href="/YDHCommunity/index.jsp">양디고 커뮤니티</a>
			</h1>
			<%@ include file="/menu.jsp"%>
		</header>
		<div id="content">
			<div class="blur"></div>