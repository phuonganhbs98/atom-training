<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>集計</title>
<link href="../css/styles.css" rel="stylesheet" type="text/css">
</head>
<body class="content">
	<jsp:include page="_title.jsp">
		<jsp:param name="title" value="statistic" />
	</jsp:include>
<div style="text-align: right;">
	<button type="button" class="main-btn">集計</button>
	</div>
	<table class="user-list">
		<tr>
			<th>No</th>
			<th>役職</th>
			<th>男の人数</th>
			<th>女の人数</th>
			<th>未登録の人数</th>
			<th>年齢別人数 (0-19)</th>
			<th>年齢別人数 (20以上)</th>
			<th>年齢別人数 (未登録)</th>
		</tr>
	</table>
</body>
</html>