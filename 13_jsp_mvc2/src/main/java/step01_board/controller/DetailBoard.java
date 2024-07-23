package step01_board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step01_board.dao.BoardDAO;
import step01_board.dto.BoardDTO;

@WebServlet("/bDetail")
public class DetailBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	// 1개의 커뮤니티 게시글 조회화면으로 이동(데이터 포함)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 주는 건(boardId) 받는다.
		long boardId = Long.parseLong(request.getParameter("boardId")); // .getAttribute는 Object 객체 타입
		
		// DAO로 boardId를 보내 1개의 게시글을 돌려 받는다.
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(boardId);
		
		// bDetail.jsp로 게시글을 보낸다.
		request.setAttribute("boardDTO", boardDTO);
		
		// bDetail.jsp로 이동
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bDetail.jsp");
		dis.forward(request, response);
	}

}
