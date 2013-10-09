<%--
    Document   : restaurants_list
    Created on : Sep 16, 2013, 7:51:28 PM
    Author     : TWINS
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="secure" uri="http://www.springframework.org/security/tags" %>
<c:forEach var="r" items="${restaurants}">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="row">
                <div class="col-md-4"><strong>${r.restaurant.name}</strong> Ресторан</div>
                <div class="col-md-2"></div>
                <div class="col-md-6">Кухни: <c:forEach var="t" items="${r.types}">
                        <span class="label label-info"><c:out value="${t.type.description}"/></span>
                    </c:forEach></div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <img src="${pageContext.request.contextPath}/resources/images/Capture.png" style="height: 96px;width: 155px" alt="..." class="img-thumbnail">
                </div>
                <div class="col-md-9">
                    <div class="row">
                        <div class="col-md-5">
                            <small>
                                <span class="glyphicon glyphicon-time " ></span>
                                <span class="label label-success tooltiped" rel="tooltip" data-placement="top" data-original-title="Заведение прямо сейчас принимает заказы" data-trigger="hover">Онлайн</span>
                                11:00 - 23:00
                            </small>
                        </div>
                        <div class="col-md-2"></div>
                        <div class="col-md-5">
                            <span class="glyphicon glyphicon-thumbs-down" style="font-size: 15px;color: #DB4173">45</span>&nbsp;&nbsp;
                            <span class="glyphicon glyphicon-thumbs-up" style="font-size: 15px;color: #2FA26F">123</span>&nbsp;&nbsp;
                            <span class="glyphicon glyphicon-comment">145</span>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-4" ><small><strong>Минимальный заказ</strong></small></div>
                            <div class="col-md-4"><small><strong>Стоимость доставки</strong></small></div>
                            <div class="col-md-4"><small><strong>Время доставки</strong></small></div>
                        </div>
                        <hr/>
                        <div class="row">
                            <div class="col-md-4"><small>${r.minOrderPrice} T</small></div>
                            <div class="col-md-4"><small>${r.deliveryPrice}</small></div>
                            <div class="col-md-4"><small>1 час</small></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>