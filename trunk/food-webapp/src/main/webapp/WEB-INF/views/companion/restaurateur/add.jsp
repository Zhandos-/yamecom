<%-- 
    Document   : add
    Created on : Sep 8, 2013, 10:26:10 PM
    Author     : daniyar.artykov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../../includes/include.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Новое заведение</title>
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-formhelpers.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resources/js/jquery/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/custom/registration.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap-formhelpers-phone.format.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap-formhelpers-phone.js"></script>
    </head>
    <body>
        <%@include file="../../includes/main_menu.jsp" %>
        <div class="container">
            <div class="row">
                <div style="width:88%; margin: 0 0 0 100px;">
                    <h1 style="text-align: center;">Добавить заведение</h1>
                    <form:form method="post" id="form" commandName="r" enctype="multipart/form-data"
                               action="${pageContext.request.contextPath}/companion/restaurateur/add"
                               cssClass="form">
                        <div class="bs-example form-horizontal" style="float: left;" role="form">
                            <div class="form-group">
                                <label for="facilityType" class="col-lg-4 control-label">
                                    Тип заведения *:
                                </label>
                                <div class="col-lg-7">
                                    <form:input path="details.facilityType" class="form-control" 
                                                id="facilityType" required="false" 
                                                placeholder="Ресторан, Суши-бар, ФастФуд" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-4 control-label">
                                    Наименование *:
                                </label>
                                <div class="col-lg-7">
                                    <form:input class="form-control" required="false" 
                                                id="name" placeholder="Наименование" 
                                                path="name" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="city" class="col-lg-4 control-label">
                                    Город *:
                                </label>
                                <div class="col-lg-7">
                                    <input class="form-control" id="city" 
                                           name="city" placeholder="Город" required="false" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="address" class="col-lg-4 control-label">
                                    Адрес *:
                                </label>
                                <div class="col-lg-7">
                                    <form:input class="form-control" id="address" 
                                                placeholder="Адрес" required="false" 
                                                path="address.address" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="minOrderPrice" class="col-lg-4 control-label">
                                    Минимальный заказ *:
                                </label>
                                <div class="col-lg-7">
                                    <form:input class="form-control" name="minOrderPrice" 
                                                id="minOrderPrice" placeholder="Минимальный заказ" 
                                                required="false" path="details.minOrderPrice"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="minOrderPrice" class="col-lg-4 control-label">
                                    Приблизительное время доставки *:
                                </label>
                                <div class="col-lg-7">
                                    <form:input class="form-control" name="deliveryTime"  
                                                id="deliveryTime" placeholder="Приблизительное время доставки " 
                                                required="true" path="details.deliveryTime" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="mPhone" class="col-lg-4 control-label">
                                    Мобильный телефон *:
                                </label>
                                <div class="col-lg-7">
                                    <input class="bfh-phone form-control" name="mPhone" 
                                           data-format="+7 (ddd) ddd-dd-dd" id="mPhone" 
                                           type="text" required="false" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sPhone" class="col-lg-4 control-label">
                                    Тел. номер для тех. поддержки:
                                </label>
                                <div class="col-lg-7">
                                    <input class="bfh-phone form-control" name="sPhone" 
                                           data-format="+7 (ddd) ddd-dd-dd" id="sPhone" 
                                           type="text" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="restaurantLogo" class="col-lg-4 control-label">
                                    Логотип:
                                </label>
                                <div class="col-lg-7">
                                    <input type="file" name="restaurantLogo" id="restaurantLogo"
                                           required="true" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="payByCard" class="col-lg-4 control-label">
                                    Принимать интернет платежи:
                                </label>
                                <div class="col-lg-7">
                                    <form:checkbox name="payByCard" path="details.payByCard" 
                                                   class="bfh-phone form-control" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="about" class="col-lg-4 control-label">
                                    Информация для заказчиков:
                                </label>
                                <div class="col-lg-7">
                                    <form:textarea class="bfh-phone form-control" 
                                                   name="about" path="about"
                                                   id="about" rows="4" cols="45" />
                                </div>
                            </div>

                        </div>
                        <div class="bs-example form-horizontal" style="float:right;width:400px;" 
                             role="form">
                            <div class="form-group">
                                <label class="col-lg-4 control-label">
                                    Кухни:
                                </label>
                                <div class="col-lg-7">
                                    <c:forEach var="t" items="${types}">
                                        <br />
                                        <form:checkbox path="details.types" value="${t}" />
                                        ${t.description}
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div style="float:right;margin:0 220px 0 0;">
                            <button type="submit" data-loading-text="подождите" 
                                    id="btn" class="btn btn-primary"
                                    style="margin:0 90px 0 0;">
                                Добавить
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
