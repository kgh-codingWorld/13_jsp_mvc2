package step01_board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step01_board.dao.BoardDAO;

@WebServlet("/bDelete")
public class DeleteBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// bDelete.jsp로 패스
		request.setAttribute("boardId", request.getParameter("boardId"));
		
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bDelete.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long boardId = Long.parseLong(request.getParameter("boardId"));
	
		BoardDAO.getInstance().deleteBoard(boardId);
		
		String jsScript = """
			   <script>
				   alert('삭제 되었습니다.');
				   location.href = 'bList';
			   </script>""";
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
		
	}

}
