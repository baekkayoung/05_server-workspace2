<%@page import="com.kh.board.model.vo.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
		Notice n = (Notice)request.getAttribute("notice");
    	// 글번호, 제목, 내용, 작성자아이디, 작성일자
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<style>
    .outer{
        background-color: black;
        color: white;
        width: 1000px;
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }

</style>
</head>
<body>
    <%@ include file ="../common/menubar.jsp"%>

    <div class="outer" align="center" >
        <br>
        <h2 align="center">공지사항 상세보기</h2>
        <br>

        <table id="detail-area" border="1">
            <tr>
                <th width="70">제목</th>
                <td colspan="3" width="430"><%= n.getNoticeTitle() %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%= n.getNoticeWriter() %></td>
                <th>작성일</th>
                <td><%= n.getCreateDate()%></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 150px;"><%= n.getNoticeContent() %></p>
                </td> 
            </tr>
        </table>
        <br><br>

        <div>		
            <a href="<%= contextPath%>/list.no" class="btn btn-sm btn-secondary">목록가기</a>
            <% if(loginUser != null && loginUser.getUserId().equals(n.getNoticeWriter())){ %>
            <!-- 현재 로그인을 한 사용자가 해당 글을 쓴 본인일 경우 -->
            <a href="<%= contextPath %>/updateForm.no?num=<%= n.getNoticeNo() %>"  class="btn btn-sm btn-warning">수정하기</a>
            <a href="<%= contextPath %>/deleteForm.no?num=<%= n.getNoticeNo() %>" class="btn btn-sm btn-danger">삭제하기</a>
            <!-- 
            본격적인 삭제요청!
            URL MAPPING 마음대로 작성
            성공시 - 목록페이미 ALERT 성공적으로 공지삭제했습니다
            실패시 - 에러페이지 이동
            
             -->
            <% } %>
        </div>

    </div>



</body>
</html>