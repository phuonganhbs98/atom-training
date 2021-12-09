<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="flex-container">
		<div>
			<p class="title">
				<c:choose>
					<c:when test="${param.title=='login'}">ログイン</c:when>
					<c:when test="${param.title=='list'}">一覧</c:when>
					<c:otherwise>タイトル</c:otherwise>
				</c:choose>
			</p>
		</div>
		<div class="flex-container link">
			<c:choose>
				<c:when test="${param.title=='list'}">
					<a href="/traning">役職別集計</a>
					<a href="/traning/logout">ログアウト</a>
				</c:when>
				<c:when test="${param.title=='statistic'}">
					<a href="/traning">一覧</a>
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>

		</div>

	</div>
</div>