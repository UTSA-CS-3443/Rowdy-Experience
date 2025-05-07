package edu.utsa.cs3443.rowdyexperience.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Checklist {
    private int percentage;
    private ArrayList<Questions> questions;


    public Checklist() {
        questions = new ArrayList<>();
        this.percentage = 0;
    }

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


    public void addQuestion(Questions q){
        questions.add(q);
    }
    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public ArrayList<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Questions> questions) {
        this.questions = questions;
    }
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

    public int percentageToTier(){
        int percent = this.percentage;
        if(percent == 100){
            return 4;
        }
        else if (percent <=50){
            return 3;
        }
        else if (percent <=70){
            return 2;
        }
        else{
            return 1;
        }
    }

}