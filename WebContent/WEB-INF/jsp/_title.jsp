<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="flex-container">
		<div>
			<p class="title">
				<c:choose>
					<c:when test="${param.title=='finish'}">
						${param.type }完了
						</c:when>
					<c:otherwise>${param.title} </c:otherwise>
				</c:choose>
			</p>
		</div>
		<div class="flex-container link">
			<c:choose>
				<c:when test="${param.title=='一覧'}">
					<a href="/traning/users/statistic">役職別集計</a>
					<a href="/traning/logout">ログアウト</a>
				</c:when>
				<c:when test="${param.title=='役職別集計'}">
					<a href="/traning">一覧</a>
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
		</div>

	</div>
</div>