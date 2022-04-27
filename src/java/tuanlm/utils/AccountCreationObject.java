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
public class AccountCreationObject {
    private String emailBlank;
    private String emailNotValid;
    private String emailDuplicate;
    private String passwordNotDuplicate;
    private String passwordNotValid;
    private String nameBlank;

    public String getEmailDuplicate() {
        return emailDuplicate;
    }

    public void setEmailDuplicate(String emailDuplicate) {
        this.emailDuplicate = emailDuplicate;
    }

    public String getEmailBlank() {
        return emailBlank;
    }

    public void setEmailBlank(String emailBlank) {
        this.emailBlank = emailBlank;
    }

    public String getEmailNotValid() {
        return emailNotValid;
    }

    public void setEmailNotValid(String emailNotValid) {
        this.emailNotValid = emailNotValid;
    }

    public String getPasswordNotValid() {
        return passwordNotValid;
    }

    public void setPasswordNotValid(String passwordNotValid) {
        this.passwordNotValid = passwordNotValid;
    }

    public String getPasswordNotDuplicate() {
        return passwordNotDuplicate;
    }

    public void setPasswordNotDuplicate(String passwordNotDuplicate) {
        this.passwordNotDuplicate = passwordNotDuplicate;
    }

    public String getNameBlank() {
        return nameBlank;
    }

    public void setNameBlank(String nameBlank) {
        this.nameBlank = nameBlank;
    }
    
    
}
