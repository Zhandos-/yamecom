<%-- 
    Document   : login
    Created on : Jul 28, 2013, 11:25:14 AM
    Author     : TWINS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="include.jsp" %> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            $(document).ready(function() {
                $('#btn').button();
                $('#btn').click(function() {
                    $(this).button('loading');
                });
            });
        </script>
    </head>
    <body>
        <%@include file="includes/main_menu.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-xs-1 col-sm-5 col-md-3"></div>
                <div class="col-xs-10 col-sm-5 col-md-6">
                    <h1>Регистрация</h1>
                    <form:form method="post" id="form"  action="${pageContext.request.contextPath}/registration" commandName="user" >
                        <div class="bs-example form-horizontal"  role="form">
                            <div class="form-group">
                                <label for="inputEmail1" class="col-lg-3 control-label">Имя:</label>
                                <div class="col-lg-7">
                                    <form:input path="name"  class="form-control" id="name" placeholder="Имя" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword1" class="col-lg-3 control-label">Фамилия:</label>
                                <div class="col-lg-7">
                                    <form:input path="surname" class="form-control" id="surname" placeholder="Фамилия" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword1" class="col-lg-3 control-label">Почта:</label>
                                <div class="col-lg-7">
                                    <form:input path="email" class="form-control"  id="email" placeholder="Email" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword1" class="col-lg-3 control-label">Пароль:</label>
                                <div class="col-lg-7">
                                    <form:password path="password" class="form-control"  id="password" placeholder="Пароль" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword1" class="col-lg-3 control-label">Повтор&nbsp;пароля:</label>
                                <div class="col-lg-7">
                                    <input  type="password"  id="password2" class="form-control" placeholder="Повтор пароля" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-offset-3 col-lg-7">
                                    <button type="button" data-loading-text="подождите" disabled="true" id="btn" class="btn btn-primary">
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
