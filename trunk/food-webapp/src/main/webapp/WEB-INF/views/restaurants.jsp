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
        <link href="${pageContext.request.contextPath}/resources/slider/css/slider.css" 
              rel="stylesheet">
        <script type="text/javascript" 
                src="${pageContext.request.contextPath}/resources/slider/js/bootstrap-slider.js">
        </script>
        <script type="text/javascript">
            $(document).ready(function() {
                var page = 0;
                var lock = true;
                $.post("${pageContext.request.contextPath}/restaurant",
                        {page: page},
                function(response) {
                    $("#loader").hide();
                    $("#theSelect").html(response);
                });
                $('#price').change(function() {
                    $('#sl1').slider('setValue', $(this).val());
//                    alert($('#price').val());
                });
                $('#sl1').slider({
                    formater: function(value) {
                        $('#price').val(value);
                        return 'Минимальный заказ: ' + value;
                    }
                }).on('slideStop', function() {
//                    alert($('#price').val());
                });
                $("input[type=checkbox]").click(function() {
                    lock = false;
                    $("#loader").show();
                    $("#theSelect").html('response');
                    var values = $('input:checkbox:checked').map(function() {
                        return this.value;
                    }).get();
                    $.post("${pageContext.request.contextPath}/restaurant",
                            {types: values},
                    function(response) {
                        $("#loader").hide();
                        $("#theSelect").html(response);
                    });

                }
                );
                $(window).scroll(function() {
                    if (lock & $(window).scrollTop() == $(document).height() - $(window).height()) {
                        page += 20;
                        $("#loader").show();
                        $.post("${pageContext.request.contextPath}/restaurant",
                                {page: page},
                        OnSuccess);
                    }
                });
                $("#search").click(function() {
                    lock = false;
                    $("#loader").show();
                    $.post("${pageContext.request.contextPath}/searchRestaurant",
                            {name: $("#text").val()},
                    function(response) {
                        $("#loader").hide();
                        $("#theSelect").html(response);
                    });
                });
            });
            var j = 0;
            function OnSuccess(response) {
                $("#loader").hide();
                $("#divResult").before(response);
            }</script>
    </head>
    <body>
        <%@include file="includes/main_menu.jsp" %>
        <br/>
        <div class="container">

            <div class="row">
                <div class="col-xs-1 col-sm-4 col-md-3">
                    <div class="panel panel-default" style="position: fixed;top:auto">
                        <div class="panel-body">
                            <ul class="nav navbar-left">
                                <li>
                                    <p><center>Кухни ресторана:</center></p>
                                </li>
                                <c:forEach var="t" items="${types}">
                                    <li>
                                        <a href="#overview">
                                            <input value="${t.id}" type="checkbox">
                                            ${t.type.description}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="panel panel-default" style="position: fixed;margin-top:300px;">
                        <p><center>Минимальный заказ:</center></p>
                        <div class="panel-body">
                            <input type="text" id='price' class="form-control" /><br/>
                            <div class="slider slider-horizontal" style="width: 130px;">
                                <input type="text" class="span2" data-slider-min="1000" 
                                       data-slider-max="10000" data-slider-step="5" 
                                       data-slider-value="10000" value="10000" id="sl1">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-10 col-sm-7 col-md-8">
                    <blockquote >
                        <p>Поиск ресторана:</p>
                        <p>
                            <input type="text" id="text" class="form-control" />
                            <input type="submit" id="search" value="Поиск" 
                                   class="btn-success" />
                        </p>
                    </blockquote>
                    <hr>
                    <div id="theSelect">
                    </div>
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
