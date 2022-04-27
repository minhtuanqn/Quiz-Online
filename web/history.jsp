<%-- 
    Document   : history
    Created on : Jan 31, 2021, 10:41:01 PM
    Author     : MINH TUAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            .search {
                display: block;
                text-align: center;
                width: 100%;
            }
            .searchComponent {
                margin-top: 20px;
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
        </div>

        <form action="searchHistoryQuizST" >
            <div class="search" style="height: 100px;">
                <div class="searchComponent">
                    Subject: <select name="cbbSubject">
                        <option >Choose a subject</option>
                        <c:forEach var="subject" items="${requestScope.SUBJECTS}">
                            <c:if test="${param.cbbSubject eq subject.id}">
                                <option selected value="${subject.id}">${subject.id} - ${subject.name}</option>
                            </c:if>
                            <c:if test="${param.cbbSubject ne subject.id}">
                                <option value="${subject.id}">${subject.id} - ${subject.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="searchComponent">
                    <input  type="submit" value="Search" />
                </div>
            </div>
            <c:set var="curPage" value="${param.curPage}"/>
            <div class="content">
                <c:if test="${requestScope.QUIZLIST ne null && requestScope.QUIZLIST.size() > 0}">
                    <table style="margin: 0 auto" border="1">
                        <thead>
                            <tr>
                                <th>Subject</th>
                                <th>Number of question</th>
                                <th>Number of correct answer</th>
                                <th>Score</th>
                                <th>Date take quiz</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="quiz" items="${requestScope.QUIZLIST}" varStatus="counter">
                                <tr>
                                    <td>${quiz.subjectId}</td>
                                    <td>${quiz.numberOfQuestion}</td>
                                    <td>${quiz.numberCorrectAns}</td>
                                    <td>${quiz.score}</td>
                                    <td>${quiz.dateText}</td>
                                    <td>
                                        <c:url var="url" value="onloadHistoryDetailST">
                                            <c:param name="txtSearch" value="${param.cbbSubject}"></c:param>
                                            <c:param name="quizId" value="${quiz.id}"></c:param>
                                        </c:url>
                                        <a href="${url}">View details</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="paging" style="text-align: center; margin-bottom:  50px; margin-top: 50px;">   
                        <c:set var="totalPage" value="${requestScope.TOTALPAGE}"/>
                        <c:if test="${totalPage gt 1}">
                            <c:forEach begin="${1}" end="${totalPage}" var="page" step="${1}">
                                <c:url value="searchHistoryQuizST" var="url">
                                    <c:param name="cbbSubject" value="${param.cbbSubject}"></c:param>
                                    <c:param name="curPage" value="${page}"></c:param>
                                </c:url>
                                <c:if test="${page eq curPage}">
                                    <div  style="display: inline; padding: 3px; margin-left: 5px;  border: groove; background-color:  yellow">
                                        <a href="${url}">${page}</a>
                                    </div>
                                </c:if>
                                <c:if test="${page ne curPage}">
                                    <div style="display: inline;  border: groove; padding: 3px; margin-left: 5px; ">
                                        <a href="${url}">${page}</a>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>

                </c:if>
                <div style="text-align:  center">
                    <c:if test="${param.cbbSubject ne null && requestScope.QUIZLIST eq null}">
                        <h3>Can not found any result</h3>
                    </c:if><c:if test="${param.cbbSubject ne null && requestScope.QUIZLIST ne null && requestScope.QUIZLIST.size() eq '0'}">
                        <h3>Can not found any result</h3>
                    </c:if>
                    <c:if test="${param.cbbSubject eq null}">
                        <h3>Search now</h3>
                    </c:if>
                </div>
            </div>
        </form>
    </body>
</html>
