<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>jsp:include</h3>
    <p>또 다른 페이지를 포함 할 때 쓰는 태그</p>
    
    
    <h4>1. 기존의 include 지시어 이용한 방식(정적 include 방식 == 컴파일시 애초에 포함되어있는 형태)</h4>
    
    <%-- 
    <%@ include file="footer.jsp" %>
    <br>
    
    특징 : include하고 있는 페이지상에 선언되어있는 변수들 현재 이 페이지에서도 사용 가능 <br>
    include한 페이지의 year 변수값 : <%= year %> <br><br>
    
    => 단, 현재 이 페이지에서 동일한 이름의 변수를 선할 수 없음 <br>
    <!-- int year = 2026; => 이런 코드가 안됨 이유는 위에 footer.jsp에 통으로 있어서 안됨  -->
    --%>
    
    <h4>2. JSP 표준 액션 태그를 이용한 방식(동적 inClude 방식 == 런타임시 포함되는 형태)</h4>
    
    <jsp:include page ="footer.jsp"/> <%--</j2sp:include> --%>
    <br>
    
    특징1: include 하고 있는 페이지에 선언된 변수를 공유하지 않음 <br>
    => 동일한 이름의 변수 재선언 가능
	<% int year = 2026; %>
	
	특징2: 포함시 inClude하는페이지로 값 전달할 수 있음 <br>
	<jsp:include page="footer.jsp">
		<jsp:param value="Hello" name="test"/>
	</jsp:include>
	
	<jsp:include page="footer.jsp">
		<jsp:param value="Bye" name="test"/>
	</jsp:include>
	
	
</body>
</html>
