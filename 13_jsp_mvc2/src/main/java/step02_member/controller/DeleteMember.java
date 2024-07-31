package step02_member.controller;

import java.io.File;
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

@WebServlet("/deleteMember")
public class DeleteMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private String profileRepositoryPath = FileConfig.PROFILE_REPOSITORY_PATH;
	
	// delete화면 이동
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		request.setAttribute("memberId", (String)session.getAttribute("memberId")); // 세션에서 조회한 아이디를 mDelete.jsp로 전송
		
		RequestDispatcher dis = request.getRequestDispatcher("step02_memberEx/mDelete.jsp");
		dis.forward(request, response);
	
	}

	// delete 로직 구현
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		// session에서 조회한 회원의 profileUUID를 조회한다.
		String profileUID = MemberDAO.getInstance().getMemberDetail((String)session.getAttribute("memberId")).getProfileUUID();
		
		// 회원의 uuid파일을 삭제한다.
		new File(profileRepositoryPath + profileUID).delete();
		
		// DAO로 회원의 id를 전달하여 delete쿼리를 진행한다.
		MemberDAO.getInstance().deleteMember((String)session.getAttribute("memberId"));

		// session정보를 삭제한다.
		session.invalidate();
		
	    String jsScript = """
				<script>
					alert('회원탈퇴 되었습니다.');
				  location.href = 'mainMember';
			    </script>""";
	   
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);

	}
	
}
