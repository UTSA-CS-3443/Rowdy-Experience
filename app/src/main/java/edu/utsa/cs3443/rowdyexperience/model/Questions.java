package edu.utsa.cs3443.rowdyexperience.model;

public class Questions {
    private String question;
    private boolean check;

    public Questions(String question, boolean check) {
        this.question = question;
        this.check = check;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getCheck() {
        return this.check;
    }
}
