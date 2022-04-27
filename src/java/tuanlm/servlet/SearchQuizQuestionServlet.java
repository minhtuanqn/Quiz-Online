/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tuanlm.dao.AnswersSourceDAO;
import tuanlm.dao.QuestionsSourceDAO;
import tuanlm.dao.SubjectsDAO;
import tuanlm.dto.QuestionsSourceDTO;
import tuanlm.dto.SubjectsDTO;
import tuanlm.utils.QuizQuestionPagingModel;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "SearchQuizQuestionServlet", urlPatterns = {"/SearchQuizQuestionServlet"})
public class SearchQuizQuestionServlet extends HttpServlet {

    private final String STUDENT_PAGE = "studentPage.jsp";
    private final String GETQUESTIONFORQUIZPAGE = "getQuestionForQuizPageST";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String subjctId = request.getParameter("cbbSubject");
        String url = STUDENT_PAGE;

        try {
            
            //load list subjects
            SubjectsDAO dao = new SubjectsDAO();
            List<SubjectsDTO> subjects = dao.getAllSubjects();
            request.setAttribute("SUBJECTS", subjects);
            
            HttpSession session = request.getSession(false);
            if (session != null) {
                if (session.getAttribute("QUIZ_QUESTION") != null) {
                    List<List> mapQuesAndAns = (List<List>) session.getAttribute("QUIZ_QUESTION");
                    if (mapQuesAndAns != null) {
                        int totalQues = mapQuesAndAns.size();
                        for (int count = 1; count < totalQues; count++) {
                            if (session.getAttribute("PAGINGQUESTIONLIST" + count) != null) {
                                session.removeAttribute("PAGINGQUESTIONLIST" + count);
                            }
                        }
                    }
                    session.removeAttribute("QUIZ_QUESTION");
                }
                if (session.getAttribute("PAGINGQUESTIONLIST") != null) {
                    session.removeAttribute("PAGINGQUESTIONLIST");
                }
                if (session.getAttribute("TIMER") != null) {
                    session.removeAttribute("TIMER");
                }
                if (session.getAttribute("SUBJECT") != null) {
                    session.removeAttribute("SUBJECT");
                }
            }

            SubjectsDAO subjectsDAO = new SubjectsDAO();
            Integer numberOfQuestion = subjectsDAO.getNumberOfQuestionInQuiz(subjctId);
            Integer timerOfQuiz = subjectsDAO.getTimeOfQuiz(subjctId);
            if (numberOfQuestion != null) {

                QuestionsSourceDAO questionsSourceDAO = new QuestionsSourceDAO();
                List<QuestionsSourceDTO> questionList = questionsSourceDAO.createListQuestionForQuiz(subjctId, numberOfQuestion);
                if (questionList != null && questionList.size() >= numberOfQuestion) {
                    AnswersSourceDAO answersSourceDAO = new AnswersSourceDAO();
                    List<List> mapQuestionAndAns = answersSourceDAO.getAnswersByQuestionList(questionList);

                    QuizQuestionPagingModel quizQuestionPaginfModel = new QuizQuestionPagingModel();
                    int totalPage = quizQuestionPaginfModel.getTotalPage(mapQuestionAndAns);
                    request.setAttribute("TOTALPAGE", totalPage);

                    List<List> pagingList = quizQuestionPaginfModel.loadPaging(mapQuestionAndAns, 1);

                    if (session != null && session.getAttribute("ROLE") != null
                            && session.getAttribute("ROLE").equals("student")) {
                        session.setAttribute("QUIZ_QUESTION", mapQuestionAndAns);
                        session.setAttribute("PAGINGQUESTIONLIST1", pagingList);
                        session.setAttribute("PAGINGQUESTIONLIST", pagingList);
                        session.setAttribute("SUBJECT", subjctId);
                        session.setAttribute("TIMER", timerOfQuiz * 60 * 1000 + new Date().getTime());

                    }
                    Integer questionIdOfFirstPage = ((QuestionsSourceDTO) pagingList.get(0).get(0)).getId();
                    url = GETQUESTIONFORQUIZPAGE
                            + "?curPage=1&oldPage=1"
                            + "&questionId=" + questionIdOfFirstPage;
                    response.sendRedirect(url);
                } 
                else {
                    request.setAttribute("NOT_ENOUGH_QUES", "Not enough question for quiz");
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }
            }

            
        } 
        catch (SQLException ex) {
            log("SearchQuizQuestionServlet_SQL: " + ex.getMessage());
        } 
        catch (NamingException ex) {
            log("SearchQuizQuestionServlet_Naming: " + ex.getMessage());
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
