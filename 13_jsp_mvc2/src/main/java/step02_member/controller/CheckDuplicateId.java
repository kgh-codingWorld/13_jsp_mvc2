package step02_member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step02_member.dao.MemberDAO;

@WebServlet("/checkDuplicateId")
public class CheckDuplicateId extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter pw = response.getWriter();
		
		if (MemberDAO.getInstance().checkDuplicateId(request.getParameter("memberId"))) { // 화면에서 전송받은 memberId가 중복되면
			pw.print("isDuple");	// "isDuple" 데이터를 AJAX의 success : function(data)로 반환
		}
		else {
			pw.print("isNotDuple"); // "isNotDuple" 데이터를 AJAX의 success : function(data)로 반환
		}
		
	}

}
