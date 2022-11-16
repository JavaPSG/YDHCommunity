<%@page import="com.github.javapsg.utils.JDBC"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav id="nav">
	<ul>
		<li><a href="#" id="menuToggle"> <span>메뉴</span><span
				id="icon">&#xE5D2</span></a>
			<div id="menu">
				<div class="tab">
					<ul>
						<li><a href="#"><span id="menu-icon">&#xe88a</span> <span>메인
									화면</span></a></li>
						<li><a href="#"><span id="menu-icon">&#xe241</span> <span>게시글</span></a></li>
						<li><a href="#"><span id="menu-icon">&#xe7ef</span> <span>사용자
									목록</span></a></li>
						<li><a href="/YDHCommuniy/content/login.jsp"><span id="menu-icon">&#xea77</span> <span>로그인</span></a></li>
						<li><a href="#"><span id="menu-icon">&#xe174</span> <span>회원가입</span></a></li>
						<%
							if (true) {
							JDBC.getConnection(); 
						%>
						<li><a href="#" id="change-white-theme"><span
								id="menu-icon">&#xe51c</span> <span>테마: 다크</span></a></li>
						<%
							} else {
						%>
						<li><a href="#" id="change-dark-theme"><span
								id="menu-icon">&#xe518</span> <span>테마: 라이트</span></a></li>
						<%
							} 
						%>
					</ul>
				</div>
			</div></li>
	</ul>
</nav>