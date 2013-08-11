<%-- 
    Document   : index
    Created on : Jul 26, 2013, 10:04:41 PM
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
        <h1>Hello World!</h1>
       
          <%
                                if(request.getUserPrincipal()==null)
                                  out.print("Anonymous");
                                else
                                    out.print(request.getUserPrincipal().getName());
                                    
                                 %>
                               <%if(request.getUserPrincipal()==null){%> 
        <a href="${pageContext.request.contextPath}/login" >Логин</a><br/>
        <% } else { %>
         <a href="${pageContext.request.contextPath}/logout" >Выйти</a><br/>
         <a href="${pageContext.request.contextPath}/profile" >Редактировать профиль</a><br/>
         <% }%>
    </body>
</html>
