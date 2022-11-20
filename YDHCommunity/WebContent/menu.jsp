<%@page import="com.github.javapsg.user.User"%>
<%@page import="com.github.javapsg.user.UserDataManager"%>
<%@page import="com.github.javapsg.utils.Theme"%>
<%@page import="com.github.javapsg.utils.ThemeUtil"%>
<%@page import="com.github.javapsg.utils.JDBCUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav id="nav">
	<ul>
		<li><a href="#" id="menuToggle"> <span>메뉴</span><span
				id="icon">&#xE5D2</span></a>
			<div id="menu">
				<%
					Theme theme = ThemeUtil.getTheme(request.getCookies());
					String result = theme.getId().split("-")[1];
				%>
				<div class="tab">
					<ul>
						<li><a href="/YDHCommunity/index.jsp"><span
								id="menu-icon">&#xe88a</span> <span>메인 화면</span></a></li>
						<li><a href="/YDHCommunity/content/post_list.jsp"><span
								id="menu-icon">&#xe241</span> <span>게시글</span></a></li>
						<li><a href="/YDHCommunity/content/user_list.jsp"><span
								id="menu-icon">&#xe7ef</span> <span>사용자 목록</span></a></li>
						<%
							User userData = (User) session.getAttribute("loginData");
							if (userData == null || !UserDataManager.getInstance().getUsers().contains(userData)) {
						%>
						<li><a href="/YDHCommunity/content/login.jsp"><span
								id="menu-icon">&#xea77</span> <span>로그인</span></a></li>
						<li><a href="/YDHCommunity/content/register.jsp"><span
								id="menu-icon">&#xe174</span> <span>회원가입</span></a></li>
						<li id="theme"><a href="#" id="<%=theme.getId()%>"><span
								id="menu-icon"><%=theme.getIcon()%></span> <span><%=theme.getName()%></span></a></li>
						<%
							}
						%>
					</ul>
				</div>
			</div></li>
	</ul>
</nav>