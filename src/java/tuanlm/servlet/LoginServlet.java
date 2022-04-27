/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tuanlm.dao.AccountsDAO;
import tuanlm.dto.AccountsDTO;
import tuanlm.utils.SHAEncryption;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String LOGIN_PAGE = "loginPage";
    private final String ONLOAD_TEACHER = "onloadTC";
    private final String ONLOAD_STUDENT = "onloadStudentST";
    private final String CANNOT_ACCESS = "cannotAccess";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String url = LOGIN_PAGE;
        try {
            AccountsDAO dao = new AccountsDAO();
            String encryptedPassord = new SHAEncryption().encryptPassword(password);
            AccountsDTO dto = dao.checkLogin(email, encryptedPassord);
            if (dto != null) {
                if(dto.getRole().equals("admin")) {
                    url = ONLOAD_TEACHER;
                }
                else if(dto.getRole().equals("student")) {
                    url = ONLOAD_STUDENT;
                }
                else {
                    url = CANNOT_ACCESS;
                }
                
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
                    session.invalidate();
                }
                
                
                session = request.getSession(true);
                session.setAttribute("WELLCOME_EMAIL", dto.getEmail());
                session.setAttribute("WELLCOME_NAME", dto.getName());
                session.setAttribute("ROLE", dto.getRole());
            }
            else {
                url += "?Status=Email or password does not existed";
            }

        } catch (NoSuchAlgorithmException ex) {
            log("LoginServlet_NoSuchAlgorithm: " + ex.getMessage());
        } 
        catch (NamingException ex) {
            log("LoginServlet_Naming: " + ex.getMessage());
        } 
        catch (SQLException ex) {
            log("LoginServlet_SQL: " + ex.getMessage());
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
