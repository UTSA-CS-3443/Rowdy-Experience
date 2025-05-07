
package edu.utsa.cs3443.rowdyexperience.model;

/**
 * Represents a question in the Rowdy Experience application.
 * Each question has a text description and a boolean check status indicating whether it has been completed.
 */
public class Questions {
    private String question;
    private boolean check;

    /**
     * Constructs a new question with the specified text and check status.
     *
     * @param question the text of the question
     * @param check the initial check status of the question (true if checked, false otherwise)
     */
    public Questions(String question, boolean check) {
        this.question = question;
        this.check = check;
    }

    /**
     * Sets the text of the question.
     *
     * @param question the new text for the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Returns the text of the question.
     *
     * @return the question text
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Sets the check status of the question.
     *
     * @param check the new check status (true if checked, false otherwise)
     */
    public void setCheck(boolean check) {
        this.check = check;
    }

    /**
     * Returns the check status of the question.
     *
     * @return true if the question is checked, false otherwise
     */
    public boolean getCheck() {
        return this.check;
    }
}
