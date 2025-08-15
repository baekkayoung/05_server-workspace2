<%@page import="com.kh.model.vo.Person"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    <%-- c:out , c:if / core라이브러리의 uri --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>JSTL Core Library</h1>
	
	<h3>1. 변수(속성 == attribute)</h3>
	<pre>
	* 변수 선언과 동시에 초기화 (c:set var="" value="" [scope=""])
	- 변수 선언하고 초기값을 대입해두는 기능을 제공
	- 해당 변수를 어떤 scope에 담아둘건지 지정 가능 (생략시 기본적으로 pageScope에 담김)
	=> 즉 , 해당 scope에 setAttribute를 통해서 key-value형태로 데이터를 담아놓는 거라고 생각하면 됨
	=> c:set으로 선언된 변수는 EL로 접근해서 사용 가능 (스크립팅 원소로는 접근 불가!!)
	
	- 변수 타입을 별도로 정하지 않음
	- 초기값을 반드시 지정해둬야됨!
	</pre>
	
	<c:set var="num1" value="10"/> 				   <!-- pageContext.setAttribute("num1", "10"); -->
	<c:set var="num2" value="20" scope="request"/> <!-- request.setAttribute("num2", "20") -->
	
	num1 변수값 : ${num1} <br>
	num2 변수값 : ${num2} <br>
	
	<c:set var="result" value="${ num1 + num2} " scope="session"/>	
	result 변수값 : ${result} <br><br>
	
	${ pageScope.num1 } <br>
	${ requestScope.num2 } <br>
	${ sessionScope.result } <br>
	
	<!-- value 속성 대신에 시작태그와 종료태그 사이에 초기값 지정 가능 -->
	<c:set var="result" scope="request">9999</c:set>
	${ requestScope.result } <br>
	
	<hr>
	
	<pre>
	*변수 삭제 (c:remove var="제거하고자하는변수명" [scope=""])
	- 해당 scope영역에서 해당 변수를 찾아 제거하는 태그
	- scope 지정 생략시 모든 scope에서 해당변수 다 찾아서 제거함
	- 즉, 해당 scope에 removeAttribute를 통해 제거하는거라고 생각하면 됨
	</pre>
	
	삭제 전 result : ${ result } <br>
	
	1) 특정 scope 지정해서 삭제 <br>
	<c:remove var="result" scope="request"/>
	request 삭제후 result : ${ result } <br><br>
	
	2) 모든 scope에서 삭제 <br>
	<c:remove var="result"/>
	모두 삭제 후 result :${ result } <br><br>
	
	<pre>
	*변수(데이터) 출력 (c:out value="출력하고자하는값" [default="기본값"] [escapeXml="true|false"])
	- 데이터를 출력하고자할 때 사용하는 태그
	
	</pre>
	<c:out value="${result}"/> <br>
	<c:out value="${result}" default="없음"/> <br><br>
	
	<c:set var="outTest" value="<b>출력테스트</b>"/>
	<c:out value="${ outTest }"/> <br> <!-- escapeXml 생략시 기본값이 true == 태그로 해석이 안됨 (문자열로 취급) -->
	<c:out value="${ outTest }" escapeXml="false"/> <br>
	
	<hr>
	<h3>2. 조건문 - if(c:if test= "조건식")</h3>
	<pre>
	-Java의 if문과 비슷한 역할을 하는 태그
	- 조건식은 test 속성에 작성 (단, EL 구문으로 기술해야 됨)
	</pre>
	
	<%-- 
	<% if( num1 > num2){ %>
	<% } %>
	--%>
	
	<c:if test="${num1 gt num2}">
		<b>num1이 num2보다 큽니다.</b>
	</c:if>
	
	<c:if test="${num1 le num2}">
		<b>num1이 num2보다 작습니다</b>
	</c:if>
	
	<br>
	
	<c:set var="str" value="안녕하세요"/>
	
	<%-- 
	<% if(str.equals("안녕하세요")) { %>
	<%} %>
	--%>
	
	<c:if test="${ str eq '안녕하세요'}">
		<h4>Hello World</h4>
	</c:if>
	
	<c:if test="${ str ne '안녕하세요'}">
		<h4>Bye World</h4>
	</c:if>
	<br>
	
	<h3>3. 조건문 - Choose (c:choose, c:when, c:otherwise)</h3>
	<pre>
	- Java의 if-else, if-else if문과 비슷한 역할을 하는 태그
	- 각 조건들 c:chooese의 하위요소로 c:when을 통해서 작성 (else문역할= c:otherwise)
	</pre>
	
	<%-- 
	<% if(num1>20) {%>
	<% }else if {(num1 >=10){ %>
	<%}else{ %>
	<%} %>
	--%>
	
	<c:choose>
		<c:when test="${num1 gt 20}">
			<!-- num1이 20보다 클 경우 -->
			<b>처음뵙겠습니다.</b>
		</c:when>
		<c:when test="${num1 ge 10}">
			<b>다시 만나서 반갑습니다.</b>
		</c:when>
		<c:otherwise>
			<b>안녕하세요</b>
		</c:otherwise>
	</c:choose>
	
	<hr>
	<h3>4. 반복문 - forEach</h3>
	<pre>
	for loop문 - (c:forEach var="변수명" begin="초기값" end="끝값" [step="반복시증가값"]) 
	for each문 - (c:forEach var="변수명" items="순차적으로 접근하고자하는 배열|컬렉션명")
	
	var 속성으로 선언된 변수에 접근하고자 할 때는 반드시 EL 구문으로 접근
	</pre>
	<%-- 
	<% for(int i=1; i<=10; i++){ %>
	<% } %>
	--%>
	
	<c:forEach var="i" begin="1" end="10" step="2">
		반복확인 : ${i} <br>
	</c:forEach>
	
	<c:forEach var="i" begin="1" end="6">
		<h${ i }> 태그 안에서도 적용 가능 </h${ i }>
	</c:forEach>
	
	<c:set var="colors">
		red,yellow,green,pink
	</c:set>
	
	colors 변수 값 : ${ colors } <br>
	
	<ul>
		<c:forEach var="c" items="${ colors }">
			<li style ="color:${c}">${ c }</li>
		</c:forEach>
		
	</ul>
		
		<% 
		ArrayList<Person> pList = new ArrayList<Person>();
		pList.add(new Person("차은우", 20, "남자"));
		pList.add(new Person("장원영", 21, "여자"));
		pList.add(new Person("박효신", 45, "남자"));

		%>
		
		<c:set var="pList" value="<%= pList %>" scope="request"/>
		
		<table border=1>
		
			<thead>
				<tr>
					<th>이름</th>
					<th>나이</th>
					<th>성별</th>
				</tr>
			</thead>
			<tbody>
				<%-- 
				<% if(pList.isEmpty()){ %>
			
				<%}else{ %>
					<% for(Person p : pList){ %>
					<%} %>
				<%} %>
	
				--%>
				
				<c:choose>
					<c:when test="${ empty pList}">
						<tr>
							<td colspan="3">조회된 사람이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="p" items="${ pList }">
							<tr>
								<td>${ p.name }</td>
								<td>${ p.age }</td>
								<td>${ p.gender }</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		
		<hr>
		<h3>5. url, 쿼리스트링 관련 - url, param</h3>
		<pre>
		- url경로를 생성하고, 쿼리스트링을 정의해둘 수 있는 태그
		
		c: url var="변수명" value="요청할url"
			c:param name="키값" value="전달값"/
			c:param name="키값" value="전달값"/
			c:param name="키값" value="전달값"/
		/c: url
		</pre>
		
		<a href="list.do?cpage=1&num=2">기존 방식</a> <br>
		
		<c:url var="ttt" value="list.do">
			<c:param name="cpage" value="1"/>
			<c:param name="num" value="2"/>
		</c:url>
		
		<a href="${ttt}">c:url을 이용한 방식</a>
	
</body>
</html>