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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import tuanlm.dto.QuizesDTO;
import tuanlm.utils.DBConector;

/**
 *
 * @author MINH TUAN
 */
public class QuizesDAO {
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
    
    public boolean insertQuiz(QuizesDTO dto) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if(cnn != null) {
                String sql = "insert into Quizes (user_take_quiz, subject_id, number_of_question, date_take_quiz, number_correct_answer) "
                        + " values(?,?,?,?,?)";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dto.getUser());
                ps.setString(2, dto.getSubjectId());
                ps.setInt(3, dto.getNumberOfQuestion());
                ps.setTimestamp(4, new Timestamp(new Date().getTime()));
                ps.setInt(5, dto.getNumberCorrectAns());
                int row  = ps.executeUpdate();
                if(row > 0) {
                    return true;
                }
            }
        } 
        finally {
            closeConnection();
        }
        return false;
    }
    
    public Integer getCurQuizId() throws SQLException, NamingException {
        try {
            cnn = DBConector.makeConnection();
            if(cnn != null) {
                String sql = "select Max(id) as  id from Quizes";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                if(rs.next()) {
                   Integer curId = rs.getInt("id");
                   return curId;
                }
            }
        } 
        finally {
            closeConnection();
        }
        return null;
    }
    
    public List<QuizesDTO> getAllQuizByUserAndSubject(String email, String subject) throws SQLException, NamingException {
        List<QuizesDTO> quizList = new ArrayList<>();
        try {
            cnn = DBConector.makeConnection();
            if(cnn != null) {
                String sql = "select id ,subject_id, number_of_question, number_correct_answer, date_take_quiz "
                        + " from Quizes where user_take_quiz = ? and subject_id like ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, "%" + subject + "%");
                rs = ps.executeQuery();
                while(rs.next()) {
                    Integer numberOfQues = rs.getInt("number_of_question");
                    Integer numberOfCorrect = rs.getInt("number_correct_answer");
                    Timestamp date = rs.getTimestamp("date_take_quiz");
                    Integer id = rs.getInt("id");
                    QuizesDTO dto = new QuizesDTO(id, email, subject, numberOfQues, numberOfCorrect, date);
                    dto.setScore(numberOfCorrect * 1.0 / numberOfQues * 10);
                    quizList.add(dto);
                }
            }
        } 
        finally {
            closeConnection();
        }
        return quizList;
    }
    
    public QuizesDTO getQuizById(Integer id) throws SQLException, NamingException {
        try {
            cnn = DBConector.makeConnection();
            if(cnn != null) {
                String sql = "select id, subject_id, user_take_quiz, number_of_question, number_correct_answer "
                        + " from Quizes where id = ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if(rs.next()) {
                   Integer quizId = rs.getInt("id");
                   String user = rs.getString("user_take_quiz");
                   Integer numOfQues = rs.getInt("number_of_question");
                   Integer numOfCorrect = rs.getInt("number_correct_answer");
                   String subjectId = rs.getString("subject_id");
                   QuizesDTO dto = new QuizesDTO();
                   dto.setId(quizId);
                   dto.setUser(user);
                   dto.setNumberOfQuestion(numOfQues);
                   dto.setNumberCorrectAns(numOfCorrect);
                   dto.setSubjectId(subjectId);
                   dto.setScore(numOfCorrect * 1.0 / numOfQues * 10);
                   return dto;
                }
            }
        } 
        finally {
            closeConnection();
        }
        return null;
    }
    
}
