package com.kh.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.kh.model.vo.Member;

/**
 * Servlet implementation class JqAjaxController4
 */
@WebServlet("/jqAjax4.do")
public class JqAjaxController4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController4() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		ArrayList<Member> = new MemberService().selectList();
		
		ArrayList<Member> list = new ArrayList<Member>();
		list.add(new Member(1, "차은우", 20, "남")); // JSONObjex {}
		list.add(new Member(2, "장원영", 22, "여"));
		list.add(new Member(3, "박보검", 27, "남"));
		
		// JSONArrat[{}, {}, {}]
		JSONArray jArr= new JSONArray(); // []
		
		/*
        for(Member m : list) { 
            JSONObject jObj = new JSONObject();
            jObj.put("userNo", m.getUserNo());
            jObj.put("userName", m.getUserName());
            jObj.put("age", m.getAge());
            jObj.put("gender", m.getGender());
            
            jArr.add(jObj);
        } 원래는 이런 식으로 하나씩 for문으로 넣어줘야하는데 이거를 JSON이 해준다
        */
		
		response.setContentType("application/json; charset=utf-8");
		new Gson().toJson(list, response.getWriter()); // 위에걸 이 한줄로 해줌..!
		// index.jsp로~
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
