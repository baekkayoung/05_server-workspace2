<%@page import="com.kh.board.model.vo.Attachment"%>
<%@page import="com.kh.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Board b = (Board)request.getAttribute("b");
	// 글번호, 카테고리명, 글제목, 글내용, 작성자아이디, 작성일
	Attachment at = (Attachment)request.getAttribute("at");
	// 첨부파일이 없다면 null
	// 첨부파일이 있다면 파일번호, 원본명, 수정명, 해당파일의 경로

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer{
        background-color: black;
        color: white;
        width: 1000px;
        height: auto;
        margin: auto;
        margin-top: 50px;
        
    }

</style>
</head>
<body>

<%@ include file ="../common/menubar.jsp" %>

<div class="outer">
    <br>
    <h2 align="center">일반게시판 상세보기</h2>

    <br>
    <table id="detail-area" border="1" align="center">

        <tr>
            <th width="70">카테고리</th>
            <td width="70"><%= b.getCategory() %></td>
            <th width="70">제목</th>
            <td width="350"><%= b.getBoardTitle() %></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><%= b.getBoardWriter() %></td>
            <th>작성일</th>
            <td><%= b.getCreate_date() %></td>
        </tr>
        <tr>
            <th>내용</th>
            <td colspan="3">
                <p style="height: 200px;"><%= b.getBoardContent() %></p>
            </td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td colspan="3">
            <% if(at==null){%>
                <!-- case1. 첨부파일이 없을 경우 -->
                첨부파일이 없습니다.
			<%}else { %>
                <!-- case2. 첨부파일이 있을 경우 -->
                <a download="<%= at.getOriginName() %>" href="<%= contextPath %>/<%= at.getFilePath() + at.getChangeName()%>"><%= at.getOriginName() %></a>
            	<!-- <a href="/jsp/  resources/board_upfiles/2025080716012190428.PNG">city1.PNG</a> -->
             <%} %>
            </td>
        </tr>

    </table>
    <br>
    <div align="center">
        <a href="<%= contextPath %>/list.bo?cpage=1" class="btn btn-sm btn-secondary">목록가기</a>
        <!-- 로그인한 사용자가 게시글 작성자일 경우 -->

		<% if(loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())){ %>
        <a href="<%= contextPath %>/updateForm.bo?bno=<%= b.getBoardNo() %>" class="btn btn-sm btn-warning">수정하기</a>
        <a href="#" class="btn btn-sm btn-danger">삭제하기</a>
        <% } %>

    </div>

    <br>
        <div id="reply-area">

            <table border="1" align="center">
                <thead>
                    <tr>
                    <th>댓글작성</th>
                   	<%if(loginUser != null){ %>
                    <td>
                        <textarea id="replyContent" rows="3" cols="50" style="resize: none;"></textarea>
                    </td>
                    <td><button onclick="insertReply()">댓글등록</button></td>
                     <%} else{%>
                    <td>
                    <textarea rows="3" cols="50" style="resize: none;" readonly>로그인 후 이용가능한 서비스입니다.</textarea>
                    </td>
                    <td><button disabled>댓글등록</button><td>
                     <%} %>
                    </tr>
                </thead>

                <tbody>
                    
                </tbody>
            </table>
            
            <script>
            	$(function(){
            		selectReplyList();
            		
            		setInterval(selectReplyList, 1000); // 1초
            	})
            	
            	// ajax로 댓글 작성용 userNologinUser.getUserNo()면 로그인 안하면 어려움...
            	function insertReply(){
            		$.ajax({
            			url:"rinsert.bo",
            			data:{
            				content: $("#replyContent").val(),
            				bno:<%= b.getBoardNo()%>,
            			},
            			type:"post",
            			success:function(result){
            				if(result > 0 ){ // 댓글 작성이 성공하면 새로 갱신된 댓글 리스트 조회
            					selectReplyList();
            					$("#replyContent").val(""); // textarea 초기화
            				}
            				
            				
            			}, error:function(){
            				console.log("댓글작성용 ajax 통신 실패!");
            			}
            			
            		});
            	}
            	
            	
            	
            	
            	
            	function selectReplyList(){ //여러 형태로 해야하니까 객체형태로 
            		$.ajax({
            			url:"rlist.bo",
            			data:{bno:<%=b.getBoardNo()%>},
            			success : function(list){
            				
            				console.log(list);
            				
            				let result ="";
            				for(let i=0; i<list.length; i++){
            					result += "<tr>"
            							+ "<td>"+ list[i].replyWriter + "</td>"
            							+ "<td>"+ list[i].replyContent + "</td>"
            							+ "<td>"+ list[i].createDate + "</td>"
            							+"</tr>"
            				}
            				$("#reply-area tbody").html(result);
            				
            			}, error:function(){
            				console.log("댓글 목록 조회용 ajax 통신 실패");
            				
            			}
            		});
            		
            	}
            </script>

        </div>
        
        <br><br><br><br><br><br><br>
        

</div>

</body>
</html>