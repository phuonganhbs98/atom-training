<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<div style="padding: 0 5%">
		<form action="/traning/users/statistic" method="post" style="text-align: right;">
			<input type="submit" value="集計" class="main-btn" style="margin-right: 10px">
		</form>
		<table class="user-list statistic">
			<tr>
				<th style="text-align: left">No</th>
				<th style="text-align: left">役職</th>
				<th>男の人数</th>
				<th>女の人数</th>
				<th style="color: red;">未登録の人数</th>
				<th>年齢別人数<br /> (0-19)
				</th>
				<th>年齢別人数<br /> (20以上)
				</th>
				<th>年齢別人数<br /> (未登録)
				</th>
			</tr>
			<c:forEach items="${result}" var="r" varStatus="loop">
				<tr>
					<td style="text-align: right; width: 5%">${loop.index + 1}</td>
					<td style="text-align: left">${r.authorityName}</td>
					<td>${r.totalMale==null?0:r.totalMale}</td>
					<td>${r.totalFemale==null?0:r.totalFemale}</td>
					<td style="color: red;">${r.totalUnknown==null?0:r.totalUnknown}</td>
					<td>${r.totalAgeSmaller19==null?0:r.totalAgeSmaller19}</td>
					<td>${r.totalAgeGreater20==null?0:r.totalAgeGreater20}</td>
					<td>${r.totalUnknownAge==null?0:r.totalUnknownAge}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>