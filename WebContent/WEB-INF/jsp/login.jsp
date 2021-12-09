<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="utf-8">
<title>ログイン</title>
<link href="./css/styles.css" rel="stylesheet" type="text/css">

</head>
<body>
	<jsp:include page="_title.jsp">
		<jsp:param name="title" value="login" />
	</jsp:include>
	<div style="margin: auto; text-align: center;">
		<c:if test="${errorString!=null}">
			<span class="show error_message">※ ${errorString}</span>
		</c:if>
		<c:if test="${errorString==null}">
			<span class="hide error_message">※ ${errorString}</span>
		</c:if>
		<div id="login_form">
			<form action="login" method="POST">
				<table style="margin: auto; text-align: center;width: 40%;">
					<tr>
						<td class="login-label">ユーザID：</td>
						<td class="login-input"><input type="text" name="userID"
							maxlength="8"></td>
					</tr>
					<tr>
						<td class="login-label">パスワード：</td>
						<td class="login-input"><input type="password"
							name="password" maxlength="8"></td>
					</tr>
				</table>
				<input type="submit" value="ログイン" class="main-btn login-btn">
			</form>
		</div>
	</div>
</body>
</html>