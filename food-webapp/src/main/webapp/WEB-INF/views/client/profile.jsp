<%-- 
    Document   : profile
    Created on : Jul 31, 2013, 8:23:36 PM
    Author     : TWINS
--%>
<%@include file="../include.jsp" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body >
         <center>
        <h1>Профиль:</h1>
    <form:form  method="post" id="form" action="${pageContext.request.contextPath}/save" commandName="user" >
        <form:hidden path="id"  />
        <form:input path="name"  id="name" placeholder="Имя" /><br/>
        <form:input path="surname"  id="surname" placeholder="Фамилия" /><br/>
        <form:input path="email"  id="email" placeholder="Email" /><br/>
        <input type="submit" value="Сохранить" /><br/>
    </form:form>
        </center>
    </body>
</html>
