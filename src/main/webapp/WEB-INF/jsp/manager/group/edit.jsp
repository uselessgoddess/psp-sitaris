<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@taglib prefix="fn" uri="jakarta.tags.functions"%>
<%--@elvariable id="group" type="by.vsu.ist.domain.Group"--%>
<c:choose>
	<c:when test="${not empty group}">
		<c:set var="title" value="Изменение пользователя"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Открытие пользователя"/>
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
	<c:url var="url__manager_group_save" value="${'/manager/group/save.html'}"/>
	<c:url var="url__manager_group_delete" value="${'/manager/group/delete.html'}"/>
	<table class="data_table">
    	<tr class="secondary-background">
    		<th>ID</th>
    		<th>Список участников</th>
    	</tr>
    	<%--@elvariable id="participants" type="java.util.List"--%>
    	<c:forEach var="account" items="${group.participants}">
    		<%--@elvariable id="account" type="by.vsu.ist.domain.Account"--%>
    		<c:remove var="css_class"/>
    		<tr class="${css_class}">
    			<td>${account.id}</td>
    			<td>${account.name}</td>
    		</tr>
    	</c:forEach>
    </table>
	<form action="${url__manager_group_save}" method="post" class="form" enctype="multipart/form-data">
    	<c:if test="${not empty group}">
    		<input type="hidden" name="id" value="${group.id}">
    	</c:if>
    	<div class="input_block">
        	<label for="coach-id">ID тренера:</label>
        	<input required type="text" id="coach-id" name="coach-id" value="${group.coach.id}">
        </div>
    	<div class="input_block">
        	<label for="user-id">ID ученика:</label>
        	<input type="text" id="user-id" name="user-id" value="">
        </div>
        <div class="input_block">
        	<label for="max-participants">Число участников:</label>
        	<input required type="number" id="max-participants" name="max-participants" value="${group.maxParticipants}">
        </div>
        <div class="input_block">
        	<label for="date">Дата:</label>
        	<input required type="text" id="date" name="date" value="${group.date}">
        </div>
        <div class="input_block">
        	<label for="place">Место:</label>
        	<input required type="text" id="place" name="place" value="${group.place}">
        </div>
    	<div class="buttons_block">
    		<button type="submit" class="button button__primary">Изменить</button>
    		<c:url var="url__manager_group_list" value="${'/manager/group/list.html'}"/>
    	</div>
    </form>
    <c:if test="${not empty group}">
        <form action="${url__manager_group_delete}" method="post" class="form" enctype="multipart/form-data">
            <div class="buttons_block">
                <button type="submit" class="button button__primary">Удалить</button>
                <c:url var="url__manager_group_list" value="${'/manager/group/list.html'}"/>
            </div>
        </form>
    </c:if>
    <a href="${url__manager_group_list}" class="button button__secondary">Назад</a>
</div>
</body>
</html>