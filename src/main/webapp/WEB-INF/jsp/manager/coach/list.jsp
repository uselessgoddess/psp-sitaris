<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
	<title>Гринготтс :: Банковские счета</title>
	<c:url var="url__style_css" value="${'/style.css'}"/>
	<link rel="stylesheet" href="${url__style_css}">
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
		<%--@elvariable id="coaches" type="java.util.List"--%>
		<c:forEach var="coach" items="${coaches}">
			<%--@elvariable id="coach" type="by.vsu.ist.domain.coach"--%>
			<c:remove var="css_class"/>
			<tr class="${css_class}">
				<td>${coach.id}</td>
				<td>${coach.name}</td>
				<td><img alt="img" src="data:image/jpeg;base64,${coach.base64Photo}" width="128" height="128"/></td>
				<td>
                	<c:url var="url__manager_coach_edit" value="${'/manager/coach/edit.html'}">
                		<c:param name="id" value="${coach.id}"/>
                	</c:url>
                	<a href="${url__manager_coach_edit}" class="text-link primary-color">изменить</a>
                </td>
			</tr>
		</c:forEach>
	</table>
	<div class="buttons_block">
		<c:url var="url__manager_coach_edit" value="${'/manager/coach/edit.html'}"/>
		<a href="${url__manager_coach_edit}" class="button button__secondary">Открыть счёт</a>
	</div>
</div>
</body>
</html>