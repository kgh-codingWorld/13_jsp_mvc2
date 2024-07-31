package step02_member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import step02_member.dao.MemberDAO;
import step02_member.dto.MemberDTO;

@WebServlet("/loginMember")
public class LoginMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// 로그인화면 이동
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("step02_memberEx/mLogin.jsp");
		dis.forward(request, response);
	}
	
	
	// 로그인 로직 구현
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// AJAX에서 전송된 memberId 와 passwd를 받아서 DTO형태로 만든다.
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(request.getParameter("memberId"));
		memberDTO.setPasswd(request.getParameter("passwd"));
		
		String isAuthentication = "false"; // 인증유무의 기본값은 false로 지정
		// DB에서 회원정보를 검색한후 인증된회원이면
		if (MemberDAO.getInstance().loginMember(memberDTO)) {
			
			HttpSession session = request.getSession();
			session.setAttribute("memberId", request.getParameter("memberId")); // session에 회원 아이디를 저장한다.			
			isAuthentication = "true"; // isAuthentication를 true로 반환하여 success : function(data)함수로 반환한다.
			
		}
		
		PrintWriter out = response.getWriter();	
		out.print(isAuthentication);

	}

}
