package com.kh.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class JqAjaxController2
 */
@WebServlet("/jqAjax2.do")
public class JqAjaxController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); 
		//얘는 밑에서 해야 되는데 얘는 없어도 되는 건가?
		// 클라이언트에서 보낸 요청 데이터를 읽을 때 인코딩을 지정
		// 요청 파라미터 읽기 전에,, 포스트에서 필수
		
		String name = request.getParameter("name"); // index.jsp data 안의 name
		int age = Integer.parseInt(request.getParameter("age"));
		
		// 요청 처리가 다 됐다는 가정하에 응답할 데이터
		// version1. 응답데이터가 하나의 문자열일 경우
//		String responseData = "이름: " + name + ", 나이 : " + age;
//		response.setContentType("text/html; charset=utf-8"); // 응답할 데이터에 한글이 있는 경우
		// 응답 보낼때 브라우저가 제대로 읽도록. 겟이나 포스트나 둘 다 필수
//		response.getWriter().print(responseData); // jsp의 매개변수로 꽂히게 됨
		
		// version2. 응답데이터가 여러개일 경우
//		response.setContentType("text/html; charset=utf-8");
//		response.getWriter().print(name);
//		response.getWriter().print(age);
//		=> succecss:function의 매개변수에 연이어서 하나의 문자열로 담겨있을거임
		
		/*
		 * JSON(JavaScript Object Notation : 자바스크립트 객체 표현법)
		 * - ajax 통신시 데이터 전송에 자주 사용되는 포맷중 하나
		 * 
		 * 			> [value, value, value]  => 자바스크립트에서의 배열 객체 => JSONArray
		 * 			> {key:value, key:value} => 자바스크립트에서의 일반객체 => JSONobject
		 * 
		 *	- 라이브러리 필요 (https://code.google.com/archive/p/json-simple/downloads)
		 */
		
		/*
		JSONArray jArr = new JSONArray(); // []
		jArr.add(name); // 다형성으로 인해 String도 넣을 수 있음 ["차은우"]
		jArr.add(age); // ["차은우", 20]
		// arrayList 안되는 이유? 자바로 에이잡기술을 쓰고 있는데 어레이는 자바에서 하는거지 자바스크립트로 하지못함.. 
		// AJAX로 데이터를 보낼 때는 **JS가 이해할 수 있는 포맷(JSON)**이 필요하니까 JSONArray를 쓰는 것임 
//		response.setContentType("text/html; charset=utf-8"); // 이렇게 해서 문자열로 콘솔에 찍힘.. 
		response.setContentType("application/json; charset=utf-8"); // => 이렇게 하면 콘솔에 배열 형식으로 찍힘
		// JSON 데이터를 응답할때 이렇게
		response.getWriter().print(jArr); // jsp의 a로
		*/
		
		JSONObject jObj = new JSONObject(); // 맵계열 컬렉션이랑 비슷.. {} 객체! 형식으로 비워져 있음
		jObj.put("name", name); // name이라는 key 값에 .. {name : "차은우"}
		jObj.put("age", age); // {age : 20 , name : "차은우"} 순서는 앞이나 뒤에 아무렇게나 들어감
		
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().print(jObj);

		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
