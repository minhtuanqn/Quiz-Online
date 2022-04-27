<%-- 
    Document   : cannotAcess
    Created on : Feb 4, 2021, 6:59:30 AM
    Author     : MINH TUAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 style="text-align: center; margin-top: 200px;">You can not access this page</h1><br/>
        <div style="text-align: center">
            <c:url value="/" var='url'>
            </c:url>
            <a href="${url}">Return login page</a>
        </div>
        
    </body>
</html>
