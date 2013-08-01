<%-- 
    Document   : login
    Created on : Jul 28, 2013, 11:25:14 AM
    Author     : TWINS
--%>
<%@include file="include.jsp" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <center>
        <h1>Login form!</h1>
    <form:form method="post" id="form" action="${pageContext.request.contextPath}/save" commandName="user" >
        <form:input path="name"  id="name" placeholder="Имя" /><br/>
        <form:input path="surname"  id="surname" placeholder="Фамилия" /><br/>
        <form:input path="email"  id="email" placeholder="Email" /><br/>
        <form:input path="password"  id="password" placeholder="Пароль" /><br/>
        <input type="submit" value="Войти" /><br/>
    </form:form>
        </center>
    </body>
</html>
