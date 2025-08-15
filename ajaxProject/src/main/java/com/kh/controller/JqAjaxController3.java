package com.kh.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.kh.model.vo.Member;

/**
 * Servlet implementation class JqAjaxController
 */
@WebServlet("/jqAjax3.do")
public class JqAjaxController3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int userNo = Integer.parseInt(request.getParameter("no"));
//		Member m = new MemberService().selectMember(userNo); //  실제로 이건데 없으니까 밑에 있다고 치고~
		// 위 Member 객체에 각 필드에 조회된 데이터들이 담겨있을 것!
		
		Member m = new Member(1, "차은우", 30, "남"); // 조회된 데이터가 다음과 같다는 가정하에 다음 작업 해보자~
		
//		response.getWriter().print(m); // success의 매개변수로 보낸다.. 매개변수 이름은 내 마음대로
		// 사실 print안의 m뒤에는 vo에서 만든 toString()이 숨어있음.. 
		// 얘를 리턴한거라 console창에는 Member [userNo=1, userName=???, age=30, gender=?] 이렇게 뜸!
		// 쉽게 말하면 문자열을 보낸 거임.
		// => vo 객체를 곧바로 응답하면 .toString()의 문자열 응답
		
		// JSONObject {key:value, key:value}
//		JSONObject jObj = new JSONObject();    // {}
//		jObj.put("userNo", m.getUserNo()); 	   // {userNo : 1}
//		jObj.put("userName", m.getUserName()); // {userNo : 1 , userName : "차은우"}
//		jObj.put("age", m.getAge()); // {userNo : 1 , userName : "차은우" , age : 20}
//		jObj.put("gender", m.getGender()); // {userNo : 1 , userName : "차은우", age : 20, gender: "남"}
//		
//		response.setContentType("application/json; charset=utf-8");
//		response.getWriter().print(jObj);
//		 => 이 방법도 괜찮은데 필드가 많아지면 복잡해짐! 알아서 해주는 GSON 라이브러리 사용해보자
		// GSON : Google JSON
		
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson(); // Gson객체.toJson(응답할자바객체, 응답할스트림); => put같은 역할까지 해서 쏴줌
		gson.toJson(m, response.getWriter()); // 알아서 데이터 담아서 쏴줌..
//		new Gson().toJson(m, response.getWriter()); 이렇게 한 줄로 가능
		
		// Gson 이용해서 vo 객체만 하나 응답시 JSONObject {key:value, key:value} 형태로 만들어서 응답
		// 해당 vo의 필드명으로 알아서 key값을 지어줌.. 대박이징?
		
		
		// 자바 배열 또는 ArrayList 응답시 JSONArray [value, value, .. ]형태로 응답
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
