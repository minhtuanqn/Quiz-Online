/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tuanlm.dto.QuestionsInQuizDTO;
import tuanlm.dto.QuestionsSourceDTO;
import tuanlm.utils.DBConector;

public class QuestionsInQuizDAO {

    private Connection cnn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (cnn != null) {
            cnn.close();
        }
    }

    private Integer getCurQuestionInQuizId() throws SQLException, NamingException {

        if (cnn != null) {
            String sql = "select Max(id) as  questionId from QuestionsInQuiz";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                Integer curId = rs.getInt("questionId");
                return curId;
            }
        }

        return null;
    }

    public boolean insertQuizHistoty(List<List> quizList, Integer curQuizId) throws NamingException, SQLException {
        AnswersInquizDAO answersInquizDAO = new AnswersInquizDAO();
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "insert into QuestionsInQuiz(question_content, quiz_id) "
                        + " values(?,?)";
                for (List list : quizList) {
                    ps = cnn.prepareStatement(sql);
                    ps.setString(1, ((QuestionsSourceDTO) list.get(0)).getQuestionContent());
                    ps.setInt(2, curQuizId);
                    int row = ps.executeUpdate();
                    if (row > 0) {
                        answersInquizDAO.insertAnswerOfQuiz(list, getCurQuestionInQuizId());
                    }
                }
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public List<QuestionsInQuizDTO> getQuestionInQuizByQuizId(Integer quizId) throws NamingException, SQLException {
        List<QuestionsInQuizDTO> questionList = new ArrayList<>();
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "select id, question_content from QuestionsInQuiz where quiz_id = ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, quizId);
                rs = ps.executeQuery();
                while(rs.next()){
                    Integer id = rs.getInt("id");
                    String content = rs.getString("question_content");
                    QuestionsInQuizDTO dto = new QuestionsInQuizDTO(id, content, quizId);
                    questionList.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return questionList;
    }
}
