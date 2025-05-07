package edu.utsa.cs3443.rowdyexperience.model;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private int year;
    private String major;
    private String password;
    private ArrayList<Badges> badges;


    public User(String firstName, String lastName, String userName, int year, String major, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.year = year;
        this.major = major;
        this.password = password;
        this.badges = new ArrayList<>();
    }


    public static User readUser(Context context, String filepath) {
        User user;

        try {
            InputStream file = new FileInputStream(new File(filepath));
            Scanner scan = new Scanner(file);

            String[] line = scan.nextLine().split(",");
            user = new User(
                    line[0],
                    line[1],
                    line[2],
                    Integer.parseInt(line[3].trim()),
                    line[4],
                    line[5]
            );

            while (scan.hasNextLine()) {
                String rawline = scan.nextLine();
                if (rawline.trim().isEmpty()) continue;

                line = rawline.split(",");
                if (line.length < 2) continue;

                Badges b = new Badges(line[0], Integer.parseInt(line[1].trim()));
                user.addBadge(b);
            }

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Error reading user file at: " + filepath, e);
        }

        return user;
    }









    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return this.year;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return this.major;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
    public void addBadge(Badges b){
        badges.add(b);

    }
    public ArrayList<Badges> getBadges(){
        return badges;
    }
    public Badges getBadgeFromName(String name) {
        for (Badges b : badges) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return null;
    }


}

