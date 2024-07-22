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

@WebServlet("/bWrite")
public class WriteBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	// 커뮤니티 게시글 작성화면으로 이동
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// bWrite.jsp파일로 이동
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bWrite.jsp");
		dis.forward(request, response);
	}

	// 커뮤니티 게시글 작성 처리 로직
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 전송되는 데이터의 한글화 처리 
		request.setCharacterEncoding("utf-8");
		
		// 전송되는 데이터를 DTO형식으로 만들어서 DAO로 전달한다.
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setWriter(request.getParameter("writer"));
		boardDTO.setSubject(request.getParameter("subject"));
		boardDTO.setEmail(request.getParameter("email"));
		boardDTO.setPassword(request.getParameter("password"));
		boardDTO.setContent(request.getParameter("content"));
		
		
		BoardDAO.getInstance().insertBoard(boardDTO);
		
		
		// 리액션
		
	}

}
