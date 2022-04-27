<%-- 
    Document   : studentPage
    Created on : Jan 28, 2021, 9:27:57 PM
    Author     : MINH TUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <style>
            h1 {
                display: block;
                text-align: center;
                background-color: #8080801c;
            }
            .optionChooser {
                text-align: center;
            }
            .question {

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
        <h1>Take quiz online</h1>
        <c:set var="curPage" value="${requestScope.CURPAGE}"/>
        <c:set var="oldPage" value="${curPage}"/>
        <div class="optionChooser">
            <form action="createQuizST">
                <c:if test="${sessionScope.PAGINGQUESTIONLIST eq null}">
                    <a href="historyST">View quiz history</a><br/><br/>
                    Subject: <select name="cbbSubject">
                        <c:forEach var="subject" items="${requestScope.SUBJECTS}">
                            <c:if test="${param.cbbSubject eq subject.id}">
                                <option selected value="${subject.id}">${subject.name}</option>
                            </c:if>
                            <c:if test="${param.cbbSubject ne subject.id}">
                                <option value="${subject.id}">${subject.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <input type="submit" value="Start Quiz" /><br/><br/>
                </c:if>
                <c:if test="${sessionScope.SUBJECT ne null}">
                    Subject: ${sessionScope.SUBJECT}<br/><br/>
                </c:if>
            </form>
        </div>
        <c:if test="${requestScope.NOT_ENOUGH_QUES eq null}">
            <c:if test="${requestScope.SUBMIT_STATUS ne null}">
                <h3 style="text-align:  center; color: red">${requestScope.SUBMIT_STATUS}</h3>
            </c:if>
            <div>
                <form onsubmit="return(saveTime())" id="submit" action="getQuestionForQuizPageST" method="post">
                    <div>
                        <c:if test="${sessionScope.PAGINGQUESTIONLIST ne null}">
                            <div style="text-align: center" >
                                <input type="hidden" id="hiddentTimer" name="txtTimeQuiz" value=""/> 
                                <input type="submit" value="Submit" /><br/>
                                <div>
                                    <div style="height: 30px;" class="container">
                                        <p id="timer"></p>
                                    </div>
                                </div>
                            </div>
                                
                            <c:forEach var="questionAndAns" items="${sessionScope.PAGINGQUESTIONLIST}">
                                <div class="content" style="display: block; border: groove; width: 70%; margin: 0 auto; margin-top: 30px; margin-bottom: 30px;">
                                    <div class="question">
                                        Question: ${questionAndAns.get(0).questionContent}
                                    </div><br/>
                                    <div class="answer">
                                        Answer:
                                        <c:forEach var="answer" items="${questionAndAns.get(1)}" varStatus="counter">
                                            <c:set var="questionId" value="${questionAndAns.get(0).id}"/>
                                            <c:if test="${counter.count eq '1'}">
                                                <c:if test="${requestScope.ANSWERCHOOSE ne null && requestScope.ANSWERCHOOSE eq answer.answerContent}">
                                                    <div>
                                                        <input type="radio" checked="checked" name="rdAns" value="A" />
                                                        A. ${answer.answerContent}
                                                    </div>
                                                </c:if>
                                                <c:if test="${requestScope.ANSWERCHOOSE eq null || requestScope.ANSWERCHOOSE ne answer.answerContent}">
                                                    <div>
                                                        <input type="radio" name="rdAns" value="A" />
                                                        A. ${answer.answerContent}
                                                    </div>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${counter.count eq '2'}">
                                                <c:if test="${requestScope.ANSWERCHOOSE ne null && requestScope.ANSWERCHOOSE eq answer.answerContent}">
                                                    <div>
                                                        <input type="radio" checked="checked" name="rdAns" value="B" />
                                                        B. ${answer.answerContent}
                                                    </div>
                                                </c:if>
                                                <c:if test="${requestScope.ANSWERCHOOSE eq null || requestScope.ANSWERCHOOSE ne answer.answerContent}">
                                                    <div>
                                                        <input type="radio" name="rdAns" value="B" />
                                                        B. ${answer.answerContent}
                                                    </div>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${counter.count eq '3'}">
                                                <c:if test="${requestScope.ANSWERCHOOSE ne null && requestScope.ANSWERCHOOSE eq answer.answerContent}">
                                                    <div>
                                                        <input type="radio" checked="checked" name="rdAns" value="C" />
                                                        C. ${answer.answerContent}
                                                    </div>
                                                </c:if>
                                                <c:if test="${requestScope.ANSWERCHOOSE eq null || requestScope.ANSWERCHOOSE ne answer.answerContent}">
                                                    <div>
                                                        <input type="radio" name="rdAns" value="C" />
                                                        C. ${answer.answerContent}
                                                    </div>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${counter.count eq '4'}">
                                                <c:if test="${requestScope.ANSWERCHOOSE ne null && requestScope.ANSWERCHOOSE eq answer.answerContent}">
                                                    <div>
                                                        <input type="radio" checked="checked" name="rdAns" value="D" />
                                                        D. ${answer.answerContent}
                                                    </div>
                                                </c:if>
                                                <c:if test="${requestScope.ANSWERCHOOSE eq null || requestScope.ANSWERCHOOSE ne answer.answerContent}">
                                                    <div>
                                                        <input type="radio" name="rdAns" value="D" />
                                                        D. ${answer.answerContent}
                                                    </div>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <div style="margin: 0 auto; width: 50%; text-align: center">
                            <c:if test="${requestScope.SCORE ne null}">
                                <h3>Total question: ${requestScope.TOTALQUESTION}<h3>
                                <h3>Number of correct answer: ${requestScope.CORRECTQUESTION}</h3>
                                <h3>You mark: ${requestScope.SCORE}</h3>
                            </c:if>
                        </div>

                    </div>

                    <div class="paging" style="text-align: center; margin-bottom:  50px;">   
                        <c:set var="totalPage" value="${requestScope.TOTALPAGE}"/>
                        <c:if test="${totalPage gt 1}">
                            <c:forEach begin="${0}" end="${totalPage + 1}" var="page" step="${1}">
                                <c:if test="${page ne curPage && page lt 1 && oldPage eq 1}">
                                    <button name="curPage" disabled value="${oldPage - 1}" type="submit" class="pagingBtn">Previous question</button>
                                    <input type="hidden" name="oldPage" value="${oldPage}" />
                                    <input type="hidden" name="questionId" value="${questionId}" />
                                </c:if>
                                <c:if test="${page ne curPage && page lt 1 && oldPage ne 1}">
                                    <button name="curPage"  value="${oldPage - 1}" type="submit" class="pagingBtn">Previous question</button>
                                    <input type="hidden" name="oldPage" value="${oldPage}" />
                                    <input type="hidden" name="questionId" value="${questionId}" />
                                </c:if>
                                <c:if test="${page eq curPage && page le totalPage && page gt 0}">
                                    <div  style="display: inline; margin-left: 5px  ">
                                        <input type="submit" class="pagingBtn" name="curPage" value="${page}" style="background: yellow" />
                                        <input type="hidden" name="oldPage" value="${oldPage}" />
                                        <input type="hidden" name="questionId" value="${questionId}" />
                                    </div>
                                </c:if>
                                <c:if test="${page ne curPage && page le totalPage && page gt 0}">
                                    <div style="display: inline;  margin-left: 5px; ">
                                        <input type="submit" class="pagingBtn"  name="curPage" value="${page}" />
                                        <input type="hidden" name="oldPage" value="${oldPage}" />
                                        <input type="hidden" name="questionId" value="${questionId}" />
                                    </div>
                                </c:if>
                                <c:if test="${page ne curPage && page gt totalPage && oldPage eq totalPage}">
                                    <button name="curPage" disabled value="${oldPage + 1}" type="submit" class="pagingBtn">Next question</button>
                                    <input type="hidden" name="oldPage" value="${oldPage}" />
                                    <input type="hidden" name="questionId" value="${questionId}" />
                                </c:if>
                                <c:if test="${page ne curPage && page gt totalPage && oldPage ne totalPage}">
                                    <button name="curPage" value="${oldPage + 1}" type="submit" class="pagingBtn">Next question</button>
                                    <input type="hidden" name="oldPage" value="${oldPage}" />
                                    <input type="hidden" name="questionId" value="${questionId}" />
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </form>
            </div>
        </c:if>
        <c:if test="${requestScope.NOT_ENOUGH_QUES ne null}">
            <h3 style="text-align: center">${requestScope.NOT_ENOUGH_QUES}</h3>
        </c:if>
        
    </body>
    <script>
        var countDownDate = ${empty sessionScope.TIMER ? 0 : sessionScope.TIMER};

        // cập nhập thời gian sau mỗi 1 giây
        var x = setInterval(function () {


            // Lấy thời gian hiện tại
            var now = new Date().getTime();

            // Lấy số thời gian chênh lệch
            var distance = countDownDate - now;

            // Tính toán số ngày, giờ, phút, giây từ thời gian chênh lệch
            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            // HIển thị chuỗi thời gian trong thẻ p
            document.getElementById("timer").innerHTML = hours + ":"
                    + minutes + ":" + seconds;

            // Nếu thời gian kết thúc, hiển thị chuỗi thông báo
            if (distance <= 0) {
                clearInterval(x);
                $('#submit').submit();
                return;
            }
        }, 1000);

        function saveTime() {
            var remainTime = countDownDate;
            var timeSession = document.getElementById("hiddentTimer");
            timeSession.value = remainTime;
        }


    </script>
</html>
