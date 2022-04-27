/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MINH TUAN
 */
public class AnswersSourceDTO implements Serializable{
    private Integer id;
    private String answerContent;
    private Date createDate;
    private Integer questionId;
    private boolean correctAnswer;

    public AnswersSourceDTO() {
    }

    public AnswersSourceDTO(Integer id, String answerContent, boolean correctAnswer) {
        this.id = id;
        this.answerContent = answerContent;
        this.correctAnswer = correctAnswer;
    }

    public AnswersSourceDTO(String answerContent, boolean correctAnswer) {
        this.answerContent = answerContent;
        this.correctAnswer = correctAnswer;
    }

    

    public AnswersSourceDTO(Integer id, String answerContent, Date createDate, Integer questionId, boolean correctAnswer) {
        this.id = id;
        this.answerContent = answerContent;
        this.createDate = createDate;
        this.questionId = questionId;
        this.correctAnswer = correctAnswer;
    }

    public AnswersSourceDTO(String answerContent, Date createDate, Integer questionId, boolean correctAnswer) {
        this.answerContent = answerContent;
        this.createDate = createDate;
        this.questionId = questionId;
        this.correctAnswer = correctAnswer;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

   
    
}
