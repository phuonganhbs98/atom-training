<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${edit?"更新":"登録"} </title>
<link href="../css/styles.css" rel="stylesheet" type="text/css">
</head>
<body class="content">
	<jsp:include page="_title.jsp">
		<jsp:param name="title" value="${edit?'edit':'signup'}" />
	</jsp:include>
	<div style="margin-bottom: 15px; text-align: center; width: 80%;">
		<c:if test="${errorString!=null}">
			<span class="show error_message">※ ${errorString}</span>
		</c:if>
		<c:if test="${errorString==null}">
			<span class="hide error_message">※ ${errorString}</span>
		</c:if>
	</div>
	<form action="/traning/users/${ edit?'edit':'signup'}" method="POST"
		style="padding: 0 3%">
		<div class="flex-container search-form">
			<div style="width: 40%;">
				<table style="width: 100%;">
					<tr>
						<td class="login-label">ユーザID：</td>
						<td class="login-input"><input type="text" name="userId"
							value="${user.userId }" maxlength="8" ${ edit?"readonly":""}></td>
					</tr>
					<tr>
						<td class="login-label">姓：</td>
						<td class="login-input"><input type="text" name="familyName"
							value="${user.familyName }" maxlength="10"></td>
					</tr>
					<tr>
						<td class="login-label">性別：</td>
						<td class="login-input"><select name="gender">
								<option value="" ${(user.genderId==null)? "selected":""} ></option>
								<c:forEach items="${genders}" var="g" varStatus="loop">
									<option value="${g.genderId}"
										${(user.genderId!=null&&user.genderId==g.genderId)? "selected":""}>${g.genderName}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td class="login-label">役職：</td>
						<td class="login-input"><select name="role">
								<option value=""></option>
								<c:forEach items="${roles}" var="r" varStatus="loop">
									<option value="${r.authorityId }"
										${(user.authorityId!=null&&user.authorityId==r.authorityId)? "selected":""}>${r.authorityName}</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
			</div>
			<div style="width: 40%;">
				<table style="width: 100%;">
					<tr>
						<td class="login-label">パスワード：</td>
						<td class="login-input"><input type="password"
							name="password" value="${user.password }" maxlength="8"></td>
					</tr>
					<tr>
						<td class="login-label">名：</td>
						<td class="login-input"><input type="text" name="firstName"
							value="${user.firstName }" maxlength="10"></td>
					</tr>
					<tr>
						<td class="login-label">年齢：</td>
						<td class="login-input"><input type="number" name="age"
							value="${user.age }"></td>
					</tr>
					<tr>
						<td class="login-label">管理者：</td>
						<td class="login-input"><input type="checkbox" id="admin"
							name="admin" value="1"
							${(user.admin!=null && user.admin==1)?"checked":""}></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="flex-container login-btn"
			style="text-align: center; justify-content: center; margin-right: 20%;">
			<a type="button" class="main-btn" href="/traning/users"
				style="margin-right: 60px">戻る</a> <input id="signup-cf"
				type="button" value="${ edit?'更新':'登録'}" class="main-btn">
		</div>

		<div id="myModal" class="modal">

			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-header">
					<span class="close">&times;</span>
					<h2>確認</h2>
				</div>
				<div class="modal-body">
					<p>${ edit?"更新":"登録"}してよろしいですか。</p>
					<input type="submit" value="${ edit?'更新':'登録'}" class="main-btn">
				</div>
				<!-- <div class="modal-footer">
      <h3>Modal Footer</h3>
    </div> -->
			</div>

		</div>
	</form>

	<script>
		// Get the modal
		var modal = document.getElementById("myModal");

		// Get the button that opens the modal
		var btn = document.getElementById("signup-cf");

		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];

		// When the user clicks the button, open the modal
		btn.onclick = function() {
			modal.style.display = "block";
		}

		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
			modal.style.display = "none";
		}

		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}
	</script>
</body>
</html>