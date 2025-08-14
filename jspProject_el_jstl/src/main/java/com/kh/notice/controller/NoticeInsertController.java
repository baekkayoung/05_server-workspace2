package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.board.model.service.NoticeService;
import com.kh.board.model.vo.Notice;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String noticeTitle = request.getParameter("title"); // jsp의 네임값
		String noticeContent = request.getParameter("content");
		
		// 로그인한 회원 정보 얻어내는 방법
		// 1. input type ="hidden" 으로 애초에 요청시 숨겨서 전달하기
		// 2. session 영역에 담겨있는 회원객체로부터 뽑기
		
		HttpSession session = request.getSession();
		int userNo = ((Member)session.getAttribute("loginUser")).getUserNo();
		
		Notice n = new Notice();
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
//		n.setNoticeWriter(userNo+""); // String이어야하는데 int니까 공백 추가하면 String 가능
		n.setNoticeWriter(String.valueOf(userNo));
		
		int result = new NoticeService().insertNotice(n);
			
			
		if(result>0) { // 성공 => /jsp/list.no url 재요청 -> 목록페이지 보여지도록 alert(성공적으로 공지사항 등록되었습니다.)
			session.setAttribute("alertMsg","성공적으로 등록했습니다.");
			response.sendRedirect(request.getContextPath()+"/list.no"); // 재요청은 무조건 response로..
			
		} else { //실패 => 에러문구 담아서 에러페이징 포워딩
			request.setAttribute("errorMsg" , "공지사항 등록실패");
			request.getRequestDispatcher("view/common/errorPage.jsp").forward(request, response);
		
			
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
