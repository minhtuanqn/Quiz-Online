/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tuanlm.dao.AccountsDAO;
import tuanlm.dto.AccountsDTO;
import tuanlm.utils.AccountCreationObject;
import tuanlm.utils.SHAEncryption;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {

    private final String CREATE_PAGE = "createAccount.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String passwordAgain = request.getParameter("txtPasswordAgain");
        String name = request.getParameter("txtName");
        String url = CREATE_PAGE;
        try {
            AccountCreationObject error = new AccountCreationObject();
            boolean checkError = false;
            if (email == null || email.trim().equals("")) {
                error.setEmailBlank("Email can not be blank");
                checkError = true;
            }
            else {
                boolean checkNotvalidEmail = email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
                if(!checkNotvalidEmail) {
                    error.setEmailNotValid("Email is not valid");
                    checkError = true;
                }
            }
            if (password == null || password.trim().equals("") || password.length() < 6 || password.length() > 20) {
                error.setPasswordNotValid("Password must be from 6-20 characters");
                checkError = true;
            } else {
                if (!password.equals(passwordAgain)) {
                    error.setPasswordNotDuplicate("Password must be duplicated");
                    checkError = true;
                }
            }
            if (name == null || name.trim().equals("")) {
                error.setNameBlank("Name can not be blank");
                checkError = true;
            }
            AccountsDAO dao = new AccountsDAO();
            boolean checkExistEmail = dao.checkExistEmail(email);
            if (checkExistEmail) {
                error.setEmailDuplicate("This email does existed");
                checkError = true;
            }
            if (!checkError) {
                String encryptedPassword = new SHAEncryption().encryptPassword(password);
                AccountsDTO dto = new AccountsDTO();
                dto.setEmail(email);
                dto.setName(name);
                dto.setPassword(encryptedPassword);

                boolean checkInsert = dao.createAccount(dto);
                if (checkInsert) {
                    request.setAttribute("STATUS_CREATE", "Create account successfuly");
                }
            }
            else {
                request.setAttribute("ERROR", error);
                request.setAttribute("NAME", name);
                request.setAttribute("EMAIL", email);
            }
            
        } catch (NamingException ex) {
            log("CreateNewAccountServlet_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
            log("CreateNewAccountServlet_SQL: " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            log("CreateNewAccountServlet_NoSuchAlgorithm: " + ex.getMessage());
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
