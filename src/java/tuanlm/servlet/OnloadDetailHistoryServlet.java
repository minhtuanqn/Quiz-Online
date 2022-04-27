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
import tuanlm.dao.AnswersInquizDAO;
import tuanlm.dao.QuestionsInQuizDAO;
import tuanlm.dao.QuizesDAO;
import tuanlm.dto.QuestionsInQuizDTO;
import tuanlm.dto.QuizesDTO;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "OnloadDetailHistoryServlet", urlPatterns = {"/OnloadDetailHistoryServlet"})
public class OnloadDetailHistoryServlet extends HttpServlet {

    private final String HISTORY_DETAIL = "historyDetailPage.jsp";
    private final String NOT_FOUND = "notfound.jsp";
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = HISTORY_DETAIL;
        String searchId = request.getParameter("txtSearch");
        String quizId = request.getParameter("quizId");
        try {
            if(quizId != null && quizId.length() > 0 && quizId.matches("[0-9]{1,}")) {
                Integer  quizIdNum = Integer.parseInt(quizId);
                QuizesDAO quizesDAO = new QuizesDAO();
                QuizesDTO quizesDTO = quizesDAO.getQuizById(quizIdNum);
                if(quizesDTO != null) {
                    request.setAttribute("QUIZ", quizesDTO);
                }
                QuestionsInQuizDAO questionInQuizDAO = new QuestionsInQuizDAO();
                List<QuestionsInQuizDTO> questionList = questionInQuizDAO.getQuestionInQuizByQuizId(quizIdNum);
                HttpSession session = request.getSession(false);
                if(questionList != null && questionList.size() > 0 && session != null 
                        && session.getAttribute("WELLCOME_EMAIL") != null 
                        && quizesDTO != null
                        && ((String) session.getAttribute("WELLCOME_EMAIL")).equals(quizesDTO.getUser())) {
                    AnswersInquizDAO answersInquizDAO = new AnswersInquizDAO();
                    List mapQuesAndAns = answersInquizDAO.getAnsByQues(questionList);
                    request.setAttribute("MAPQUESANDANS", mapQuesAndAns);
                    request.setAttribute("SEARCH", searchId);
                }
                else {
                    url = NOT_FOUND;
                }
            }
            else {
                url = NOT_FOUND;
            }
            
        }
        catch (NamingException ex) {
            log("OnloadDetailHistoryServlet_Naming: " + ex.getMessage());
        } 
        catch (SQLException ex) {
            log("OnloadDetailHistoryServlet_SQL: " + ex.getMessage());
        }        
        finally {
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
