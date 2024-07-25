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

@WebServlet("/bUpdate")
public class UpdateBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	// update화면으로 이동
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		long boardId = Long.parseLong(request.getParameter("boardId"));
		
		// 수정할 게시글 정보를 가져온다.
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(boardId);
		
		request.setAttribute("boardDTO", boardDTO);
		
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bUpdate.jsp");
		dis.forward(request, response);
	
	}

	// update 처리 로직
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		
		// DTO 타입으로 DAO에 전달한다.
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		boardDTO.setSubject(request.getParameter("subject"));
		boardDTO.setContent(request.getParameter("content"));
		
		BoardDAO.getInstance().updateBoard(boardDTO);
		
		// 리액션
		String jsScript = """
				   <script>
					   alert('수정 되었습니다.');
					   location.href = 'bList';
				   </script>""";
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
		
	}

}
