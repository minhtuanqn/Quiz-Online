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
import tuanlm.dao.AnswersSourceDAO;
import tuanlm.dao.SubjectsDAO;
import tuanlm.dto.AnswersSourceDTO;
import tuanlm.dto.SubjectsDTO;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "OnloadUpdatePageServlet", urlPatterns = {"/OnloadUpdatePageServlet"})
public class OnloadUpdatePageServlet extends HttpServlet {

    private final String UPDATE_PAGE = "updatePage.jsp";
    private final String NOT_FOUND = "notfound.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String contentQuestion = request.getParameter("txtContentQuestion");
        String questionId = request.getParameter("txtQuestionId");
        String subjectId = request.getParameter("txtSubjectId");
        Integer questionNumber = -1;
        String url = UPDATE_PAGE;
        try  {
            if (questionId != null && questionId.length() > 0 && questionId.matches("[0-9]{1,}")) {
                questionNumber = Integer.parseInt(questionId);
            }
            AnswersSourceDAO ansDAO = new AnswersSourceDAO();
            List<AnswersSourceDTO> answerList = ansDAO.getAllAnswerByQuestionId(questionNumber);
            if(answerList != null && answerList.size() > 0) {
                request.setAttribute("ANSWERA", answerList.get(0));
                request.setAttribute("ANSWERB", answerList.get(1));
                request.setAttribute("ANSWERC", answerList.get(2));
                request.setAttribute("ANSWERD", answerList.get(3));
                request.setAttribute("QUESTION_CONTENT", contentQuestion);
                
                request.setAttribute("SELECTED_SUBJECT", subjectId);
                request.setAttribute("QUESTION_ID", questionId);
            }
            else {
                url = NOT_FOUND;
            }
            
            
            SubjectsDAO dao = new SubjectsDAO();
            List<SubjectsDTO> subjects = dao.getAllSubjects();
            request.setAttribute("SUBJECTS", subjects);
        }
        catch (SQLException ex) {
            log("OnloadUpdatePageServlet_SQL: " + ex.getMessage());
        } 
        catch (NamingException ex) {
            log("OnloadUpdatePageServlet_Naming: " + ex.getMessage());
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
