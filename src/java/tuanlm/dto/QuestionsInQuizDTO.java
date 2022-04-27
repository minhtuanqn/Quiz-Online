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
public class QuestionsInQuizDTO implements Serializable{
    private Integer id;
    private String questonContent;
    private Integer quizId;

    public QuestionsInQuizDTO() {
    }

    public QuestionsInQuizDTO(Integer id, String questonContent, Integer quizId) {
        this.id = id;
        this.questonContent = questonContent;
        this.quizId = quizId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestonContent() {
        return questonContent;
    }

    public void setQuestonContent(String questonContent) {
        this.questonContent = questonContent;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }
    
    
}
