<%-- 
    Document   : error403
    Created on : Sep 8, 2013, 9:34:52 PM
    Author     : daniyar.artykov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ошибка!</title>
    </head>
    <body>
    <c:if test="${not empty param.login_error}">
        <div class="error">${SPRING_SECURITY_LAST_EXCEPTION.message}</div>
    </c:if>
</body>
</html>
