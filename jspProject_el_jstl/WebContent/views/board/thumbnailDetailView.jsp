<%@page import="com.kh.board.model.vo.Attachment"%>
<%@page import="com.kh.board.model.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        height: 800px;
        margin: auto;
        margin-top: 50px;
    }

    .detail-area td{
        border: 1px solid white;
        text-align: center;
    }

</style>
</head>
<body>
    <jsp:include page="../common/menubar.jsp"/>
    

    <div class="outer">
        <br>
        <h2 align="center">사진게시판 상세보기</h2>
        <br>

        <table class="detail-area" align="center">
        <tr>
            <td width="70">제목</td>
            <td colspan="3" width="600">${b.boardTitle}</td>
        </tr>
        <tr>
            <td>작성자</td>
            <td>${b.boardWriter}</td>
            <td>작성일</td>
            <td>${b.create_date}</td>

        </tr>
        <tr>
            <td>내용</td>
            <td colspan="3"s>
                <p style="height: 50px;">${b.boardContent}</p>
            </td>
        </tr>
        <tr>
            <td>대표사진</td>
            <td colspan="3">
                <div>
                    <img src="${pageContext.request.contextPath}/${list[0].filePath}${list[0].changeName}"  width="500px" height="300px">
                </div>
            </td>
        </tr>
        <tr>
            <td>상세사진</td>
            <td colspan="3">
                <div>
                	<c:forEach var="at" items="${list}" begin="1">
                            <img src="${pageContext.request.contextPath}/${at.filePath}${at.changeName}" 
                                 width="200px" height="150px">
                        </c:forEach>
                </div>
            </td>
        </tr>
    </table>
    <br>
    <div align="center">
    	<a href="" class="btn btn-sm btn-secondary" >목록가기</a>
    </div>
    
   </div>

</body>
</html>