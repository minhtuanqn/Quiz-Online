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
public class QuestionsSourceDTO implements Serializable{
    private Integer id;
    private String questionContent;
    private Date createDate;
    private String subjectId;
    private boolean active;

    public QuestionsSourceDTO() {
    }

    public QuestionsSourceDTO(Integer id, String questionContent, Date createDate, String subjectId, boolean active) {
        this.id = id;
        this.questionContent = questionContent;
        this.createDate = createDate;
        this.subjectId = subjectId;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
