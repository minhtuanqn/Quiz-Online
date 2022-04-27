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
public class QuestionsSourceDAO implements Serializable {

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

    public List<QuestionsSourceDTO> searchQuestion(String searchedName, String subjectId, boolean status) throws NamingException, SQLException {
        List<QuestionsSourceDTO> searchedList = null;
        try {
            cnn = DBConector.makeConnection();
            String sql = "select id, question_content, create_date, subject_id, status "
                    + " from QuestionsSource ";
            boolean checkSearch = false;
            if (searchedName != null && searchedName.length() > 0) {
                sql += " where question_content like " + "'%" + searchedName + "%'";
                checkSearch = true;
            }
            if (subjectId != null && subjectId.length() > 0) {
                if (checkSearch == true) {
                    sql += " or subject_id = '" + subjectId + "'";
                } else {
                    sql += " where subject_id = '" + subjectId + "'";
                    checkSearch = true;
                }
            }
            if (checkSearch == true) {
                sql += " or status =  " + "'" + status + "'";
            } else {
                sql += " where status = " + "'" + status + "'";
                checkSearch = true;
            }
            if (checkSearch) {
                sql += " order by id";
                if (cnn != null) {
                    ps = cnn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Integer id = rs.getInt("id");
                        String subjectSearch = rs.getString("subject_id");
                        String questionContent = rs.getString("question_content");
                        Timestamp createDate = rs.getTimestamp("create_date");
                        boolean statusSearch = rs.getBoolean("status");
                        QuestionsSourceDTO dto = new QuestionsSourceDTO(id, questionContent, createDate, subjectSearch, statusSearch);
                        if (searchedList == null) {
                            searchedList = new ArrayList<>();
                        }
                        searchedList.add(dto);
                    }
                }
            }

        } finally {
            closeConnection();
        }
        return searchedList;
    }

    public boolean createNewQuestion(QuestionsSourceDTO dto) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "insert into QuestionsSource(question_content, create_date, subject_id, status) "
                        + " values(?,?,?,?)";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dto.getQuestionContent());
                ps.setTimestamp(2, new Timestamp(dto.getCreateDate().getTime()));
                ps.setString(3, dto.getSubjectId());
                ps.setBoolean(4, true);
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

    public Integer getCurQuestionId() throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "select max(id) as maxId from QuestionsSource";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Integer questionId = rs.getInt("maxId");
                    return questionId;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean updateAnswer(List<AnswersSourceDTO> ansList) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "update AnswersSource set answer_content = ? and is_correct_answer = ?";
                for (AnswersSourceDTO answersSourceDTO : ansList) {
                    ps = cnn.prepareStatement(sql);
                    ps.setString(1, answersSourceDTO.getAnswerContent());
                    ps.setBoolean(2, answersSourceDTO.isCorrectAnswer());
                    ps.executeUpdate();
                }
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean updateQuestion(QuestionsSourceDTO quesDTO) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "update QuestionsSource set question_content = ?, subject_id = ? "
                        + " where id = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, quesDTO.getQuestionContent());
                ps.setString(2, quesDTO.getSubjectId());
                ps.setInt(3, quesDTO.getId());
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

    public boolean deleteQuestion(Integer quesId) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "update QuestionsSource set status = ? where id = ?";
                ps = cnn.prepareStatement(sql);
                ps.setBoolean(1, false);
                ps.setInt(2, quesId);
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

    public List<QuestionsSourceDTO> createListQuestionForQuiz(String subjectId, Integer numberOfQuestion) throws SQLException, NamingException {
        List<QuestionsSourceDTO> searchedList = null;
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "SELECT TOP " + numberOfQuestion + " question_content, id FROM QuestionsSource "
                        + " where subject_id = ? and status = ? ORDER BY NEWID() ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, subjectId);
                ps.setBoolean(2, true);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (searchedList == null) {
                        searchedList = new ArrayList<>();
                    }
                    QuestionsSourceDTO questionsSourceDTO = new QuestionsSourceDTO();
                    questionsSourceDTO.setId(rs.getInt("id"));
                    questionsSourceDTO.setQuestionContent(rs.getString("question_content"));
                    searchedList.add(questionsSourceDTO);
                }
            }
        } 
        finally {
            closeConnection();
        }
        return searchedList;
    }
}
