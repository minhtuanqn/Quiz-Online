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
import tuanlm.dao.QuizesDAO;
import tuanlm.dao.SubjectsDAO;
import tuanlm.dto.QuizesDTO;
import tuanlm.dto.SubjectsDTO;
import tuanlm.utils.QuestionHistoryPagingModel;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "SearchHistoryQuizServlet", urlPatterns = {"/SearchHistoryQuizServlet"})
public class SearchHistoryQuizServlet extends HttpServlet {

    private final String HISTORYPAGE = "history.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String subject = request.getParameter("cbbSubject");
        String curPage = request.getParameter("curPage");
        Integer curpageNum = 1;
        if(curPage != null && curPage.length() > 0 && curPage.matches("[0-9]{1,}")) {
            curpageNum = Integer.parseInt(curPage);
        }
        String url = HISTORYPAGE ;
                 
        try {
            HttpSession session = request.getSession(false);
            if (subject != null && subject.length() > 0) {
                if (session != null) {
                    if (session.getAttribute("WELLCOME_EMAIL") != null) {
                        String email = (String) session.getAttribute("WELLCOME_EMAIL");
                        QuizesDAO dao = new QuizesDAO();
                        List<QuizesDTO> quizList = dao.getAllQuizByUserAndSubject(email, subject);
                        QuestionHistoryPagingModel questionHistoryPagingModel = new QuestionHistoryPagingModel();
                        Integer totalPage = questionHistoryPagingModel.getTotalPage(quizList);
                        if(curpageNum > totalPage) {
                            curpageNum = totalPage;
                        }
                        List pagingList = questionHistoryPagingModel.loadPaging(quizList, curpageNum);
                        
                        url += "?curPage=" + curpageNum;
                        if(quizList != null) {
                            request.setAttribute("QUIZLIST", pagingList);
                        } 
                        request.setAttribute("TOTALPAGE", totalPage);
                    }
                }
            }
            SubjectsDAO dao = new SubjectsDAO();
            List<SubjectsDTO> subjects = dao.getAllSubjects();
            request.setAttribute("SUBJECTS", subjects);

        } catch (SQLException ex) {
            log("SearchHistoryQuizServlet_SQL: " + ex.getMessage());
        } 
        catch (NamingException ex) {
            log("SearchHistoryQuizServlet_Naming: " + ex.getMessage());
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
