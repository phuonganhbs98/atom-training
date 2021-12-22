<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>削除</title>
<link href="../css/styles.css" rel="stylesheet" type="text/css">
</head>
<body class="content">
	<jsp:include page="_title.jsp">
		<jsp:param name="title" value="削除" />
	</jsp:include>
	<p style="margin-left:3%; font-size: 30px">以下のデータを削除してよろしいですか。</p>
	<table style="margin: 0 20%; text-align: center;width: 40%;">
		<tr>
			<td class="login-label">ユーザID:</td>
			<td class="login-input">${user.userId }</td>
		</tr>
		<tr>
			<td class="login-label">氏名:</td>
			<td class="login-input">${user.familyName} ${user.firstName}</td>
		</tr>
	</table>
	<form action="/traning/users/delete" method="post">
		<input type="submit" value="削除" class="main-btn" style="margin: 3% 25%; ">
	</form>
</body>
</html>