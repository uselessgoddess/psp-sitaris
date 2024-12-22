<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@taglib prefix="fn" uri="jakarta.tags.functions"%>
<%--@elvariable id="coach" type="by.vsu.ist.domain.Account"--%>
<c:choose>
	<c:when test="${not empty coach}">
		<c:set var="title" value="Изменения пользователя"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Добавление пользователя"/>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
	<title>Гринготтс :: ${title}</title>

	<c:url var="url__style_manager_css" value="${'/style-manager.css'}"/>
	<link rel="stylesheet" href="${url__style_manager_css}">
</head>
<body>
<div class="header primary-background">
	<h1 class="header__title">Качалка «Тестостерон»</h1>
</div>
<div class="content">
	<h2 class="page_title primary-color">${title}</h2>
	<c:url var="url__manager_coach_save" value="${'/manager/coach/save.html'}"/>
	<form action="${url__manager_coach_save}" method="post" class="form" enctype="multipart/form-data">
		<c:if test="${not empty coach}">
			<input type="hidden" name="id" value="${coach.id}">
		</c:if>
		<div class="input_block">
			<label for="name-input">Имя пользователя:</label>
			<input type="text" id="name-input" name="name" value="${coach.name}">
		</div>
		<div class="input_block">
			<label for="photo-input">Аватар пользователя:</label>
			<input type="file" id="photo-input" name="photo" value="${coach.photo}">
		</div>
		<div class="buttons_block">
			<button type="submit" class="button button__primary">Сохранить</button>
			<c:url var="url__manager_coach_list" value="${'/manager/coach/list.html'}"/>
			<a href="${url__manager_coach_list}" class="button button__secondary">Назад</a>
		</div>
	</form>
</div>
</body>
</html>