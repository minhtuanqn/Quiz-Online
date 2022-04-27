/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author MINH TUAN
 */
public class QuizesDTO implements Serializable{
    private Integer id;
    private String user;
    private String subjectId;
    private Integer numberOfQuestion;
    private Integer numberCorrectAns;
    private Date dateTakeQuiz;
    private double score;
    private String dateText;

    public QuizesDTO() {
    }

    public QuizesDTO(String user, String subjectId, Integer numberOfQuestion, Integer numberCorrectAns) {
        this.user = user;
        this.subjectId = subjectId;
        this.numberOfQuestion = numberOfQuestion;
        this.numberCorrectAns = numberCorrectAns;
    }


    public QuizesDTO(Integer id, String user, String subjectId, Integer numberOfQuestion, Integer numberCorrectAns, Date dateTakeQuiz) {
        this.id = id;
        this.user = user;
        this.subjectId = subjectId;
        this.numberOfQuestion = numberOfQuestion;
        this.numberCorrectAns = numberCorrectAns;
        this.dateTakeQuiz = dateTakeQuiz;
    }
    


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(Integer numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public Integer getNumberCorrectAns() {
        return numberCorrectAns;
    }

    public void setNumberCorrectAns(Integer numberCorrectAns) {
        this.numberCorrectAns = numberCorrectAns;
    }

    
    
    public Date getDateTakeQuiz() {
        return dateTakeQuiz;
    }

    public String getDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        dateText = sdf.format(dateTakeQuiz);
        return dateText;
    }
    

    public void setDateTakeQuiz(Date dateTakeQuiz) {
        this.dateTakeQuiz = dateTakeQuiz;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    
    
    
}
