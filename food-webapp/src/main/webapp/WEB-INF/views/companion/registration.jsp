<%--
    Document   : login
    Created on : Jul 28, 2013, 11:25:14 AM
    Author     : TWINS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../includes/include.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Регистрация партнера</title>
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
                    <h1>Регистрация партнера</h1>
                    <form:form method="post" id="form" action="${pageContext.request.contextPath}/companion/registration" 
                               commandName="user" >
                        <div class="bs-example form-horizontal" role="form">
                            <div class="form-group">
                                <label for="name" class="col-lg-4 control-label">Имя:</label>
                                <div class="col-lg-7">
                                    <form:input path="name" class="form-control" 
                                                id="name" placeholder="Имя" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="surname" class="col-lg-4 control-label">Фамилия:</label>
                                <div class="col-lg-7">
                                    <form:input path="surname" class="form-control" 
                                                id="surname" placeholder="Фамилия" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-lg-4 control-label">Почта *:</label>
                                <div class="col-lg-7">
                                    <form:input path="email" class="form-control" 
                                                id="email" placeholder="Email" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-lg-4 control-label">Пароль *:</label>
                                <div class="col-lg-7">
                                    <form:password path="password" class="form-control" 
                                                   id="password" placeholder="Пароль" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="repassword" class="col-lg-4 control-label">Повтор&nbsp;пароля *:</label>
                                <div class="col-lg-7">
                                    <input class="form-control" name="repassword"
                                           id="repassword" placeholder="Повтор пароля" 
                                           type="password" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="col-lg-4 control-label">Телефон *:</label>
                                <div class="col-lg-7">
                                    <input class="bfh-phone form-control" name="phone" 
                                           data-format="+7 (ddd) ddd-dd-dd" id="phone" type="text" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-offset-4 col-lg-7">
                                    <button type="submit" data-loading-text="подождите" 
                                            id="btn" class="btn btn-primary">
                                        Регистрация
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
                <div class="col-xs-1 col-sm-5 col-md-2"></div>
            </div>
        </div>
    </body>
</html>
