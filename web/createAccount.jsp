<%-- 
    Document   : createAccount
    Created on : Jan 23, 2021, 1:53:32 PM
    Author     : MINH TUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .container {
                margin: auto;
                display: div;
            }
            h1 {
                text-align: center;
            }
            .infor {
                display: block;
                border: groove;
                padding: 50px;
                width: 350px;
                margin-left:35%;
                margin-top: 100px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Registration New Account</h1>
            <c:set var="error" value="${requestScope.ERROR}"/>
            <form action="createAccount" method="post">
                <div class="infor">
                    Input email of student: <input type="text" name="txtEmail" value="${requestScope.EMAIL}" /><br/><br/>
                    <c:if test="${error.emailBlank ne null}">
                        <font color="red">
                            ${error.emailBlank}
                        </font><br/><br/>
                    </c:if>
                    <c:if test="${error.emailDuplicate ne null}">
                        <font color="red">
                            ${error.emailDuplicate}
                        </font><br/><br/>
                    </c:if>
                    <c:if test="${error.emailNotValid ne null}">
                        <font color="red">
                            ${error.emailNotValid}
                        </font><br/><br/>
                    </c:if>
                        Input account password: <input type="password" name="txtPassword" value="" /><br/><br/>
                    <c:if test="${error.passwordNotValid ne null}">
                        <font color="red">
                            ${error.passwordNotValid}
                        </font><br/><br/>
                    </c:if>
                        Input password again: <input type="password" name="txtPasswordAgain" value="" /><br/><br/>
                    <c:if test="${error.passwordNotDuplicate ne null}">
                        <font color="red">
                            ${error.passwordNotDuplicate}
                        </font><br/><br/>
                    </c:if>
                        Input student name: <input type="text" name="txtName" value="${requestScope.NAME}" /><br/><br/>
                    <c:if test="${error.nameBlank ne null}">
                        <font color="red">
                            ${error.nameBlank}
                        </font><br/><br/>
                    </c:if>
                    <div style="text-align: center;">
                        <input type="submit" name="btnAction" value="Create" /><br/><br/>
                        <c:if test="${requestScope.STATUS_CREATE ne null}">
                            <font color="red">
                                ${requestScope.STATUS_CREATE}
                            </font><br/><br/>
                        </c:if>
                        <c:url value="loginPage" var="url">
                        </c:url>
                        <a href="${url}">Back to login page</a>
                    </div>
                </div>

            </form>

        </div>

    </body>
</html>
