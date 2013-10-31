<%-- 
    Document   : restaurateur
    Created on : Sep 8, 2013, 10:26:10 PM
    Author     : daniyar.artykov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../includes/include.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Кабинет ресторатора</title>
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-formhelpers.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resources/js/jquery/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/custom/registration.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap-formhelpers-phone.format.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap-formhelpers-phone.js"></script>
    </head>
    <body>
        <%@include file="../includes/main_menu.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-xs-1 col-sm-5 col-md-3"></div>
                <div class="col-xs-10 col-sm-5 col-md-6">
                    <h1>Кабинет ресторатора</h1>
                    <a class="btn btn-primary" href="restaurateur/add">Добавить заведение</a>
                </div>
            </div>
        </div>
    </body>
</html>
