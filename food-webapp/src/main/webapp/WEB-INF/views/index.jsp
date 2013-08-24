<%-- 
    Document   : index
    Created on : Jul 26, 2013, 10:04:41 PM
    Author     : TWINS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="include.jsp" %> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="includes/main_menu.jsp" %>
        <div class="container">
        <h1>Hello World!</h1>

        <%
            if (request.getUserPrincipal() == null) {
                out.print("Anonymous");
            } else {
                out.print(request.getUserPrincipal().getName());
            }
        %>
        <%if (request.getUserPrincipal() == null) {%> 
        <a href="${pageContext.request.contextPath}/login" >Логин</a><br/>
        <% } else {%>
        <a href="${pageContext.request.contextPath}/logout" >Выйти</a><br/>
        <a href="${pageContext.request.contextPath}/profile" >Редактировать профиль</a><br/>
        <% }%>
        </div>
    </body>
</html>
