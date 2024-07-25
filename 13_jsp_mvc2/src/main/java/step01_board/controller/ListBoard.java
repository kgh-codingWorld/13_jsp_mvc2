package step01_board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step01_board.dao.BoardDAO;
import step01_board.dto.BoardDTO;

@WebServlet("/bList")
public class ListBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// 커뮤니티게시글 전체조회 화면으로 이동 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<BoardDTO> boardList = BoardDAO.getInstance().getBoardList();
		
		// bList.jsp파일에 전체게시글을 전송한다.
		request.setAttribute("boardList" , boardList);
		
		// bList.jsp파일로 이동
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bList.jsp");
		dis.forward(request, response);
	
	}

}
