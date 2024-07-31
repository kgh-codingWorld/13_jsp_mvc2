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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 주는 건 받자.
		request.setAttribute("menu", request.getParameter("menu")); // bAuthentication.jsp 파일로 패스
		
		
		//  1개의 게시글정보를 가져온다. + jsp파일로 전송
		long boardId = Long.parseLong(request.getParameter("boardId"));
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(boardId);
		
		request.setAttribute("boardDTO", boardDTO);
		
		// jsp로 이동한다.
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bAuthentication.jsp");
		dis.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 주는건 받자
		request.setCharacterEncoding("utf-8");
		
		
		// DTO 타입으로 DAO클래스에 데이터 전송
		BoardDTO boardDTO = new BoardDTO();
		long boardId = Long.parseLong(request.getParameter("boardId"));
		
		boardDTO.setBoardId(boardId);
		boardDTO.setPassword(request.getParameter("password"));
		
		boolean isChecked = BoardDAO.getInstance().checkAuthenticationUser(boardDTO);
		
		// 리액션
		String jsScript = "";
		if (isChecked) { // 인증 ok
			
			String menu = request.getParameter("menu");
			if (menu.equals("update")) { // 수정화면으로 이동
				jsScript += "<script>";
				jsScript += "location.href='bUpdate?boardId=" + boardId + "';";
				jsScript += "</script>";
			}
			else if (menu.equals("delete")) { // 삭제화면으로 이동
				jsScript += "<script>";
				jsScript += "location.href='bDelete?boardId=" + boardId + "';";
				jsScript += "</script>";
			}
			
		}
		else {
			
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
