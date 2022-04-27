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
import tuanlm.dto.SubjectsDTO;
import tuanlm.utils.DBConector;

/**
 *
 * @author MINH TUAN
 */
public class SubjectsDAO {

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

    public List<SubjectsDTO> getAllSubjects() throws NamingException, SQLException {
        List<SubjectsDTO> subjectsList = null;
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "select id, name from Subjects";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (subjectsList == null) {
                        subjectsList = new ArrayList<>();
                    }
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    SubjectsDTO dto = new SubjectsDTO(id, name);
                    subjectsList.add(dto);
                }

            }
        } 
        finally {
            closeConnection();
        }
        return subjectsList;
    }
    
    public Integer getNumberOfQuestionInQuiz(String subjectId) throws SQLException, NamingException {
        try {
            cnn = DBConector.makeConnection();
            if(cnn != null) {
                String sql = "select number_of_question_in_quiz from Subjects where id = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, subjectId);
                rs = ps.executeQuery();
                if(rs.next()) {
                    Integer numberOfQuestion = rs.getInt("number_of_question_in_quiz");
                    return numberOfQuestion;
                }
            }
        } 
        finally {
            closeConnection();
        }
        return null;
    }
    
    public Integer getTimeOfQuiz(String subjectId) throws SQLException, NamingException {
        try {
            cnn = DBConector.makeConnection();
            if(cnn != null) {
                String sql = "select time_of_each_quiz_minute from Subjects where id = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, subjectId);
                rs = ps.executeQuery();
                if(rs.next()) {
                    Integer numberOfQuestion = rs.getInt("time_of_each_quiz_minute");
                    return numberOfQuestion;
                }
            }
        } 
        finally {
            closeConnection();
        }
        return null;
    }
}
