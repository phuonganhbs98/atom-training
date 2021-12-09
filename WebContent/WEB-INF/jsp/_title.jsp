<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<h1 class="title">
		<c:choose>
			<c:when test="${param.title=='login'}">ログイン</c:when>
			<c:when test="${param.title=='list'}">一覧</c:when>
			<c:otherwise>タイトル</c:otherwise>
		</c:choose>
	</h1>
</div>