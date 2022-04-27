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
import tuanlm.dto.AnswersInQuizDTO;
import tuanlm.dto.AnswersSourceDTO;
import tuanlm.dto.QuestionsInQuizDTO;
import tuanlm.utils.DBConector;

/**
 *
 * @author MINH TUAN
 */
public class AnswersInquizDAO {

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

    public boolean insertAnswerOfQuiz(List ansInfor, Integer questionId) throws NamingException, SQLException {
        String ansCorrect = "";
        List<AnswersSourceDTO> anserSrc = (List<AnswersSourceDTO>) ansInfor.get(1);
        for (AnswersSourceDTO ansSrc : anserSrc) {
            if (ansSrc.isCorrectAnswer()) {
                ansCorrect = ansSrc.getAnswerContent();
            }
        }
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "insert into AnswersInQuiz(question_id, answer_choose, answer_correct) "
                        + " values(?,?,?)";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, questionId);
                if (ansInfor.size() >= 3) {
                    ps.setString(2, ((String) ansInfor.get(2)));
                } else {
                    ps.setString(2, null);
                }
                ps.setString(3, ansCorrect);
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public List getAnsByQues(List<QuestionsInQuizDTO> questionList) throws SQLException, NamingException {
        List mapQuesAndAnsList = new ArrayList();
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "select answer_choose, answer_correct from AnswersInQuiz where question_id = ?";
                for (QuestionsInQuizDTO questionsInQuizDTO : questionList) {
                    List quesAndAnsList = new ArrayList();
                    quesAndAnsList.add(questionsInQuizDTO);
                    ps = cnn.prepareStatement(sql);
                    ps.setInt(1, questionsInQuizDTO.getId());
                    rs = ps.executeQuery();
                    while(rs.next()) {
                        String choose = rs.getString("answer_choose");
                        String correct = rs.getString("answer_correct");
                        AnswersInQuizDTO dto = new AnswersInQuizDTO();
                        dto.setAnswerChoose(choose);
                        dto.setCorrectAns(correct);
                        quesAndAnsList.add(dto);
                    }
                    mapQuesAndAnsList.add(quesAndAnsList);
                }

            }
        } finally {
            closeConnection();
        }
        return mapQuesAndAnsList;
    }
}
