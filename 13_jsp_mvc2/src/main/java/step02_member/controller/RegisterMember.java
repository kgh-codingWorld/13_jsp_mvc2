package step02_member.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import step02_member.dao.MemberDAO;
import step02_member.dto.MemberDTO;

@WebServlet("/registerMember")
public class RegisterMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private String profileRepositoryPath = FileConfig.PROFILE_REPOSITORY_PATH;
	
	// 회원가입화면 이동
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dis = request.getRequestDispatcher("step02_memberEx/mRegister.jsp");
		dis.forward(request, response);
	
	}
	
	// 회원가입 처리 로직
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 전송된 파일을 받는다.
		MultipartRequest multi = new MultipartRequest(request, profileRepositoryPath , 1024 * 1024 * 30 , "utf-8" ); 
		
		// 전송된 데이터를 DTO 형식으로 만든다.
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(multi.getParameter("memberId"));
		memberDTO.setMemberNm(multi.getParameter("memberNm"));
		memberDTO.setPasswd(multi.getParameter("passwd"));
		memberDTO.setSex(multi.getParameter("sex"));
		memberDTO.setBirthAt(multi.getParameter("birthAt"));
		memberDTO.setHp(multi.getParameter("hp"));
		if (multi.getParameter("smsRecvAgreeYn") == null) memberDTO.setSmsRecvAgreeYn("N");				
		else										  	  memberDTO.setSmsRecvAgreeYn(multi.getParameter("smsRecvAgreeYn"));
		memberDTO.setEmail(multi.getParameter("email"));
		if (multi.getParameter("emailRecvAgreeYn") == null) memberDTO.setEmailRecvAgreeYn("N");
		else 												memberDTO.setEmailRecvAgreeYn(multi.getParameter("emailRecvAgreeYn"));					
		memberDTO.setZipcode(multi.getParameter("zipcode"));
		memberDTO.setRoadAddress(multi.getParameter("roadAddress"));
		memberDTO.setJibunAddress(multi.getParameter("jibunAddress"));
		memberDTO.setNamujiAddress(multi.getParameter("namujiAddress"));
		
		
		Enumeration<?> files =  multi.getFileNames();						
		
		String originalFileName = "";
		String profileUUID = "";
		
		// 원본파일 이름과 UUID를 구하여 DTO 객체에 set한다.
		if (files.hasMoreElements()) {													
			String element = (String)files.nextElement();								
			originalFileName = multi.getOriginalFileName(element);						
			profileUUID = UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
			
			memberDTO.setProfile(originalFileName);
			memberDTO.setProfileUUID(profileUUID);
			
		}
		
		// 업로드된 파일의 이름을 UUID.확장자 형태로 수정한다.
		File file = new File(profileRepositoryPath + originalFileName);						
		File renameFile = new File(profileRepositoryPath + profileUUID);			
		file.renameTo(renameFile);	
		
		// 전송된 데이터를 DTO형식으로 DAO로 전달하여 INSERT쿼리를 진행한다.
		MemberDAO.getInstance().registerMember(memberDTO);
		
		String jsScript = """
				<script>
					alert('회원가입 되었습니다.');
				  location.href='mainMember';
			    </script>""";
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
	
	}
	

}
