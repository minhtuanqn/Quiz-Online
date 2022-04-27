/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tuanlm.dto.AnswersSourceDTO;
import tuanlm.dto.QuestionsSourceDTO;
import tuanlm.utils.DBConector;

/**
 *
 * @author MINH TUAN
 */
public class AnswersSourceDAO implements Serializable {

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

    public List<List> getAnswersByQuestionList(List<QuestionsSourceDTO> questionList) throws SQLException, NamingException {
        List<List> mapQuesAndAnsList = new ArrayList<>();
        try {
            String sql = "select id, answer_content, create_date, question_id, is_correct_answer from AnswersSource where question_id = ?";
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                if (questionList != null && questionList.size() > 0) {
                    for (QuestionsSourceDTO questionsSourceDTO : questionList) {
                        List<AnswersSourceDTO> answersList = null;
                        ps = cnn.prepareStatement(sql);
                        ps.setInt(1, questionsSourceDTO.getId());
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            Integer id = rs.getInt("id");
                            String answerContent = rs.getString("answer_content");
                            Timestamp createDate = rs.getTimestamp("create_date");
                            Integer questionId = questionsSourceDTO.getId();
                            boolean isCorrect = rs.getBoolean("is_correct_answer");
                            AnswersSourceDTO dto = new AnswersSourceDTO(id, answerContent, createDate, questionId, isCorrect);
                            if (answersList == null) {
                                answersList = new ArrayList<>();
                            }
                            answersList.add(dto);
                        }
                        List questAndAnsList = new ArrayList();
                        questAndAnsList.add(questionsSourceDTO);
                        questAndAnsList.add(answersList);
                        mapQuesAndAnsList.add(questAndAnsList);
                    }
                }

            }
        } finally {
            closeConnection();
        }
        return mapQuesAndAnsList;
    }

    public boolean addAnswerToSource(List<AnswersSourceDTO> answerList) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "insert into AnswersSource(answer_content, create_date, question_id,is_correct_answer) "
                        + " values(?,?,?,?)";
                for (AnswersSourceDTO answersSourceDTO : answerList) {
                    ps = cnn.prepareStatement(sql);
                    ps.setString(1, answersSourceDTO.getAnswerContent());
                    ps.setTimestamp(2, new Timestamp(answersSourceDTO.getCreateDate().getTime()));
                    ps.setInt(3, answersSourceDTO.getQuestionId());
                    ps.setBoolean(4, answersSourceDTO.isCorrectAnswer());
                    ps.executeUpdate();
                }
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public List<AnswersSourceDTO> getAllAnswerByQuestionId(int questionId) throws SQLException, NamingException {
        List<AnswersSourceDTO> ansList = new ArrayList<>();
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "select id ,answer_content, question_id, is_correct_answer from AnswersSource where question_id = ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, questionId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String ansContent = rs.getString("answer_content");
                    boolean correctAns = rs.getBoolean("is_correct_answer");
                    AnswersSourceDTO dto = new AnswersSourceDTO();
                    dto.setId(id);
                    dto.setAnswerContent(ansContent);
                    dto.setQuestionId(questionId);
                    dto.setCorrectAnswer(correctAns);
                    ansList.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return ansList;
    }

    public boolean updateAns(List<AnswersSourceDTO> ansList) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "update AnswersSource set answer_content = ?, is_correct_answer = ? "
                        + " where id = ?";
                for (AnswersSourceDTO answersSourceDTO : ansList) {
                    ps = cnn.prepareStatement(sql);
                    ps.setString(1, answersSourceDTO.getAnswerContent());
                    ps.setBoolean(2, answersSourceDTO.isCorrectAnswer());
                    ps.setInt(3, answersSourceDTO.getId());
                    ps.executeUpdate();
                }
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
