<%--
    Document   : login
    Created on : Jul 28, 2013, 11:25:14 AM
    Author     : TWINS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Логин</title>
        <%@include file="includes/include.jsp" %>
        <script src="resources/js/jquery/jquery.validate.min.js"></script>
        <script src="resources/js/custom/login.js"></script>
    </head>
    <body>
        <%@include file="includes/main_menu.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-xs-1 col-sm-5 col-md-4"></div>
                <div class="col-xs-10 col-sm-5 col-md-5">
                    <h1>Логин</h1>
                    <form id="form" action="#" method="post">
                        <div class="bs-example form-horizontal" id="result" role="form">
                            <div class="form-group">
                                <label for="email" class="col-lg-4 control-label">Почта:</label>
                                <div class="col-lg-6">
                                    <input type="text" name="email" id="email"  class="form-control"   placeholder="почта"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-lg-4 control-label">Пароль:</label>
                                <div class="col-lg-6">
                                    <input type="password" name="password" id="password"  class="form-control"   placeholder="пароль"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-offset-4 col-lg-6">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox">Запомнить меня
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-offset-4 col-lg-6">
                                    <button type="submit" data-loading-text="подождите" id="btn" class="btn btn-primary">
                                        Войти
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-xs-1 col-sm-5 col-md-4"></div>
            </div>
        </div>
    </body>
</html>
