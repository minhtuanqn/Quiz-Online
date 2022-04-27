<%-- 
    Document   : questionInfor
    Created on : Jan 24, 2021, 6:09:25 PM
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
            body {
                background-color: #8080801c;
                }
            h1 {
                text-align: center;
                background-color: #8080801c;
            }
            .QuestionInfor {
                display: block;
                height: 600px;
                background-repeat: repeat;
            }
            .content {
                padding-top: 45px;
                text-align: center;
                width: 38%;
                border: groove;
                height: 500px;
                background-color: #fcf8e3;
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
        <h1>
            Create Question
        </h1>
        <c:set var="AnsList" value="${requestScope.ANSLIST}"/>
        <div class="QuestionInfor">
            <form action="createQuestionInforTC">
                <div class="content" style="margin: 0 auto">
                    <div>
                        Subject: <select name="cbbSubject">
                            <c:forEach var="subject" items="${requestScope.SUBJECTS}">
                                <c:if test="${requestScope.SUBJECT ne subject.id}">
                                    <option value="${subject.id}">${subject.name}</option>
                                </c:if>
                                <c:if test="${requestScope.SUBJECT eq subject.id}">
                                    <option selected value="${subject.id}">${subject.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div><br/>
                    <div>
                        Question content: <textarea requied="true" name="txtContent" rows="4" cols="30">${requestScope.QUESTION_CONTENT}</textarea>
                    </div><br/>
                    <c:if test="${requestScope.ERROR ne null 
                                  && requestScope.ERROR.questionBlank ne null
                                  && requestScope.ERROR.questionBlank ne ''}">
                          <font color="red">
                          ${requestScope.ERROR.questionBlank}
                          </font><br/><br/>
                    </c:if>

                    Answer A: <input type="text" name="txtAnswerA" required="true" value="${requestScope.ANSWERA.answerContent}" /><br/><br/>

                    Answer B: <input type="text" name="txtAnswerB" required="true" value="${requestScope.ANSWERB.answerContent}" /><br><br/>

                    Answer C: <input type="text" name="txtAnswerC" required="true" value="${requestScope.ANSWERC.answerContent}" /><br><br/>

                    Answer D: <input type="text" name="txtAnswerD" required="true" value="${requestScope.ANSWERD.answerContent}" /><br><br/>

                    <c:if test="${requestScope.ERROR ne null 
                                  && requestScope.ERROR.answerBlank ne null
                                  && requestScope.ERROR.answerBlank ne ''}">
                          <font color="red">
                          ${requestScope.ERROR.answerBlank}
                          </font><br/><br/>
                    </c:if>

                    Choose correct answer:<br/>
                    <c:if test="${requestScope.ANSWERA.correctAnswer}">
                        A.<input type="radio" name="rdAnswer" checked='checked' value="A"/>
                    </c:if>
                    <c:if test="${!requestScope.ANSWERA.correctAnswer}">
                        A.<input type="radio" name="rdAnswer" value="A"/>
                    </c:if>
                    <c:if test="${requestScope.ANSWERB.correctAnswer}">
                        B.<input type="radio" name="rdAnswer" checked='checked' value="B"/>
                    </c:if>
                    <c:if test="${!requestScope.ANSWERB.correctAnswer}">
                        B.<input type="radio" name="rdAnswer" value="B"/>
                    </c:if>
                    <c:if test="${requestScope.ANSWERC.correctAnswer}">
                        C.<input type="radio" name="rdAnswer" checked='checked' value="C"/>
                    </c:if>
                    <c:if test="${!requestScope.ANSWERC.correctAnswer}">
                        C.<input type="radio" name="rdAnswer" value="C"/>
                    </c:if>
                    <c:if test="${requestScope.ANSWERD.correctAnswer}">
                        D.<input type="radio" name="rdAnswer" checked='checked' value="D"/>
                    </c:if>
                    <c:if test="${!requestScope.ANSWERD.correctAnswer}">
                        D.<input type="radio" name="rdAnswer" value="D"/>
                    </c:if><br/><br/>
                    <c:if test="${requestScope.ERROR ne null 
                                  && requestScope.ERROR.correctAnswerBlank ne null
                                  && requestScope.ERROR.correctAnswerBlank ne ''}">
                          <font color="red">
                          ${requestScope.ERROR.correctAnswerBlank}
                          </font><br/><br/>
                    </c:if>
                    <c:if test="${requestScope.STATUS ne null}">
                        <font color="red">
                        ${requestScope.STATUS}
                        </font><br/><br/>
                    </c:if>
                    <input type="submit" value="Create question" /><br/><br/>
                    <c:url value="/onloadTC" var="url">
                    </c:url>
                    <a href="${url}">Click here to go to home page</a>

                </div>
            </form>
        </div>
    </body>
</html>
