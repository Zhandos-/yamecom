<%--
    Document   : main_menu
    Created on : Aug 24, 2013, 6:23:13 PM
    Author     : TWINS
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Fixed navbar -->
<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="${pageContext.request.contextPath}/" class="navbar-brand"></a>
        </div>
        <div class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <li class="active"><a href="${pageContext.request.contextPath}/">zakazhiedu</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/restaurants">Рестораны</a></li>
                <!--    <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                      <li><a href="#">Action</a></li>
                      <li><a href="#">Another action</a></li>
                      <li><a href="#">Something else here</a></li>
                      <li class="divider"></li>
                      <li class="dropdown-header">Nav header</li>
                      <li><a href="#">Separated link</a></li>
                      <li><a href="#">One more separated link</a></li>
                  </ul>
              </li>-->
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <%if (request.getUserPrincipal() != null) {%>
                <%if (request.isUserInRole("ROLE_COMPANION")) {%>
                <li class="active dropdown">
                    <a href="${pageContext.request.contextPath}/companion/restaurateur" >Ресторатор</a>
                </li>
                <% }%>
                <li class="active dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <%
                            out.print(request.getUserPrincipal().getName());
                        %>
                        <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <!--<li class="dropdown-header">Настройки профиля</li>-->
                        <li><a href="${pageContext.request.contextPath}/client/profile">Настройки профиля</a></li>
                        <li><a href="#">Настройки моих адресов</a></li>
                        <li><a href="#">Настройки моих телефонов</a></li>
                        <li class="divider"></li>
                        <li class="dropdown-header"></li>
                        <li><a href="#">Настройки уведомлений</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout" >Выйти</a></li>
                        <!--     <li><a href="#">One more separated link</a></li>-->
                    </ul>
                </li>
                <% } else {%>
                <li class="active dropdown">
                    <a href="${pageContext.request.contextPath}/login" >Войти</a>
                </li>
                <li class="nav navbar-nav">
                    <a href="${pageContext.request.contextPath}/client/registration" >Регистрация</a>
                </li>
                <% }%>
            </ul>
        </div>
    </div>
</header>
<!-- Fixed navbar -->
