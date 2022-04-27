/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "CreateQuestionServlet", urlPatterns = {"/CreateQuestionServlet"})
public class CreateQuestionServlet extends HttpServlet {

    private final String QUESTION_INFOR_PAGE = "questionInfor.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = QUESTION_INFOR_PAGE + "?btnAction=Create new Question";
        String questionContent = request.getParameter("txtContent").trim();
        String subjectId = request.getParameter("cbbSubject");
        String correctAnswer = request.getParameter("rdAnswer");
        String answerA = request.getParameter("txtAnswerA").trim();
        String answerB = request.getParameter("txtAnswerB").trim();
        String answerC = request.getParameter("txtAnswerC").trim();
        String answerD = request.getParameter("txtAnswerD").trim();
        try {

            SubjectsDAO dao = new SubjectsDAO();
            List<SubjectsDTO> subjects = dao.getAllSubjects();
            request.setAttribute("SUBJECTS", subjects);

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
            if (checkError) {
                request.setAttribute("ERROR", error);
                AnswersSourceDTO ansARemain = new AnswersSourceDTO(answerA, correctAnswer != null && correctAnswer.equals("A"));
                AnswersSourceDTO ansBRemain = new AnswersSourceDTO(answerB, correctAnswer != null && correctAnswer.equals("B"));
                AnswersSourceDTO ansCRemain = new AnswersSourceDTO(answerC, correctAnswer != null && correctAnswer.equals("C"));
                AnswersSourceDTO ansDRemain = new AnswersSourceDTO(answerD, correctAnswer != null && correctAnswer.equals("D"));
                request.setAttribute("ANSWERA", ansARemain);
                request.setAttribute("ANSWERB", ansBRemain);
                request.setAttribute("ANSWERC", ansCRemain);
                request.setAttribute("ANSWERD", ansDRemain);
                request.setAttribute("QUESTION_CONTENT", questionContent);
                request.setAttribute("SUBJECT", subjectId);
            } else {
                QuestionsSourceDTO questionDTO = new QuestionsSourceDTO();
                questionDTO.setQuestionContent(questionContent);
                questionDTO.setSubjectId(subjectId);
                questionDTO.setCreateDate(new Date());
                QuestionsSourceDAO questionDAO = new QuestionsSourceDAO();
                boolean checkAddQuestion = questionDAO.createNewQuestion(questionDTO);
                if (checkAddQuestion) {
                    Integer curQuestion = questionDAO.getCurQuestionId();
                    if (curQuestion != null) {
                        AnswersSourceDAO answerDAO = new AnswersSourceDAO();
                        List<AnswersSourceDTO> listAnswer = new ArrayList();
                        AnswersSourceDTO ansA = new AnswersSourceDTO(answerA, new Date(), curQuestion, correctAnswer != null && correctAnswer.equals("A"));
                        AnswersSourceDTO ansB = new AnswersSourceDTO(answerB, new Date(), curQuestion, correctAnswer != null && correctAnswer.equals("B"));
                        AnswersSourceDTO ansC = new AnswersSourceDTO(answerC, new Date(), curQuestion, correctAnswer != null && correctAnswer.equals("C"));
                        AnswersSourceDTO ansD = new AnswersSourceDTO(answerD, new Date(), curQuestion, correctAnswer != null && correctAnswer.equals("D"));
                        listAnswer.add(ansA);
                        listAnswer.add(ansB);
                        listAnswer.add(ansC);
                        listAnswer.add(ansD);
                        boolean checkAddAns = answerDAO.addAnswerToSource(listAnswer);
                        if (checkAddAns) {
                            request.setAttribute("STATUS", "Create question successfully");
                        } else {
                            request.setAttribute("STATUS", "Create question fail");
                        }

                    }

                }
            }

        } catch (NamingException ex) {
            log("CreateQuestionServlet_Naming: " + ex.getMessage());
        } catch (SQLException ex) {
            log("CreateQuestionServlet_SQL: " + ex.getMessage());
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
