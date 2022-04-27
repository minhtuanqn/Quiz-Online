/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dto;

import java.io.Serializable;

/**
 *
 * @author MINH TUAN
 */
public class AnswersInQuizDTO implements Serializable{
    private Integer id;
    private Integer questionId;
    private String answerChoose;
    private String correctAns;

    public AnswersInQuizDTO() {
    }

    public AnswersInQuizDTO(Integer id, Integer questionId, String answerChoose, String correctAns) {
        this.id = id;
        this.questionId = questionId;
        this.answerChoose = answerChoose;
        this.correctAns = correctAns;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswerChoose() {
        return answerChoose;
    }

    public void setAnswerChoose(String answerChoose) {
        this.answerChoose = answerChoose;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }
    
    
}
