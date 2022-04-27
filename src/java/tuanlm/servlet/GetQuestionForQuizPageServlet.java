/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tuanlm.dto.AnswersSourceDTO;
import tuanlm.dto.QuestionsSourceDTO;
import tuanlm.utils.QuizQuestionPagingModel;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "GetQuestionForQuizPageServlet", urlPatterns = {"/GetQuestionForQuizPageServlet"})
public class GetQuestionForQuizPageServlet extends HttpServlet {

    private final String STUDENTPAGE = "studentPage.jsp";
    private final String SUBMIT = "SubmitServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String curPages[] = request.getParameterValues("curPage");
        String url = STUDENTPAGE;
        String curPage = "1";
        String timer = request.getParameter("txtTimeQuiz");
        Long timerNum = null;
        HttpSession session = request.getSession(false);
        if(timer == null && session.getAttribute("TIMER") != null) {
            timerNum = (Long) session.getAttribute("TIMER");
        }
        if(timer != null && session.getAttribute("TIMER") != null) {
            timerNum = Long.parseLong(timer);
        }
        
        try {
            if (curPages == null || curPages.length == 0) {
                url = SUBMIT;
            } else {
                for (String curPageItem : curPages) {
                    if (curPageItem != null && curPageItem.trim().length() > 0) {
                        curPage = curPageItem;
                    }
                }

                String oldPage = request.getParameter("oldPage");
                Integer curPageNum = 1;
                if(curPage.length() > 0 && curPage.matches("[0-9]{1,}")) {
                    curPageNum = Integer.parseInt(curPage);
                }
                String answersChoose[] = request.getParameterValues("rdAns");
                String answerChoose = null;
                if (answersChoose != null) {
                    for (String string : answersChoose) {
                        if (string != null && !string.trim().equals("")) {
                            answerChoose = string;
                        }
                    }
                }

                
                if (session != null && session.getAttribute("ROLE") != null
                        && session.getAttribute("ROLE").equals("student")) {
                    if (session.getAttribute("QUIZ_QUESTION") != null) {
                        QuizQuestionPagingModel quizQuestionPagingModel = new QuizQuestionPagingModel();
                        List<List> mapListQuesAndAns = (List<List>) session.getAttribute("QUIZ_QUESTION");
                        int totalPage = quizQuestionPagingModel.getTotalPage(mapListQuesAndAns);
                        if(curPageNum > totalPage) {
                            curPageNum = totalPage;
                        }
                        List<List> pagingList = quizQuestionPagingModel.loadPaging(mapListQuesAndAns, curPageNum);

                        
                        request.setAttribute("TOTALPAGE", totalPage);

                        // save answer to session
                        List<List> updatedList = (List<List>) session.getAttribute("PAGINGQUESTIONLIST" + oldPage);
                        int index = -1;
                        if (answerChoose != null && answerChoose.equals("A")) {
                            index = 0;
                        } else if (answerChoose != null && answerChoose.equals("B")) {
                            index = 1;
                        } else if (answerChoose != null && answerChoose.equals("C")) {
                            index = 2;
                        } else if (answerChoose != null && answerChoose.equals("D")) {
                            index = 3;
                        }

                        if (updatedList != null) {
                            String detailAns = null;
                            if (index >= 0) {
                                detailAns = ((List<AnswersSourceDTO>) updatedList.get(0).get(1)).get(index).getAnswerContent();
                            }
                            if (updatedList.get(0).size() >= 3) {
                                updatedList.get(0).remove(2);
                                updatedList.get(0).add(detailAns);
                            } else {
                                updatedList.get(0).add(detailAns);
                            }
                            
                            int questionIdOfUpdated = ((QuestionsSourceDTO) updatedList.get(0).get(0)).getId();
                            int tmpCount = -1;
                            for (int count = 0; count < mapListQuesAndAns.size(); count++) {
                                if (((QuestionsSourceDTO) mapListQuesAndAns.get(count).get(0)).getId() == questionIdOfUpdated) {
                                    tmpCount = count;
                                }
                            }
                            mapListQuesAndAns.get(tmpCount).add(2, detailAns);
                            
                            session.setAttribute("PAGINGQUESTIONLIST" + oldPage, updatedList);
                        } else {
                            String detailAns = null;
                            if (index >= 0) {
                                detailAns = ((List<AnswersSourceDTO>) pagingList.get(0).get(1)).get(index).getAnswerContent();
                            }
                            pagingList.get(0).add(detailAns);
                            session.setAttribute("PAGINGQUESTIONLIST" + oldPage, pagingList);
                        }

                        //get question for paging view
                        if (session.getAttribute("PAGINGQUESTIONLIST" + curPageNum) != null) {
                            List<List> existPagingQuestion = (List<List>) session.getAttribute("PAGINGQUESTIONLIST" + curPageNum);
                            if (existPagingQuestion.get(0).size() >= 3) {
                                String oldAnswer = (String) existPagingQuestion.get(0).get(2);
                                request.setAttribute("ANSWERCHOOSE", oldAnswer);
                            }
                            session.setAttribute("PAGINGQUESTIONLIST", existPagingQuestion);
                        } else {
                            session.setAttribute("PAGINGQUESTIONLIST", pagingList);
                            session.setAttribute("PAGINGQUESTIONLIST" + curPageNum, pagingList);
                        }
                        session.setAttribute("QUIZ_QUESTION", mapListQuesAndAns);
                    }
                    session.setAttribute("TIMER", timerNum);
                    request.setAttribute("CURPAGE", curPageNum);
                }
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
