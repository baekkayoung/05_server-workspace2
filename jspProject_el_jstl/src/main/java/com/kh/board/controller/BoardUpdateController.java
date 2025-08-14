package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		if(ServletFileUpload.isMultipartContent(request)) {// 사용자가 요청시 전달한 값이 muti~가 맞는지 확인
			
			// 1_1. 전달되는 파일 용량 제한(int maxSize)
			int maxSize = 10 * 1024 * 1024;
			
			// 1_2. 전달되는 파일을 저장시킬 서버의 폴더 물리적인 경로 (String savePath)
			String savePath = request.getServletContext().getRealPath("/resources/board_upfiles/");
			// 2. 전달된 파일명 수정 작업 후 서버에 업로드
			// 		HttpServletRequest request => MultiRequest로 바꿔준다.
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "utf-8", new MyFileRenamePolicy());
			// 이 한 줄 만으로 savePath에 이미 파일이 올라감.
			
			// 3. 본격적으로 sql문 실행할 때 필요한 값을 뽑아서 vo에 기록
			
			// >> 공통적으로 수행: update Board
			int boardNo = Integer.parseInt(multiRequest.getParameter("bno"));
			String Category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			
			Board b = new Board(); // 각 필드에 초기값이 담긴 상태로.
			b.setBoardNo(boardNo); // 자료 많으니까 객체에 담아두기
			b.setCategory(Category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			
			Attachment at = null; // 첨부가 있을지도 없을지도 모르니가 처음에는 null로 초기화
			// 있을 경우 그때 생성
			
			// 멀티리퀘스트 클래스로 확인 가능
			if(multiRequest.getOriginalFileName("upfile") != null) {
				// 새로 넘어온 첨부파일이 있을 경우 => at 생성
				at = new Attachment(); // 객체 생성... null이 아니라 뭐라도 있게됨
				at.setOriginName(multiRequest.getOriginalFileName("upfile"));
				at.setChangeName(multiRequest.getFilesystemName("upfile")); // 시스템상에서는 무슨 이름이에요?
				at.setFilePath("resources/board_upfiles/");
				
				
			if(multiRequest.getParameter("originFileNo") != null) {
				// 기존의 첨부파일이 있었을 경우 => attachment update (기존의 파일번호 필요함)
				at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));
			}else {
				// 기존의 첨부파일이 없었을 경우 => Insert Attachment (현재 게시글번호 필요함)
				at.setRefBoardNo(boardNo); // 얘는 왜 multi로 안해?
			}
				
			}
			
			// 새로 넘어온 첨부파일이 없었다면 at는 여전히 null일거임
			int result = new BoardService().updateBoard(b,at);
			// 새로운 첨부파일x 						=> (b, null)				=> Board Update
			// 새로운 첨부파일O, 기존의 첨부파일 O         => (b, fileNo가 담긴 at)		=> Board Update, Attachment Update
			// 새로운 첨부파일O, 기존의 첨부파일 x			=> (b, refBoardNo가 담긴 at)  => Board Update, Attachment Insert
			// at가 null 첨부파일 건들지 x, 
			
			if(result > 0) {
				// 성공 => alert(성공적으로 수정됐습니다.) => 기존에 봤었던 상세조회 페이지
				request.getSession().setAttribute("alertMsg","성공적으로 수정됐습니다.");
				response.sendRedirect(request.getContextPath()+"/detail.bo?bno=" + b.getBoardNo());
			}else { // 실패 => 오류페이지
				request.setAttribute("errorMsg", "수정에 실패했습니다.");
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
