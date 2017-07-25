<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<%-- 	<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
			◀ 
	<% }else{ %>
			<a href="javascript:fncGetUserList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
	<% } %>
	
	<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
			<a href="javascript:fncGetUserList('<%=i %>');"><%=i %></a>
	<% 	}  %>
	
	<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage()){ %>
			 ▶
	<% }else{ %>
			<a href="javascript:fncGetUserList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
	<% } %> --%>
	
	
	<c:if test="${resultPage.currentPage <= resultPage.pageUnit}">
		◀
	</c:if>
	<c:if test="${resultPage.currentPage > resultPage.pageUnit }">
		<a href="javascript:fncGetList('${resultPage.currentPage-1}')">◀ </a>
	</c:if>
	
	<c:forEach var="page" begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetList('${ page }');">${ page }</a>
	</c:forEach>
	
	<c:if test="${resultPage.currentPage >= resultPage.maxPage}">
		▶
	</c:if>
	<c:if test="${resultPage.currentPage < resultPage.maxPage }">
		<a href="javascript:fncGetList('${resultPage.currentPage+1}')">▶</a>
	</c:if>