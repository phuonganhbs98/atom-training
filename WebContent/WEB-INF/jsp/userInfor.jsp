<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>一覧</title>
<link href="./css/styles.css" rel="stylesheet" type="text/css">
</head>
<body class="content">
	<jsp:include page="_title.jsp">
		<jsp:param name="title" value="一覧" />
	</jsp:include>
	<form class="flex-container search-form" id="search-form" action="users" method="POST">
		<div style="width: 40%;">
			<table style="width: 100%;">
				<tr>
					<td class="login-label">姓：</td>
					<td class="login-input"><input type="text" name="familyName"
						value="${familyName }"></td>
				</tr>
				<tr>
					<td class="login-label">役職：</td>
					<td class="login-input"><select name="role" value="${role}">
					<option value="" ${role==""? "selected":""}>  </option>
					<c:forEach items="${roles}" var="r" varStatus="loop">
							<option value="${r.authorityId }" ${(role!=""&&role==r.authorityId)? "selected":""}>${r.authorityName} </option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
		</div>
		<div style="width: 40%;">
			<table style="width: 100%;">
				<tr>
					<td class="login-label label-search-fm">名：</td>
					<td class="login-input"><input type="text" name="firstName"
						value="${firstName }"></td>
				</tr>
			</table>
			<div class="flex-container search-btn-group">
				<input type="button" onclick="myFunction(1)" class="main-btn" value="リスト">
				<input type="button" onclick="myFunction(2)" class="main-btn" value="リスト2"> <input
					type="button"  class="main-btn" onclick="myFunction(0)" value="検索">
			</div>
		</div>
	</form>
	<table class="user-list">
		<tr>
			<th>No</th>
			<th>ユーザID</th>
			<th>氏名</th>
			<th>性別</th>
			<th>年齢</th>
			<th>役職</th>
			<th style="text-align: center; width: 230px"><a
					class="main-btn" href="/traning/users/signup"
					type="button" style="padding: 0 50px;"
					>登録</a></th>
		</tr>

		<c:forEach items="${users}" var="user" varStatus="loop">
			<tr>
				<td style="text-align: right; width: 5%">${loop.index + 1}</td>
				<td>${user.userId}</td>
				<td>${user.familyName} ${user.firstName}</td>
				<td style="width: 10%">${user.genderName}</td>
				<td style="text-align: right; width: 8%">${user.age}</td>
				<td style="width: 15%"><c:if test="${user.admin==1}">★</c:if>
					${user.roleName}</td>
				<td  style="text-align: center">
					<a href="/traning/users/edit?userId=${user.userId }"  type="button" class="small-btn main-btn">更新</a> |
					<a href="/traning/users/delete?userId=${user.userId }" type="button" class=" small-btn main-btn">削除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<script>
	function myFunction(type) {
		if(type==1){
			document.getElementById("search-form").action = "users/print";
			document.getElementById("search-form").target="_blank";
		}else if(type ==2){
			document.getElementById("search-form").action = "users/print2";
			document.getElementById("search-form").target="_blank";
		}
		else{
			document.getElementById("search-form").action = "users";
			document.getElementById("search-form").target="_self";
		}
		  document.getElementById("search-form").submit();
		}
	</script>
</body>
</html>