
package edu.utsa.cs3443.rowdyexperience.model;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a checklist used in the Rowdy Experience application.
 * This class manages a list of questions and calculates the completion percentage.
 */

public class Checklist {
    private int percentage;
    private ArrayList<Questions> questions;

    /**
     * Constructs an empty checklist with a default completion percentage of 0.
     */
    public Checklist() {
        questions = new ArrayList<>();
        this.percentage = 0;
    }

    /**
     * Reads a checklist from a file at the specified filepath.
     * The file should contain lines in the format:
     *
     * <pre>
     * 0,Question text
     * 1,Another question text
     * </pre>
     *
     * Where the first element indicates the checked status (0 for unchecked, 1 for checked),
     * and the second element is the question text.
     *
     * @param context the application context (required for file access on Android)
     * @param filepath the path to the file containing the checklist data
     * @return a Checklist object populated with questions from the file
     * @throws RuntimeException if an error occurs while reading the file
     */
    public static Checklist readChecklist(Context context, String filepath) {
        Checklist temp = new Checklist();

        try {
            InputStream file = new FileInputStream(new File(filepath));
            Scanner scan = new Scanner(file);
            String[] line;

            while (scan.hasNextLine()) {
                String rawline = scan.nextLine();
                if (rawline.trim().isEmpty()) continue;

                try {
                    line = rawline.split(",");
                    if (line.length < 2) continue; // Skip malformed or short lines

                    int flag = Integer.parseInt(line[0].trim()); // Trim to handle extra spaces
                    String questionText = line[1].trim();

                    Questions q = new Questions(questionText, flag == 1);
                    temp.addQuestion(q);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // Log and skip the malformed line
                    System.err.println("Skipping malformed line: " + rawline);
                }
            }

            scan.close(); // Always close the scanner

        } catch (IOException e) {
            throw new RuntimeException("Error reading user file at: " + filepath, e);
        }

        return temp;
    }

    /**
     * Adds a question to the checklist.
     *
     * @param q the question to add
     */
    public void addQuestion(Questions q) {
        questions.add(q);
    }

    /**
     * Returns the current completion percentage of the checklist.
     *
     * @return the completion percentage as an integer
     */
    public int getPercentage() {
        return percentage;
    }

    /**
     * Sets the completion percentage of the checklist.
     *
     * @param percentage the new completion percentage
     */
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    /**
     * Returns the list of questions in the checklist.
     *
     * @return an ArrayList of Questions
     */
    public ArrayList<Questions> getQuestions() {
        return questions;
    }

    /**
     * Sets the list of questions in the checklist.
     *
     * @param questions the list of questions to set
     */
    public void setQuestions(ArrayList<Questions> questions) {
        this.questions = questions;
    }

    /**
     * Calculates the completion percentage based on the number of checked questions.
     * If no questions are present, the percentage is set to 0.
     */
    public void calculatePercentage() {
        if (questions == null || questions.isEmpty()) {
            this.percentage = 0;
            return;
        }

        int checkedCount = 0;

        for (Questions q : questions) {
            if (q.getCheck()) {
                checkedCount++;
            }
        }

        this.percentage = (int) ((checkedCount / (double) questions.size()) * 100);
    }

    /**
     * Converts the current checklist completion percentage to a badge tier.
     *
     * @return an integer representing the badge tier:
     *         - 4 for 100% completion
     *         - 3 for up to 50% completion
     *         - 2 for up to 70% completion
     *         - 1 for any other completion level
     */
    public int percentageToTier() {
        int percent = this.percentage;
        if (percent == 100) {
            return 4;
        } else if (percent <= 50) {
            return 3;
        } else if (percent <= 70) {
            return 2;
        } else {
            return 1;
        }
    }
}
