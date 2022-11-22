<%@page import="com.github.javapsg.ydhcommunity.post.PostDataManager"%>
<%@page import="com.github.javapsg.ydhcommunity.user.User"%>
<%@page import="com.github.javapsg.ydhcommunity.theme.ThemeUtil"%>
<%@page import="com.github.javapsg.ydhcommunity.theme.Theme"%>
<%@page import="com.github.javapsg.ydhcommunity.user.UserDataManager"%>
<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%
	UserDataManager userManager = UserDataManager.getInstance();
	PostDataManager postManager = PostDataManager.getInstance();
	userManager.init();
	Theme theme = ThemeUtil.getTheme(request.getCookies());
	String result = theme.getId().split("-")[1];
%>
<body class="<%=theme.getThemeClass()%>">
	<div id="wrapper">
		<header id="header" class="alt">
			<h1>
				<a href="/YDHCommunity/index.jsp">양디고 커뮤니티</a>
			</h1>
			<nav id="nav">
				<ul>
					<li><a href="#" id="menuToggle"> <span>메뉴</span><span
							id="icon">&#xE5D2</span></a>
						<div id="menu">
							<div class="tab">
								<ul>
									<li><a href="/YDHCommunity/index.jsp"><span
											id="menu-icon">&#xe88a</span> <span>메인 화면</span></a></li>
									<li><a href="/YDHCommunity/content/post_list.jsp"><span
											id="menu-icon">&#xe241</span> <span>게시글</span></a></li>
									<li><a href="/YDHCommunity/content/user_list.jsp"><span
											id="menu-icon">&#xe7ef</span> <span>사용자 목록</span></a></li>
									<%
										String url = request.getRequestURI();
										User user = userManager.getUser(request.getCookies());
										if (user == null) {
									%>
									<li><a href="/YDHCommunity/content/login.jsp"><span
											id="menu-icon">&#xea77</span> <span>로그인</span></a></li>
									<li><a href="/YDHCommunity/content/register.jsp"><span
											id="menu-icon">&#xe174</span> <span>회원가입</span></a></li>
									<%
										} else {
									%>
									<li><a href="/YDHCommunity/content/user_view.jsp?user=<%= user.getEmail() %>"><span
											id="menu-icon">&#xe7fd</span> <span>프로필</span></a></li>
									<li><a href="/YDHCommunity/Logout?url=<%=url%>"><span
											id="menu-icon">&#xe174</span> <span>로그아웃</span></a></li>
									<%
										}
									%> 
									<li id="theme"><a
										href="/YDHCommunity/Theme?theme=<%=result%>&url=<%=url%>&user=<%=(String) request.getParameter("user")%>"
										id="<%=theme.getId()%>"><span id="menu-icon"><%=theme.getIcon()%></span>
											<span><%=theme.getName()%></span></a></li>
								</ul>
							</div>
						</div></li>
				</ul>
			</nav>
		</header>
		<div id="content">
			<div class="blur"></div>