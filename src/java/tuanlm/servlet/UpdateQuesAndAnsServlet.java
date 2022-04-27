/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import tuanlm.dto.AnswersSourceDTO;
import tuanlm.dto.QuestionsSourceDTO;
import tuanlm.dto.SubjectsDTO;
import tuanlm.utils.QuestionObjectCreation;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "UpdateQuesAndAnsServlet", urlPatterns = {"/UpdateQuesAndAnsServlet"})
public class UpdateQuesAndAnsServlet extends HttpServlet {

    private final String UPDATE_PAGE = "updatePage.jsp";
    private final String NOT_FOUND = "notfound.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = UPDATE_PAGE;

        String questionContent = request.getParameter("txtContent");
        String subjectId = request.getParameter("cbbSubject");
        String questionId = request.getParameter("txtQuestionId");
        Integer quesNumId = -1;
        if(questionId != null && questionId.length() > 0 && questionId.matches("[0-9]{1,}")) { 
            quesNumId = Integer.parseInt(questionId);
        }
        String correctAnswer = request.getParameter("rdAnswer");
        String answerA = request.getParameter("txtAnswerA");
        if(answerA != null) {
            answerA = answerA.trim();
        }
        String answerB = request.getParameter("txtAnswerB");
        if(answerB != null) {
                answerB = answerB.trim();
            }
        String answerC = request.getParameter("txtAnswerC");
        if(answerC != null) {
            answerC = answerC.trim();
        }
        String answerD = request.getParameter("txtAnswerD");
        if(answerD != null) {
            answerD = answerD.trim();
        }
        String idAnsA = request.getParameter("txtIdAnsA");
        String idAnsB = request.getParameter("txtIdAnsB");
        String idAnsC = request.getParameter("txtIdAnsC");
        String idAnsD = request.getParameter("txtIdAnsD");
        
        Integer idNumA = -1;
        Integer idNumB = -1;
        Integer idNumC = -1;
        Integer idNumD = -1;
        if(idAnsA != null && idAnsA.matches("[0-9]{1,}")) {
           idNumA = Integer.parseInt(idAnsA);
        }
        if(idAnsB != null && idAnsB.matches("[0-9]{1,}")) {
           idNumB = Integer.parseInt(idAnsB);
        } 
        if(idAnsC != null && idAnsC.matches("[0-9]{1,}")) {
           idNumC = Integer.parseInt(idAnsC);
        } 
        if(idAnsD != null && idAnsD.matches("[0-9]{1,}")) {
           idNumD = Integer.parseInt(idAnsD);
        } 
        try {
            if(questionId != null) {
                SubjectsDAO dao = new SubjectsDAO();
                List<SubjectsDTO> subjects = dao.getAllSubjects();
                request.setAttribute("SUBJECTS", subjects);

                request.setAttribute("QUESTION_ID", questionId);

                boolean checkError = false;
                QuestionObjectCreation error = new QuestionObjectCreation();
                if (questionContent == null || questionContent.trim().equals("")) {
                    error.setQuestionBlank("Question content can not be blank");
                    checkError = true;
                }
                if (correctAnswer == null) {
                    error.setCorrectAnswerBlank("Correct answer can not be blank");
                    checkError = true;
                }
                if (answerA == null || answerA.equals("")
                        || answerB == null || answerB.equals("")
                        || answerC == null || answerC.equals("")
                        || answerD == null || answerD.equals("")) {
                    checkError = true;
                    error.setAnswerBlank("Answer can not be blank");
                }

                List<AnswersSourceDTO> ansList = new ArrayList<>();
                AnswersSourceDTO ansA = new AnswersSourceDTO(idNumA, answerA, correctAnswer != null && correctAnswer.equals("A"));
                AnswersSourceDTO ansB = new AnswersSourceDTO(idNumB, answerB, correctAnswer != null && correctAnswer.equals("B"));
                AnswersSourceDTO ansC = new AnswersSourceDTO(idNumC, answerC, correctAnswer != null && correctAnswer.equals("C"));
                AnswersSourceDTO ansD = new AnswersSourceDTO(idNumD, answerD, correctAnswer != null && correctAnswer.equals("D"));
                ansList.add(ansA);
                ansList.add(ansB);
                ansList.add(ansC);
                ansList.add(ansD);

                if (checkError) {
                    request.setAttribute("ERROR", error);
                } else {
                    QuestionsSourceDTO quesDTO = new QuestionsSourceDTO();
                    quesDTO.setId(quesNumId);
                    quesDTO.setQuestionContent(questionContent);
                    quesDTO.setSubjectId(subjectId);
                    QuestionsSourceDAO questionsSourceDAO = new QuestionsSourceDAO();
                    boolean checkUpdateQues = questionsSourceDAO.updateQuestion(quesDTO);
                    if (checkUpdateQues) {
                        AnswersSourceDAO answersSourceDAO = new AnswersSourceDAO();
                        boolean checkUpdateAns = answersSourceDAO.updateAns(ansList);
                        if (checkUpdateAns) {
                            request.setAttribute("STATUS", "Update question successfuly");
                        } else {
                            request.setAttribute("STATUS", "Update fail");
                        }

                    }
                }
                request.setAttribute("ANSWERA", ansA);
                request.setAttribute("ANSWERB", ansB);
                request.setAttribute("ANSWERC", ansC);
                request.setAttribute("ANSWERD", ansD);
                request.setAttribute("QUESTION_CONTENT", questionContent);
                request.setAttribute("SELECTED_SUBJECT", subjectId);
            }
            else {
                url = NOT_FOUND;
            }
            
            
        } catch (NamingException ex) {
            log("UpdateQuesAndAnsServlet_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
            log("UpdateQuesAndAnsServlet_SQL: " + ex.getMessage());
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
