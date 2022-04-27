<%-- 
    Document   : historyDetailPage
    Created on : Feb 1, 2021, 12:21:13 AM
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
            h1 {
                display: block;
                text-align: center;
                background-color: #8080801c;
            }
            .contentComponent {
                display: block;
                border: groove;
                width: 50%;
                margin: 0 auto;
                margin-top: 30px;
                margin-bottom: 50px;
                padding: 30px;
            }
        </style>
    </head>
    <body>
        <font color="red">
        Welcome, ${sessionScope.WELLCOME_NAME}
        </font>
        <form action="logout">
            <input type="submit" value="Logout" />
        </form>
        <h1>Quiz History</h1>
        <div style="text-align: center">
            <a style="font-size: 130%; text-align: center" href="onloadStudentST">Take a quiz</a>
        </div><br/>
        <div style="text-align: center">
            <c:url value="searchHistoryQuizST" var="url">
                <c:param name="cbbSubject" value="${requestScope.SEARCH}"></c:param>
            </c:url>
            <a style="font-size: 130%; text-align: center" href="${url}">Back</a>
        </div>
        <div class="content">
            <h3 style="text-align: center">${requestScope.QUIZ.subjectId} - Total score: ${requestScope.QUIZ.score}(${requestScope.QUIZ.numberCorrectAns}/${requestScope.QUIZ.numberOfQuestion})</h3>
            <c:set var="mapQuesAndAns" value="${requestScope.MAPQUESANDANS}"/>
            <c:if test="${mapQuesAndAns ne null && mapQuesAndAns.size() > 0}">
                <c:forEach var="quesAndAns" varStatus="counter" items="${requestScope.MAPQUESANDANS}">
                    <div class="contentComponent">
                        Question ${counter.count}: ${quesAndAns.get(0).questonContent}<br/>
                        Answer choose: ${quesAndAns.get(1).answerChoose}<br/>
                        Answer correct: ${quesAndAns.get(1).correctAns}<br/>
                        <c:if test="${quesAndAns.get(1).answerChoose eq quesAndAns.get(1).correctAns}">
                            Point: 1
                        </c:if>
                        <c:if test="${quesAndAns.get(1).answerChoose ne quesAndAns.get(1).correctAns}">
                            Point: 0
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </body>
</html>
