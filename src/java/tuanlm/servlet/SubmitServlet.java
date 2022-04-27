/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tuanlm.dao.QuestionsInQuizDAO;
import tuanlm.dao.QuizesDAO;
import tuanlm.dto.AnswersSourceDTO;
import tuanlm.dto.QuestionsSourceDTO;
import tuanlm.dto.QuizesDTO;
import tuanlm.utils.QuizQuestionPagingModel;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "SubmitServlet", urlPatterns = {"/SubmitServlet"})
public class SubmitServlet extends HttpServlet {

    private final String STUDENT_PAGE = "OnloadStudentServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String oldPage = request.getParameter("oldPage");
        Integer oldPageNum = 1;
        if (oldPage != null && oldPage.length() > 0) {
            oldPageNum = Integer.parseInt(oldPage);
        }
        String lastAnswer = request.getParameter("rdAns");
        String url = STUDENT_PAGE;
        Integer totalQuestion = 0;
        try {
            //save answer to session
            QuizQuestionPagingModel quizQuestionPagingModel = new QuizQuestionPagingModel();

            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("ROLE") != null
                    && session.getAttribute("ROLE").equals("student")) {
                if (session.getAttribute("QUIZ_QUESTION") != null) {

                    List<List> mapAllQuesandAns = (List<List>) session.getAttribute("QUIZ_QUESTION");
                    List<List> pagingList = quizQuestionPagingModel.loadPaging(mapAllQuesandAns, oldPageNum);
                    List<List> updatedList = (List<List>) session.getAttribute("PAGINGQUESTIONLIST" + oldPage);
                    totalQuestion = quizQuestionPagingModel.getTotalPage(mapAllQuesandAns);
                    int index = -1;
                    String detailAns = null;
                    if (lastAnswer != null) {
                        if (lastAnswer.equals("A")) {
                            index = 0;
                        } else if (lastAnswer.equals("B")) {
                            index = 1;
                        } else if (lastAnswer.equals("C")) {
                            index = 2;
                        } else if (lastAnswer.equals("D")) {
                            index = 3;
                        }
                    }

                    if (updatedList != null) {

                        if (index >= 0) {
                            detailAns = ((List<AnswersSourceDTO>) updatedList.get(0).get(1)).get(index).getAnswerContent();
                        }
                        
                        int questionIdOfUpdated = ((QuestionsSourceDTO) updatedList.get(0).get(0)).getId();
                        int tmpCount = -1;
                        for (int count = 0; count < mapAllQuesandAns.size(); count++) {
                            if (((QuestionsSourceDTO) mapAllQuesandAns.get(count).get(0)).getId() == questionIdOfUpdated) {
                                tmpCount = count;
                            }
                        }
                        if(tmpCount >= 0) {
                            mapAllQuesandAns.get(tmpCount).add(2, detailAns);
                        }
                    } 
                    else {
                        if (index >= 0) {
                            detailAns = ((List<AnswersSourceDTO>) pagingList.get(0).get(1)).get(index).getAnswerContent();
                        }
                        pagingList.get(0).add(detailAns);
                    }

                    //Caculate number of answer correct
                    int correctAns = 0;
                    for (List quesAndAns : mapAllQuesandAns) {
                        if (quesAndAns.size() > 2) {
                            String answerOfStudent = (String) quesAndAns.get(2);
                            List<AnswersSourceDTO> anserSourceList = (List<AnswersSourceDTO>) quesAndAns.get(1);
                            for (AnswersSourceDTO answersSourceDTO : anserSourceList) {
                                if (answersSourceDTO.getAnswerContent().equals(answerOfStudent)) {
                                    if (answersSourceDTO.isCorrectAnswer()) {
                                        correctAns++;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    //Insert quiz to DB
                    String user = "";
                    String subject = "";
                    if (session.getAttribute("WELLCOME_EMAIL") != null) {
                        user = (String) session.getAttribute("WELLCOME_EMAIL");
                    }
                    if (session.getAttribute("SUBJECT") != null) {
                        subject = (String) session.getAttribute("SUBJECT");
                    }
                    QuizesDTO quizesDTO = new QuizesDTO(user, subject, totalQuestion, correctAns);
                    QuizesDAO quizesDAO = new QuizesDAO();
                    boolean checkInsertQuiz = quizesDAO.insertQuiz(quizesDTO);

                    //Insert question and answer to DB
                    if (checkInsertQuiz) {
                        Integer quizId = quizesDAO.getCurQuizId();
                        QuestionsInQuizDAO questionsInQuizDAO = new QuestionsInQuizDAO();
                        boolean checkInsertQuestionAndAns = questionsInQuizDAO.insertQuizHistoty(mapAllQuesandAns, quizId);
                        if (checkInsertQuestionAndAns) {
                            request.setAttribute("SUBMIT_STATUS", "Submit successfully");
                        } else {
                            request.setAttribute("SUBMIT_STATUS", "Submit fail");
                        }
                    }
                    request.setAttribute("TOTALQUESTION", totalQuestion);
                    request.setAttribute("CORRECTQUESTION", correctAns);
                    double score = correctAns * 1.0 / totalQuestion * 10;
                    request.setAttribute("SCORE", score);
                }

                //Delete data in session
                for (int count = 1; count <= totalQuestion; count++) {
                    if (session.getAttribute("PAGINGQUESTIONLIST" + count) != null) {
                        session.removeAttribute("PAGINGQUESTIONLIST" + count);
                    }
                }
                if (session.getAttribute("PAGINGQUESTIONLIST") != null) {
                    session.removeAttribute("PAGINGQUESTIONLIST");
                }
                if (session.getAttribute("SUBJECT") != null) {
                    session.removeAttribute("SUBJECT");
                }
                if (session.getAttribute("QUIZ_QUESTION") != null) {
                    session.removeAttribute("QUIZ_QUESTION");
                }
                if (session.getAttribute("TIMER") != null) {
                    session.removeAttribute("TIMER");
                }
            }
        } catch (NamingException ex) {
            log("SubmitServlet_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
            log("SubmitServlet_SQL: " + ex.getMessage());
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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException,
            IOException {
        processRequest(request,
                response
        );

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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException,
            IOException {
        processRequest(request,
                response
        );

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String
            getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
