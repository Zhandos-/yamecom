<%-- 
    Document   : login
    Created on : Jul 28, 2013, 11:25:14 AM
    Author     : TWINS
--%>
<%@include file="include.jsp" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login form!</h1>
    <center>
        <form method="post" id="form" action="<c:url value="/j_spring_security_check" />"  >
            <table class="separate" cellspacing="10px">
                <tr>
                    <td class="right">
                        <label for="Login1_UserName" id="Login1_UserNameLabel">
                            Логин:
                        </label>
                    </td>
                    <td class="left">
                        <input name="j_username" type="text" id="j_username" style="width:130px;" />
                        <span id="Login1_UserNameRequired" title="" style="color:Red;visibility:hidden;">*</span>
                    </td>
                </tr>
                <tr>
                    <td class="right">
                        <label for="Login1_Password" id="Login1_PasswordLabel">
                            Пароль:
                        </label>
                    </td>
                    <td class="left">
                        <input name="j_password" type="password" id="j_password" style="width:130px;" />
                        <span id="Login1_PasswordRequired" title="" style="color:Red;visibility:hidden;">*</span>
                    </td>
                </tr>
                <tr valign="top">
                    <td class="right">
                        <label for="Login1_UserName" id="Login1_Label4">

                        </label>
                    </td>
                    <td class="left">
                        <div id="submit">
                            <input type="submit" name="Login1$LoginButton"  id="Login1_LoginButton" />
                        </div>
                        <input id="_spring_security_remember_me" type="checkbox" name="_spring_security_remember_me" />
                    </td>
                </tr>
            </table>  
        </form>
        <a href="${pageContext.request.contextPath}/registration" >Регистрация</a>
    </center>
</body>
</html>
