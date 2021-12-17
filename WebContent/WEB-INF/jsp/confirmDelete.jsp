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
		<jsp:param name="title" value="delete" />
	</jsp:include>
	<h2>以下のデータを削除してよろしいですか。</h2>
	<table>
		<tr>
			<td>ユーザID:</td>
			<td>${user.userId }</td>
		</tr>
		<tr>
			<td>氏名:</td>
			<td>${user.familyName}${user.firstName}</td>
		</tr>
	</table>
	<form action="/traning/users/delete" method="post">
		<input type="submit" value="削除" class="main-btn">
	</form>
</body>
</html>