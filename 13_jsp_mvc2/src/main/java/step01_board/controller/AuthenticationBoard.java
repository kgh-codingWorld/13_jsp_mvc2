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
import step01_board.dto.BoardDTO;

@WebServlet("/bAuthentication")
public class AuthenticationBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// bAuthentication.jsp로 이동
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 주는 것 받는다.
		request.setAttribute("menu", request.getParameter("menu")); // 패스
		
		// 1개의 게시글 정보를 가져온다. + jsp 파일로 전송
		long boardId = Long.parseLong(request.getParameter("boardId"));
		
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(boardId);
		
		request.setAttribute("boardDTO", boardDTO);
		
		// jsp로 이동한다.
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bAuthentication.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 주는 건 받는다.
		BoardDTO boardDTO = new BoardDTO();
		long boardId = Long.parseLong(request.getParameter("boardId"));
		
		boardDTO.setBoardId(boardId);
		boardDTO.setPassword(request.getParameter("password"));
		
		boolean isAuthentication = BoardDAO.getInstance().checkAuthenticationUser(boardDTO);
		
		String jsScript = "";
		
		if(isAuthentication == true) {
			
			String menu = request.getParameter("menu");
			if (menu.equals("update")) {
				// 수정화면으로 이동
				jsScript += "<script>";
				jsScript += "location.href='bUpdate?boardId=" + boardId + "';";
				jsScript += "</script>";
			}
			else if(menu.equals("delete")) {
				// 삭제화면으로 이동
				jsScript += "<script>";
				jsScript += "location.href='bDelete';";
				jsScript += "</script>";
			}
			
		} else {
			/*
			  
				# history 객체
	
				- 사용자가 방문한 url 정보로 이동하는 객체
	
				[ 페이지 관련 기능 ]
				history.go(-1); 	// 한페이지 뒤로 이동
				history.back();		// 한페이지 뒤로 이동
	
				history.go(1);		// 한페이지 앞으로 이동
				history.forward(); 	// 한페이지 앞으로 이동
				
				history.go(n);		// n페이지 앞으로 이동
				history.go(-n);		// n페이지 뒤로 이동
			
			*/
			
			jsScript = """
					<script>
						alert('패스워드를 확인하세요.');
						history.go(-1);
					</script>
					""";
			
		}
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
		
	}

}
