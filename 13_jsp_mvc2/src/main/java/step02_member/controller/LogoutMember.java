package step02_member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logoutMember")
public class LogoutMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	// 로그아웃 처리 로직
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.invalidate(); // session의 모든 데이터를 삭제한다.
		
	   String jsScript = """
				<script>
					alert('로그아웃 되었습니다.');
				  location.href = 'mainMember';
			    </script>""";
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
			   
		
	}	

}
