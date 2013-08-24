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
        <%@include file="include.jsp" %> 
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
                <div class="col-xs-1 col-sm-5 col-md-4"></div>
                <div class="col-xs-10 col-sm-5 col-md-5">
                    <h1>Логин</h1>
                    <div class="bs-example form-horizontal"  role="form">
                        <div class="form-group">
                            <label for="inputEmail1" class="col-lg-4 control-label">Почта:</label>
                            <div class="col-lg-6">
                                <input type="email" class="form-control"  id="inputEmail1" placeholder="почта">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword1" class="col-lg-4 control-label">Пароль:</label>
                            <div class="col-lg-6">
                                <input type="password" class="form-control"  id="inputPassword1" placeholder="пароль">
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
                                <button type="button" data-loading-text="подождите" id="btn" class="btn btn-primary">
                                    Войти
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-1 col-sm-5 col-md-4"></div>
            </div>
        </div>
    </body>
</html>
