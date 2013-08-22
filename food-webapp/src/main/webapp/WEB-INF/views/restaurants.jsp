<%-- 
    Document   : restaurans
    Created on : Aug 22, 2013, 8:39:21 PM
    Author     : TWINS
--%>
<%@include file="include.jsp" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
<script type="text/javascript" src="resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
    
$(document).ready(function(){  
     $("#loader").hide();
    $(window).scroll(function(){ 
        if($(window).scrollTop() == $(document).height() - $(window).height()){
            $("#loader").show();
            $.ajax({
                type: "POST",
                url: "restaurant",
                data: '{id ' + 1 + '}',
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success:OnSuccess,
                failure: function (response) {
                      OnSuccess(response)
                },
                error: function (response) {
                    alert(response.d);
                }
            });
        }
    });
});
var j=0;
function OnSuccess(response) {
        
        $("#loader").hide();
        var res='';
        for(var i=0;i<response.length;i++)
            {
                res+=" <div class=\"as_country_container\" id=\"data\"> ";
                res+=" <table id=\"result\">";
                res+="<tr >";
                res+="<td style=\"width:300px;\">"+response[i].about+"</td>";
                res+="<td>"+response[i].name+"</td>"
                res+="</tr>";
                res+="</table>"
                res+="</div>"
            }
            $("#divResult").before(res);
    }
</script>
    </head>
    <body>
        <h1 id="1">Hello World!</h1>
<div class="as_wrapper">
    <%
        for(int i=0;i<15;i++)
        {
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
    </body>
</html>
