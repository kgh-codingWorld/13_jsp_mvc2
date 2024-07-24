package step01_board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import step01_board.dto.BoardDTO;

/*

	# JSP MVC2 데이터베이스 연동메뉴얼

	1) 데이터베이스 연결 풀링(연결을 미리 만들어 놓고 재사용하여 데이터베이스 연결 및 해제에 따른 오버헤드를 줄임)과 
	   관련된 기능을 사용하고 데이터베이스 애플리케이션의 성능을 향상시키기 위하여 아래의 라이브러리를 WEB-INF/lib 경로에 추가한다.
	
		commons-dbcp2-2.2.0.jar 
		commons-pool2-2.5.0.jar
		mysql-connector-j-8.0.32.jar


	2) 이클립스에서 Servers폴더에 있는 Context.xml (경로설정) 파일에 아래의 설정을 추가한다. 
	
		[ 확인사항 ] 
		- url , name , username , password
	
		<Resource 
			auth="Container" 
			driverClassName="com.mysql.cj.jdbc.Driver"
			type="javax.sql.DataSource"
			url="jdbc:mysql://localhost:3306/MVC2_PRACTICE?serverTimezone=Asia/Seoul&amp;useSSL=false"
			name="jdbc/board" 
			username="root"
			password="1234" 
			loginTimeout="10" 
			maxWait="5000" 
		/> 

	3) 데이터베이스와 연동하는 메서드를 생성하여 데이터베이스 연결객체를 생성 및 사용한다. 
	
		(패키지)
		import javax.naming.Context;
		import javax.naming.InitialContext;
		import javax.sql.DataSource;
		
		(연결코드)
		private void getConnection() {
			
			try {
				Context initctx = new InitialContext();						 // 데이터베이스와 연결하기 위한 init객체 생성
				Context envctx = (Context) initctx.lookup("java:comp/env");  // lookup 메서드를 통해 context.xml 파일에 접근하여 자바환경 코드를 검색     
				DataSource ds = (DataSource) envctx.lookup("jdbc/board"); 	 // <Context>태그안의 <Resource> 환경설정의 name이 jdbc/board인 것을 검색	  
				conn = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
		}

*/

// DAO (Data Access Object) : 데이터베이스와의 통신 및 데이터베이스 관련 작업을 처리하는 데 사용
public class BoardDAO {

	/*
 	
	 	# 싱글턴 디자인 패턴
	  
		단일 인스턴스 유지, 상태 공유, 전역 접근 지점, 어플리케이션의 상태 관리 및 메모리 최적화와 같은 이점을 활용하여
		어플리케이션의 구조를 더 효율적으로 관리하고 유지보수하기 쉽게 개발할 수 있다.
	
	*/
	
	private BoardDAO() {}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	
	// 데이터베이스 연동객체 생성
	private Connection conn         = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs            = null;
	
	// 데이터베이스 연동 메서드 생성
	private void getConnection() {
		
		try {
			Context initctx = new InitialContext();						 // 데이터베이스와 연결하기 위한 init객체 생성
			Context envctx = (Context) initctx.lookup("java:comp/env");  // lookup 메서드를 통해 context.xml 파일에 접근하여 자바환경 코드를 검색     
			DataSource ds = (DataSource) envctx.lookup("jdbc/board"); 	 // <Context>태그안의 <Resource> 환경설정의 name이 jdbc/board인 것을 검색	  
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 데이터베이스 연동 해지 메서드 생성
	private void getClose() {
		if (rs != null)    try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		if (pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
		if (conn != null)  try {conn.close();} catch (SQLException e) {e.printStackTrace();}
	}
	
	// 게시글 등록 메서드
	//    serlvet               db
	public void insertBoard(BoardDTO boardDTO) {
		
		try {
			
			getConnection(); // DB 연결 메서드
			
			String sql = """
				INSERT INTO BOARD(WRITER , EMAIL , SUBJECT , PASSWORD , CONTENT , READ_CNT , ENROLL_DT)
				VALUES           (  ?    ,  ?    ,    ?    ,    ?     ,   ?     ,    0     ,    NOW())	
					""";
			
			pstmt = conn.prepareStatement(sql); 	  // 패스
			pstmt.setString(1, boardDTO.getWriter()); // 선처리문 완성
			pstmt.setString(2, boardDTO.getEmail());
			pstmt.setString(3, boardDTO.getSubject());
			pstmt.setString(4, boardDTO.getPassword());
			pstmt.setString(5, boardDTO.getContent());
			pstmt.executeUpdate(); // sql 실행
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose(); // DB 연결 해지 메서드
		}
		
	}
	
	// 게시물 조회 메서드
	public ArrayList<BoardDTO> getBoardList() { // BoardDTO 객체를 ArrayList로 반환하는 메서드
		
		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>(); // BoardDTO 객체들을 모아 관리하는 컬렉션이다.
		
		try {
			// 데이터베이스 연결
			getConnection();
			
			// 데이터베이스에서 게시물 목록을 조회하는 로직을 여기에 추가
			pstmt = conn.prepareStatement("SELECT * FROM BOARD"); // 패스
			rs = pstmt.executeQuery(); // executeQuery(); > selectQuery 실행 // selectQuery 실행하여 결과 집합을 반환
			
			// 결과 집합을 순회하며 BoardDTO 객체를 생성하고 리스트에 추가
			while(rs.next()) {
				
				BoardDTO boardDTO = new BoardDTO(); // 게시물 하나의 데이터를 저장하고 전달하는 역할을 한다.
				boardDTO.setBoardId(rs.getLong("BOARD_ID"));
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setEnrollDt(rs.getDate("ENROLL_DT"));
				boardDTO.setSubject(rs.getString("SUBJECT"));
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
				
				// 리스트에 추가
				boardList.add(boardDTO);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		System.out.println("DAO boardList : " + boardList);
		
		// 조회한 게시물 목록을 반환
		return boardList;
	}
	
	// 게시물 상세조회 메서드
	public BoardDTO getBoardDetail(long boardId) {
		
		BoardDTO boardDTO = new BoardDTO();

		try {
			getConnection();
			
			// 조회수 증가
			String sql = """
					UPDATE BOARD
					SET	   READ_CNT = READ_CNT + 1
					WHERE  BOARD_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardId);
			pstmt.executeUpdate();
			
			// 1개의 커뮤니티 게시글 정보 반환
			sql = """
					SELECT * 
					FROM   BOARD
					WHERE  BOARD_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardDTO.setBoardId(rs.getLong("BOARD_ID"));
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setContent(rs.getString("CONTENT"));
				boardDTO.setEmail(rs.getString("EMAIL"));
				boardDTO.setSubject(rs.getString("SUBJECT"));
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
			}
			
		} catch(Exception e) {
			
		} finally {
			getClose();
		}
		
		System.out.println("DAO boardDetail : " + boardDTO);
		
		return boardDTO;
	}
	
	
}



