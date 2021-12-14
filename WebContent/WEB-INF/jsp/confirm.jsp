<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="content">
<jsp:include page="_title.jsp">
		<jsp:param name="title" value="${title=='登録'?signup:edit }" />
	</jsp:include>
	<p class="title" style="margin-left: 8%">${title}してよろしいですか。</p>
	<a href="/traning/users" class="main-btn"　type="button"
		style="padding: 0 20px; margin-left: 15%">一覧へ戻る</a>
</body>
</html>