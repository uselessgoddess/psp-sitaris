<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%--@elvariable id="admin" type="String"--%>
<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
	<title>Качалка :: Тестостерон</title>

	<c:url var="url__style_manager_css" value="${'/style-manager.css'}"/>
	<link rel="stylesheet" href="${url__style_manager_css}">
</head>
<body>
<div class="header primary-background">
	<h1 class="header__title">Качалка «Тестостерон»</h1>
</div>
<div class="content">
	<h2 class="page_title primary-color">Пользователи</h2>
	<table class="data_table">
		<tr class="secondary-background">
			<th>ID</th>
			<th>Имя</th>
			<th>Фото</th>
			<th></th>
		</tr>
		<%--@elvariable id="accounts" type="java.util.List"--%>
		<c:forEach var="account" items="${accounts}">
			<%--@elvariable id="account" type="by.vsu.ist.domain.Account"--%>
			<c:remove var="css_class"/>
			<tr class="${css_class}">
				<td>${account.id}</td>
				<td>${account.name}</td>
				<td><img alt="img" src="data:image/jpeg;base64,${account.base64Photo}" width="128" height="128"/></td>
				<td>
                	<c:url var="url__manager_account_edit" value="${'/manager/account/edit.html'}">
                		<c:param name="id" value="${account.id}"/>
                	</c:url>
                	<a href="${url__manager_account_edit}" class="text-link primary-color">изменить</a>
                </td>
			</tr>
		</c:forEach>
	</table>

    <c:if test="${not empty admin}">
        <div class="buttons_block">
            <c:url var="url__manager_coach_edit" value="${'/manager/account/edit.html'}"/>
            <a href="${url__manager_coach_edit}" class="button button__secondary">Новый пользователь</a>
        </div>
    </c:if>
</div>
</body>
</html>