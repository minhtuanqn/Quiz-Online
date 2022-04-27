<%-- 
    Document   : login
    Created on : Jan 13, 2021, 1:58:58 AM
    Author     : MINH TUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            .login {
                font-size: 130%;
                border: solid;
                height: 200px;
                width:  450px;
                margin-left: 35%;
                text-align: center;
            }
            h1 {
                font-size: 300%;
                margin-top: 100px;
                text-align: center;
            }
            #btnLogin {
                font-size: 90%;
            }
            .loginUp {
                margin-left: 35%;
                width:  450px;
                background-color: rgb(255, 186, 0);
            }

        </style>
    </head>
    <body>
        <div class="loginUp">
            <h1>
                Login Page
            </h1>
        </div>
        <div class="login"><br/>
            <form action="login" method="POST">
                Username: <input  type="text" name="txtEmail" value="" /><br/><br/>
                Password: <input type="password" name="txtPassword" value=""/><br/><br/>
                <input id="btnLogin" type="submit" name="btnAction" value="Login" /><br/>
                <c:if test="${param.Status ne null}">
                    <font color="red">
                    ${param.Status}
                    </font>
                </c:if>
            </form>
            <a href="create">Create new Account</a>
        </div>
    </body>
</html>
