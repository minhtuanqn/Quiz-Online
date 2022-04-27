/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tuanlm.dao.QuestionsSourceDAO;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "DeleteQuestionServlet", urlPatterns = {"/DeleteQuestionServlet"})
public class DeleteQuestionServlet extends HttpServlet {

    private final String SEARCH_SERVLET = "searchTC";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String questionId = request.getParameter("txtQuestionId");
        Integer questionIdNum = Integer.parseInt(questionId);
        String subject = request.getParameter("cbbSubject");
        String questionName = request.getParameter("txtSearchName");
        String active = request.getParameter("chkActive");
        String curPage = request.getParameter("curPage");
        
        String url = SEARCH_SERVLET;
        boolean checkSearch = false;
        if(active != null && checkSearch == false) {
            url += "?chkActive=" + active;
            checkSearch = true;
        }
        if(subject != null && !subject.trim().equals("") && checkSearch == true) {
            url += "&cbbSubject=" + subject;
        }
        if(subject != null && !subject.trim().equals("") && checkSearch == false) {
            url += "?cbbSubject=" + subject;
            checkSearch = true;
        }
        if(questionName != null && checkSearch == false) {
            url += "?txtSearchName=" + questionName;
            checkSearch = true;
        }
        if(questionName != null && checkSearch == true) {
            url += "&txtSearchName=" + questionName;
        }
        if(curPage != null && !curPage.trim().equals("") && checkSearch == false) {
            url += "?curPage=" + curPage;
        }
        if(curPage != null && !curPage.trim().equals("") && checkSearch == true) {
            url += "&curPage=" + curPage;
        }
        
        
        try  {
            QuestionsSourceDAO dao = new QuestionsSourceDAO();
            dao.deleteQuestion(questionIdNum);
            
        }
        catch (NamingException ex) {
            log("DeleteQuestionServlet_Naming: " + ex.getMessage());
        } 
        catch (SQLException ex) {
            log("DeleteQuestionServlet_SQL: " + ex.getMessage());
        }        
        finally {
            response.sendRedirect(url);
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
