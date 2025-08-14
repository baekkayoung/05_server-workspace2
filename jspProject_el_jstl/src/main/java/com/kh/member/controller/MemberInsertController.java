package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) 인코딩 작업
		request.setCharacterEncoding("utf-8");
		
		// 2) 요청시 전달값 뽑아서 변수 및 객체에 기록하기
		String userId = request.getParameter("userId"); // "user03"
		String userPwd = request.getParameter("userPwd"); // "pass03"
		String userName = request.getParameter("userName"); // "장원영"
		String phone = request.getParameter("phone"); // "010-1234-5678" | ""
		String email = request.getParameter("email"); // "A@naver.com | ""
		String address = request.getParameter("address"); // "서울" | ""
		String[] interestArr = request.getParameterValues("interest"); // ["운동","등산",...] | NULL
		
		
		// String[] ----> String
		// ["운동","등산"] > "운동, 등산"
		
		String interest= "";
		if(interestArr != null) {
			interest = String.join(",", interestArr);
		}
		
		// 기본생성자로 생성 후 setter 메소드 이용해서 담기
		// "아싸리 매개변수 생성자를 이용해서 생성과 동시에 담기"
		
		Member m = new Member(userId, userPwd, userName, phone, email, address, interest);
		
		
		// 3) 요청처리 (서비서 메소드 호출 및 결과 받기)
		int result = new MemberService().insertMember(m);
			
		// 4) 처리 결과를 가지고 사용자가 보게 될 응답뷰 지정 후 포워딩 또는 url 재요청
		
		if(result>0) {
			// 성공 => / jsp url 재요청 => index 페이지
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "성공적으로 회원가입 되었습니다!");
			
			response.sendRedirect(request.getContextPath()); // url 재요청 => request값 셋팅 x
			
		}else {
			// 실패
			request.setAttribute("errorMsg", "회원가입 실패!");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			// 굳이 안 담아도 됨~
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
