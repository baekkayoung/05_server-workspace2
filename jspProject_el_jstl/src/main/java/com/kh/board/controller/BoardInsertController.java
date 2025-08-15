package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 일반 방식이 아닌 multipart/form-data로 전송하는 경우 request로부터 값 뽑기 불가
		// String boardTitle = request.getParameter("title");
		// String category = request.getParameter("category");
		
		// enctype이 multipart/form-data로 잘 전송되었을 경우 전반적인 내용들을 수행
		if(ServletFileUpload.isMultipartContent(request)) {
			
		// 파일 업로드를 위한 라이브러리 : cos.jar(com.oreilly.servlet의 약자)
								  // http://www.servlets.com 접속해서 다운로드
			// 1. 전달되는 파일을 처리할 작업 내용 (전달되는 파일의 용량 제한, 전달된 파일을 저장시킬 폴더 경로)
			// 1_1) 전달되는 파일의 용량 제한 (int maxSize => byte단위로 ) = 10Mbyte로 제한
			/*
			 * byte => kbyte => mbyte => gbyte => tbyte
			 * 
			 * 1kbyte == 1024byte
			 * 1mbyte == 1024kbyte == 1024*1024byte
			 * 10mbyte == 10*1024*1024 byte
			 */
			int maxSize = 10*1024*1024;
			/*
			 * application => ServletContext
			 * session
			 */
			// 1_2) 전달된 파일을 저장시킬 폴더의 경로 알아내기
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/"); // application
//			System.out.println(savePath);
//			C:\05_server-workspace2\jspProject\WebContent\resources\board_upfiles\
			
			// 2. 전달된 파일의 파일명 수정 및 서버에 업로드 작업
			/*
			 * >>  HttpServletRequest request => MultipartRequest multiRequest 변환
			 * 
			 
			 request, 저장시킬폴더경로, 용량제한, 인코딩값, 파일명 수정시켜주는객체
			 	경로에 업로드됨
			 	위 구문 한 줄 실행만으로 넘어온 첨부파일이 해당 폴더에 무조건 업로드 됨!
				단, 업로드시 파일명은 수정해주는게 일반적임! 그래서 파일명 수정시켜주는 객체
				=> 같은 파일명이 존재할 경우 덮어씌워줄 수 있고, 파일명에 한글/특수문자/띄어쓰기가 포함될 경우 서버에 따라 문제 발생
				
				기본적으로 파일명이 안겹치도록 수정작업해주는 객체 있음
				=> DefaultFileRenamePolicy 객체 (cos.jar에서 제공하는 객체)
				=> 내부적으로 해당 클래스에 rename() 메소드가 실행되면서 파일명 수정된 후 업로드
				
				rename(원본파일){
				기존에 동일한 파일명이 존재할 경우
				파일명 뒤에 카운팅된 숫자를 붙여줌
				ex) aaa.jpg, aaa1.jpg, aaa2.jpg
					꽃.png, 꽃1.png
				return 수정파일
				}
				
				나만의 방식대로 절대 안 겹치도록 rename 할 수 있게 FileRenamePolicy 클래스 만들기 (Rename 메소드 재정의)
				com.kh.common.MyFileRenamePolicy 클래스 만들기!
			*
			*/
			// 이 한줄 자체로 업로드!
			MultipartRequest multiRequest = new MultipartRequest(request, savePath , maxSize, "utf-8", new MyFileRenamePolicy());
			// db에 사진을 저장할 수 없잖아... 사진을 컴퓨터에 저장하고 파일경로를 db에 저장하면 이미지태그할때 src에다가 그 경로를 적어서 보여지게 하는 거임
			
			// 3. DB에 기록할 데이터를 뽑아서 Vo에 주섬주섬 담기
			// > 카테고리번호, 제목, 내용, 작성자회원번호 뽑아서 Board 테이블 insert
			// > 넘어온 첨부파일이 있다면 원본명, 수정명, 저장폴더경로 Attachment 테이블에 insert
			// requst로 뽑으면 안됨!!
			
			String category = multiRequest.getParameter("category"); // form에 있는 거
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");
			
			Board b = new Board();
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(boardWriter);
			
			Attachment at = null; 
			// 처음에는 null로 초기화.. 첨부파일 무조건 넘겨야하는건아님, 넘어오는 게 있다면 생성해주려고! 밑에 클래스에서 제공하는 거 사용!
			// multiRequest.getOriginalFileName("키") : 넘어온 첨부파일이 있었을 경우 "원본명" | 없었을 경우 null 리턴
			if(multiRequest.getOriginalFileName("upfile") !=null) { // 넘어온 첨부파일이 있을 경우
				at = new Attachment(); //여기 파란색 jsp에서의 네임값임
				
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); // 괄호안 자체가 원본명
				at.setChangeName(multiRequest.getFilesystemName("upfile")); // 시스템상에 어떻게 저장되어있냐~?
				at.setFilePath("resources/board_upfiles/");
			}
			
			
			
			// 4. 서비스 요청 (요청처리)
			
			int result= new BoardService().insertBoard(b, at);
			
			// 5. 응답뷰 지정
			// 상공 = > /jsp/list.bo?cpage=1 url 재요청
			// 실패 => 에러페이지
			
			if(result>0) { // 성공
				// HttpSession session = request.getSession();
				request.getSession().setAttribute("alertMsg", "성공!");
				response.sendRedirect(request.getContextPath()+"/list.bo?cpage=1");
				
			} else { // 실패
				request.setAttribute("errorMsg", "에러");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
		
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
