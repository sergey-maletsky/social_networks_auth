<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="snauth" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<snauth:includes/>

<p><a href="${contextPath}/">Вернуться на главную страницу</a></p>

<c:choose>
    <c:when test="${not empty error}">
                <p>
                    ${error}
                </p>
    </c:when>
    <c:when test="${not empty isLogin}">
        <p>Пользователь ${user.firstName} ${user.lastName} успешно вошел в систему</p>
    </c:when>
    <c:otherwise>
       <p>${user.firstName}, Вы успешно зарегистрировались. Ваш email: ${user.email}</p>
    </c:otherwise>
</c:choose>

</body>
</html>