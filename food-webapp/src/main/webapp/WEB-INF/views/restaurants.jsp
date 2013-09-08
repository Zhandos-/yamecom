<%--
    Document   : restaurans
    Created on : Aug 22, 2013, 8:39:21 PM
    Author     : TWINS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="includes/include.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Рестораны</title>
        <style>
            .as_wrapper{
                margin:0 auto;
                width:500px;
                font-family:Arial;
                color:#333;
                font-size:14px;
            }
            .as_country_container{
                padding:20px;
                border:2px dashed #17A3F7;
                margin-bottom:10px;
            }
        </style>

        <script type="text/javascript">

            $(document).ready(function() {
                $("#loader").hide();
                $(window).scroll(function() {
                    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
                        $("#loader").show();
                        $.ajax({
                            type: "POST",
                            url: "restaurant",
                            data: '{id ' + 1 + '}',
                            contentType: "application/json;charset=UTF-8",
                            dataType: "json",
                            success: OnSuccess,
                            failure: function(response) {
                                OnSuccess(response)
                            },
                            error: function(response) {
                                alert(response.d);
                            }
                        });
                    }
                });
            });
            var j = 0;
            function OnSuccess(response) {

                $("#loader").hide();
                var res = '';
                for (var i = 0; i < response.length; i++)
                {
                    res += " <div class=\"as_country_container\" id=\"data\"> ";
                    res += " <table id=\"result\">";
                    res += "<tr >";
                    res += "<td style=\"width:300px;\">" + response[i].about + "</td>";
                    res += "<td>" + response[i].name + "</td>";
                    res += "</tr>";
                    res += "</table>";
                    res += "</div>";
                }
                $("#divResult").before(res);
            }</script>
    </head>
    <body>
        <%@include file="includes/main_menu.jsp" %>
        <br/>
        <div class="container">

            <div class="row">
                <div class="col-xs-1 col-sm-4 col-md-3">
                    <div class="bs-sidebar hidden-print affix" role="complementary" style="">
                        <ul class="nav bs-sidenav">

                            <li>
                                <a href="#overview">Overview</a>
                                <ul class="nav">
                                    <li><a href="#overview-doctype">HTML5 doctype</a></li>
                                    <li><a href="#overview-mobile">Mobile first</a></li>
                                    <li><a href="#overview-responsive-images">Responsive images</a></li>
                                    <li><a href="#overview-type-links">Typography and links</a></li>
                                    <li><a href="#overview-normalize">Normalize</a></li>
                                    <li><a href="#overview-container">Containers</a></li>
                                </ul>
                            </li>
                            <li class="">
                                <a href="#grid">Grid system</a>
                                <ul class="nav">
                                    <li><a href="#grid-media-queries">Media queries</a></li>
                                    <li><a href="#grid-options">Grid options</a></li>
                                    <li><a href="#grid-example-basic">Ex: Stacked-to-horizonal</a></li>
                                    <li class=""><a href="#grid-example-mixed">Ex: Mobile and desktops</a></li>
                                    <li class=""><a href="#grid-example-mixed-complete">Ex: Mobile, tablet, desktops</a></li>
                                    <li class=""><a href="#grid-responsive-resets">Responsive column resets</a></li>
                                    <li class=""><a href="#grid-offsetting">Offsetting columns</a></li>
                                    <li class=""><a href="#grid-nesting">Nesting columns</a></li>
                                    <li class=""><a href="#grid-column-ordering">Column ordering</a></li>
                                    <li class=""><a href="#grid-less">LESS mixins and variables</a></li>
                                </ul>
                            </li>
                            <li class="">
                                <a href="#type">Typography</a>
                                <ul class="nav">
                                    <li class=""><a href="#type-headings">Headings</a></li>
                                    <li class=""><a href="#type-body-copy">Body copy</a></li>
                                    <li class=""><a href="#type-emphasis">Emphasis</a></li>
                                    <li class=""><a href="#type-abbreviations">Abbreviations</a></li>
                                    <li class=""><a href="#type-addresses">Addresses</a></li>
                                    <li class=""><a href="#type-blockquotes">Blockquotes</a></li>
                                    <li class=""><a href="#type-lists">Lists</a></li>
                                </ul>
                            </li>
                            <li class=""><a href="#code">Code</a></li>
                            <li class="">
                                <a href="#tables">Tables</a>
                                <ul class="nav">
                                    <li class=""><a href="#tables-example">Basic example</a></li>
                                    <li class=""><a href="#tables-striped">Striped rows</a></li>
                                    <li class=""><a href="#tables-bordered">Bordered table</a></li>
                                    <li class=""><a href="#tables-hover-rows">Hover rows</a></li>
                                    <li class=""><a href="#tables-condensed">Condensed table</a></li>
                                    <li class=""><a href="#tables-contextual-classes">Contextual classes</a></li>
                                    <li class=""><a href="#tables-responsive">Responsive tables</a></li>
                                </ul>
                            </li>
                            <li class="active">
                                <a href="#forms">Forms</a>
                                <ul class="nav">
                                    <li class=""><a href="#forms-example">Basic example</a></li>
                                    <li class=""><a href="#forms-inline">Inline form</a></li>
                                    <li class=""><a href="#forms-horizontal">Horizontal form</a></li>
                                    <li class="active"><a href="#forms-controls">Supported controls</a></li>
                                    <li class=""><a href="#forms-controls-static">Static control</a></li>
                                    <li class=""><a href="#forms-control-states">Control states</a></li>
                                    <li class=""><a href="#forms-control-sizes">Control sizing</a></li>
                                    <li class=""><a href="#forms-help-text">Help text</a></li>
                                </ul>
                            </li>
                            <li class="">
                                <a href="#buttons">Buttons</a>
                                <ul class="nav">
                                    <li class=""><a href="#buttons-options">Options</a></li>
                                    <li class=""><a href="#buttons-sizes">Sizes</a></li>
                                    <li class=""><a href="#buttons-disabled">Disabled state</a></li>
                                    <li class=""><a href="#buttons-tags">Button tags</a></li>
                                </ul>
                            </li>
                            <li class="">
                                <a href="#images">Images</a>
                            </li>
                            <li class="">
                                <a href="#helper-classes">Helper classes</a>
                            </li>
                            <li class="">
                                <a href="#responsive-utilities">Responsive utilities</a>
                                <ul class="nav">
                                    <li class=""><a href="#responsive-utilities-classes">Available classes</a></li>
                                    <li class=""><a href="#responsive-utilities-print">Print classes</a></li>
                                    <li class=""><a href="#responsive-utilities-tests">Test cases</a></li>
                                </ul>
                            </li>


                        </ul>
                    </div>

                </div>
                <div class="col-xs-10 col-sm-7 col-md-8">
                    <blockquote >
                        <p>Выберите один из ресторанов</p>
                    </blockquote>
                    <hr>
                    <%
                        for (int i = 0; i < 15; i++) {
                    %>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-4"><strong>Del Papa</strong> Ресторан</div>
                                <div class="col-md-2"></div>
                                <div class="col-md-6">Кухни: Пицца / Итальянская / Домашняя</div>
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
                                            <div class="col-md-4"><small>3000 T</small></div>
                                            <div class="col-md-4"><small>Бесплатная! В пределах Алматы!</small></div>
                                            <div class="col-md-4"><small>1 час</small></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%                        }
                    %>
                    <div class="as_wrapper">
                        <%
                            for (int i = 0; i < 15; i++) {
                        %>
                        <div class="as_country_container" id="data">
                            <table id="result">
                                <tr>
                                    <td style="width:300px;"></td>
                                    <td>some text <%=i%></td>
                                </tr>
                            </table>
                        </div>
                        <%
                            }
                        %>

                        <div id="divResult"></div>
                        <div id="loader">
                            <img src="resources/images/load.gif" />
                        </div>
                    </div>
                </div>
                <div class="col-xs-1 col-sm-3 col-md-0"></div>
            </div>
        </div>

    </body>
</html>
