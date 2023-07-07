package Classes;

import java.io.Serializable;

public class Role implements Serializable {
    public static Role loggedInRole ;
    public static boolean loggedInRoleExistance = false ;
    private String securityQuestionAnswer ;
    public String securityQuestion ;

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }
}