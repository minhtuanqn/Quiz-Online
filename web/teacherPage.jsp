<%-- 
    Document   : home
    Created on : Jan 23, 2021, 5:16:44 PM
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
            .search {
                display: block;
                border: groove;
                padding: 30px;
                margin-top: 15px;
                background-color: #fcf8e3;
                font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
            }
            .searchComponent {
                display: block;
                width: 20%;
                margin-left: 3%;
                float: left;
                margin-bottom: 15px;
                font-size: 135%;
            }
            .content {
                width: 100%;
                padding-top: 50px;
                padding-bottom: 35px;
                margin-top: 70px;
                display: block;
            }
            .extraButton {
                margin-top: 15px;
                margin-bottom: 15px;
                display: block;
                clear: both;
                float: right;
            }
            th {
                background-color: #fcf8e3;
            }
            body {
                background-color: #8080801c;
            }
            td {
                background: white;
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


        <c:set var="curPage" value="${param.curPage}"/>
        <form action="searchTC" >
            <div class="search" style="height: 100px;">
                <div class="searchComponent">
                    Subject: <select name="cbbSubject">
                        <option value="">Choose a subject</option>
                        <c:forEach var="subject" items="${requestScope.SUBJECTS}">
                            <c:if test="${param.cbbSubject eq subject.id}">
                                <option selected value="${subject.id}">${subject.name}</option>
                            </c:if>
                            <c:if test="${param.cbbSubject ne subject.id}">
                                <option value="${subject.id}">${subject.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="searchComponent">
                    Question name: <textarea name="txtSearchName" rows="4" cols="30">${param.txtSearchName}</textarea>
                </div>
                <c:if test="${param.chkActive ne null && param.chkActive ne ''}">
                    <div class="searchComponent">
                        Active question: <input type="checkbox" name="chkActive" checked value="ON" /> 
                    </div>
                </c:if>
                    <c:if test="${param.chkActive eq null || param.chkActive eq ''}">
                    <div class="searchComponent">
                        Active question: <input type="checkbox" name="chkActive" value="ON" /> 
                    </div>
                </c:if>
                <div class="searchComponent" style="width: 10%;">
                    <input style="width: 200px" type="submit" value="Search" />
                </div>
            </div>
        </form>
        <form action="onLoadQuestionTC">
            <div class="extraButton">
                <input type="submit" style="height: 30px" name="btnAction" value="Create new Question" />
            </div>
        </form>

        <c:set var="questionsAndAnswer" value="${requestScope.QUESTIONANDANSWER}"/>
        <c:if test="${questionsAndAnswer ne null && questionsAndAnswer.size() > 0}">
            <div class="content" style="margin-top: 0px;">
                <c:forEach items="${questionsAndAnswer}" var="QA" varStatus="counter">
                    <div style="margin-top: 30px">
                        <table border="1" style="margin: 0 auto; width: 90%">
                            <thead>
                                <tr>
                                    <th>
                                        Question ID
                                    </th>
                                    <th style="width: 23%; padding: 3px;">
                                        Question Content
                                    </th>
                                    <th>Answers</th>
                                    <th style="width: 21%; padding: 3px;">
                                        Correct answer
                                    </th>
                                    <th  style="width: 5px;">
                                        Subject ID
                                    </th>
                                    <th style="width: 5px;">
                                        Status
                                    </th>
                                    <th  style="width: 5px;">
                                        Action
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td style="width: 5px;">
                                        ${QA.get(0).id}
                                    </td>
                                    <td >
                                        ${QA.get(0).questionContent}
                                    </td>
                                    <td>
                                        <c:forEach var="answer" items="${QA.get(1)}">
                                            <div>
                                                ${answer.answerContent}
                                            </div>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="answer" items="${QA.get(1)}">
                                            <c:if test="${answer.correctAnswer eq true}">
                                                <div>
                                                    ${answer.answerContent}
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        ${QA.get(0).subjectId}
                                    </td>
                                    <td>
                                        ${QA.get(0).active}
                                    </td>
                                    <td>
                                        <form action="updateQuestionTC">
                                            <input type="hidden" name="txtContentQuestion" value="${QA.get(0).questionContent}" />
                                            <input type="hidden" name="txtQuestionId" value="${QA.get(0).id}" />
                                            <input type="hidden" name="txtSubjectId" value="${QA.get(0).subjectId}" />
                                            <input type="submit" style="width: 100%" value="Update" />
                                        </form>
                                        <form action="deleteQuestionTC">
                                            <input type="hidden" name="txtSearchName" value="${param.txtSearchName}" />
                                            <input type="hidden" name="cbbSubject" value="${param.cbbSubject}" />
                                            <input type="hidden" name="chkActive" value="${param.chkActive}" />
                                            <input type="hidden" name="txtQuestionId" value="${QA.get(0).id}" />
                                            <input type="submit" style="width: 100%" value="Delete" />
                                            <input type="hidden" name="curPage" value="${param.curPage}" />
                                        </form>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </c:forEach>
            </div>
            
            <div class="paging" style="text-align: center; margin-bottom:  50px;">   
                <c:set var="totalPage" value="${requestScope.TOTALPAGE}"/>
                <c:if test="${totalPage gt 1}">
                    <c:forEach begin="${1}" end="${totalPage}" var="page" step="${1}">
                        <c:url value="searchTC" var="url">
                            <c:param name="txtSearchName" value="${param.txtSearchName}"></c:param>
                            <c:param name="chkActive" value="${param.chkActive}"></c:param>
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
        <c:if test="${questionsAndAnswer ne null && questionsAndAnswer.size() == 0}">
            <h2 style="text-align: center">Can not found any result</h2> 
        </c:if>
        <c:if test="${questionsAndAnswer eq null}">
            <h2 style="text-align: center">Search now</h2> 
        </c:if>
    </body>
</html>
