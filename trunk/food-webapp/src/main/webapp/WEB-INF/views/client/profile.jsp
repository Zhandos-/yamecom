<%--
    Document   : profile
    Created on : Jul 31, 2013, 8:23:36 PM
    Author     : TWINS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Настройки профиля</title>
        <%@include file="include.jsp" %>
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-formhelpers.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resources/js/jquery/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/custom/profile.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap-formhelpers-phone.format.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap-formhelpers-phone.js"></script>
        <script>
            $(document).ready(function() {
                $('#mytab a').click(function(e) {
                    e.preventDefault();
                    $(this).tab('show');
                })
            });
        </script>
    </head>
    <body >
        <%@include file="../includes/main_menu.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-xs-1 col-sm-4 col-md-2"></div>
                <div class="col-xs-10 col-sm-6 col-md-7">
                    <br/>
                    <ul id="mytab" class="nav nav-pills">
                        <li class="active"><a href="#profile">Настройки профиля</a></li>
                        <li><a href="#change-password" >Изменить пароль</a></li>
                        <li><a href="#">Мои адреса</a></li>
                        <li><a href="#">Настройки уведомлений</a></li>
                    </ul>
                    <br/>
                    <div id="tab-content" class="tab-content">
                        <div id="profile" class="tab-pane fade active in">
                            <div class="bs-example form-horizontal"   role="form">
                                <form:form method="post" id="form"  action="#" commandName="user" >
                                    <form:hidden path="id"   />
                                    <div class="form-group">
                                        <label for="name" class="col-lg-4 control-label">Имя:</label>
                                        <div class="col-lg-7">
                                            <form:input path="name"  class="form-control" id="name" placeholder="Имя" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="surname" class="col-lg-4 control-label">Фамилия:</label>
                                        <div class="col-lg-7">
                                            <form:input path="surname" class="form-control" id="surname" placeholder="Фамилия" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="email" class="col-lg-4 control-label">Почта *:</label>
                                        <div class="col-lg-7">
                                            <form:input path="email" class="form-control"  id="email" placeholder="Email" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="phones[0].number" class="col-lg-4 control-label">Мобильный&nbsp;телефон *:</label>
                                        <div class="col-lg-7">
                                            <form:hidden path="phones[0].id"   />
                                            <form:hidden path="phones[0].phoneType"   />
                                            <form:input path="phones[0].number" class="form-control" id="phones[0].number"  placeholder="Мобильный телефон" />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-lg-offset-4 col-lg-7">
                                            <button type="submit" data-loading-text="подождите" id="btn" class="btn btn-primary">
                                                Сохранить
                                            </button>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                        <div id="change-password" class="tab-pane fade">
                            <div class="bs-example form-horizontal"  role="form">
                                <form id="form2" method="post"   action="#">
                                    <div class="form-group">
                                        <label for="email" class="col-lg-4 control-label">Старый&nbsp;пароль *:</label>
                                        <div class="col-lg-7">
                                            <input type="password" class="form-control"  name="oldpassword" id="oldpassword" placeholder="Старый пароль" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="email" class="col-lg-4 control-label">Новый&nbsp;пароль *:</label>
                                        <div class="col-lg-7">
                                            <input type="password"  class="form-control" name="password"  id="password" placeholder="Новый пароль" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="email" class="col-lg-4 control-label">Повтор&nbsp;нового&nbsp;пароля&nbsp;*:</label>
                                        <div class="col-lg-7">
                                            <input type="password"  class="form-control" name="repassword" id="repassword" placeholder="Повтор нового пароля" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-offset-4 col-lg-7">
                                            <button type="submit" data-loading-text="подождите" id="btn2" class="btn btn-primary">
                                                Сохранить
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-1 col-sm-5 col-md-2"></div>
        </div>
    </body>
</html>
