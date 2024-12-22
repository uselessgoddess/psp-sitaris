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
	<h2 class="page_title primary-color">Группы</h2>
	<table class="data_table">
		<tr class="secondary-background">
			<th>ID</th>
			<th>Имя тренера</th>
			<th>Число участников</th>
			<th>Список участников</th>
			<th>Время занятий</th>
			<th>Место занятий</th>
		</tr>
		<%--@elvariable id="groups" type="java.util.List"--%>
		<c:forEach var="group" items="${groups}">
			<%--@elvariable id="group" type="by.vsu.ist.domain.Group"--%>
			<c:remove var="css_class"/>
			<tr class="${css_class}">
				<td>${group.id}</td>
				<td>${group.coach.name}</td>
				<td>
				    ${group.participants.size()} / ${group.maxParticipants}
				    <c:if test="${group.maxParticipants - group.participants.size() <= 15}">
                        :(( мало места
				    </c:if>
				</td>
				<td>
                	<c:url var="url__manager_group_edit" value="${'/manager/group/edit.html'}">
                		<c:param name="id" value="${group.id}"/>
                	</c:url>
                	<a href="${url__manager_group_edit}" class="text-link primary-color">участники</a>
                </td>
                <td>${group.date}</td>
                <td>${group.place}</td>
			</tr>
		</c:forEach>
	</table>
	<%--@elvariable id="conflicts" type="java.util.List"--%>
	<c:forEach var="conflict" items="${conflicts}">
	    <%--@elvariable id="conflict" type="String"--%>
	    <ul>: ${conflict}</ul>
	</c:forEach>
	<div class="buttons_block">
		<c:url var="url__manager_account_edit" value="${'/manager/group/edit.html'}"/>
		<a href="${url__manager_account_edit}" class="button button__secondary">Открыть счёт</a>
	</div>
</div>
</body>
</html>