<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>終了</title>
<link href="../css/styles.css" rel="stylesheet" type="text/css">
</head>
<body class="content">
	<jsp:include page="_title.jsp">
		<jsp:param name="title" value="finish" />

		<jsp:param name="type" value="${title}" />
	</jsp:include>
	<p class="title" style="margin-left: 8%">${title}完了しました。</p>
	<a href="/traning/users" class="main-btn"
		style="padding: 0 20px; margin-left: 15%">一覧へ戻る</a>
</body>
</html>