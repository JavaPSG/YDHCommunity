package com.github.javapsg.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String id, pwd;
		MemberVO vo = null;
		PrintWriter out = response.getWriter();
		
		id = request.getParameter("id");
		pwd = request.getParameter("pwd");
		
		MemberDAO dao = new MemberDAO();
		vo = dao.getMemberData(id);
		
		if(vo == null || !pwd.equals(vo.getMemberPwd())){
			out.println("<script> alert('회원정보가 맞지 않습니다.'); history.back(); </script>"); 
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginOK", vo);
			response.sendRedirect("/login/loginOK.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
