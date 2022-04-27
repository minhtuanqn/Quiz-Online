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
import tuanlm.dao.QuestionsSourceDAO;
import tuanlm.dao.SubjectsDAO;
import tuanlm.dto.QuestionsSourceDTO;
import tuanlm.dto.SubjectsDTO;
import tuanlm.utils.QuestionPagingModel;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "SearchQuestionServlet", urlPatterns = {"/SearchQuestionServlet"})
public class SearchQuestionServlet extends HttpServlet {

    private final String TEACHER_PAGE = "teacherPage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String questionName = request.getParameter("txtSearchName");
        String active = request.getParameter("chkActive");
        String subject = request.getParameter("cbbSubject");
        String curPage = request.getParameter("curPage");

        Integer curNumPage = 1;
        if (curPage != null && curPage.length() > 0 && curPage.matches("[0-9]{1,}")) {
            curNumPage = Integer.parseInt(curPage);
        }

        String url = TEACHER_PAGE;
        boolean checkSearch = false;

        boolean isActive = false;
        if (active != null && !active.equals("")) {
            isActive = true;
        }
        else {
            isActive = false;
        }
        try {
            QuestionsSourceDAO questionDAO = new QuestionsSourceDAO();
            List<QuestionsSourceDTO> questionList = questionDAO.searchQuestion(questionName, subject, isActive);
            AnswersSourceDAO answerDAO = new AnswersSourceDAO();
            QuestionPagingModel pagingModel = new QuestionPagingModel();

            List<List> mapQuesAndAnsList = answerDAO.getAnswersByQuestionList(questionList);

            int totalPage = pagingModel.getTotalPage(mapQuesAndAnsList);
            if (curNumPage > totalPage) {
                curNumPage = totalPage;
            }
            List<List> pagingMapList = pagingModel.loadPaging(mapQuesAndAnsList, curNumPage);
            request.setAttribute("QUESTIONANDANSWER", pagingMapList);
            request.setAttribute("TOTALPAGE", totalPage);

            SubjectsDAO dao = new SubjectsDAO();
            List<SubjectsDTO> subjects = dao.getAllSubjects();
            request.setAttribute("SUBJECTS", subjects);

            if (active != null && checkSearch == false) {
                url += "?chkActive=" + active;
                checkSearch = true;
            }
            if (subject != null && !subject.trim().equals("") && checkSearch == true) {
                url += "&cbbSubject=" + subject;
            }
            if (subject != null && !subject.trim().equals("") && checkSearch == false) {
                url += "?cbbSubject=" + subject;
                checkSearch = true;
            }
            if (questionName != null && !questionName.trim().equals("") && checkSearch == false) {
                url += "?txtSearchName=" + questionName;
                checkSearch = true;
            }
            if (questionName != null && !questionName.trim().equals("") && checkSearch == true) {
                url += "&txtSearchName=" + questionName;
            }
            if (curPage != null && !curPage.trim().equals("") && checkSearch == false) {
                url += "?curPage=" + curPage;
            }
            if (curPage != null && !curPage.trim().equals("") && checkSearch == true) {
                url += "&curPage=" + curPage;
            }
            if (curPage == null && checkSearch == false) {
                url += "?curPage=" + 1;
            }
            if (curPage == null && checkSearch == true) {
                url += "&curPage=" + 1;
            }
            

        } catch (NamingException ex) {
            log("SearchQuestionServlet_Naming: " + ex.getMessage());
        } 
        catch (SQLException ex) {
            log("SearchQuestionServlet_SQL: " + ex.getMessage());
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
