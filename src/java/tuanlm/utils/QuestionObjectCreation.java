/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.utils;

/**
 *
 * @author MINH TUAN
 */
public class QuestionObjectCreation {
    private String questionBlank;
    private String correctAnswerBlank;
    private String answerBlank;

    public QuestionObjectCreation() {
    }

    public QuestionObjectCreation(String questionBlank, String correctAnswerBlank, String answerBlank) {
        this.questionBlank = questionBlank;
        this.correctAnswerBlank = correctAnswerBlank;
        this.answerBlank = answerBlank;
    }

    

    public String getQuestionBlank() {
        return questionBlank;
    }

    public void setQuestionBlank(String questionBlank) {
        this.questionBlank = questionBlank;
    }

    public String getCorrectAnswerBlank() {
        return correctAnswerBlank;
    }

    public void setCorrectAnswerBlank(String correctAnswerBlank) {
        this.correctAnswerBlank = correctAnswerBlank;
    }

    public String getAnswerBlank() {
        return answerBlank;
    }

    public void setAnswerBlank(String answerBlank) {
        this.answerBlank = answerBlank;
    }
    
    
    
}
