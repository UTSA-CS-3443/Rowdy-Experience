package edu.utsa.cs3443.rowdyexperience;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3443.rowdyexperience.model.Checklist;
import edu.utsa.cs3443.rowdyexperience.model.Questions;
import edu.utsa.cs3443.rowdyexperience.model.User;

public class ChecklistActivity extends AppCompatActivity {

    private Checklist list;

    int[] checkBoxIds = {
            R.id.checkBox1, R.id.checkBox2, R.id.checkBox3, R.id.checkBox4,
            R.id.checkBox5, R.id.checkBox6, R.id.checkBox7, R.id.checkBox8,
            R.id.checkBox9, R.id.checkBox10
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        String username = getIntent().getStringExtra("username");
        String version = getIntent().getStringExtra("button");

        findChecklisttoRead(username, version);
        TextView title = findViewById(R.id.title);
        title.setText(version);

        ColorStateList blueTint = ColorStateList.valueOf(Color.parseColor("#002D72"));

        for (int id : checkBoxIds) {
            CheckBox cb = findViewById(id);
            cb.setButtonTintList(blueTint);
        }


        setCheckboxes();

        Button save_exitButton = findViewById(R.id.save_exitButton);

        save_exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChecklistFromCheckboxes();
                findChecklisttoWrite( username, version);
                list.calculatePercentage();
                updateUserCategoryValue(username,version, list.percentageToTier());
                launchCreateDashboardActivity(username);
            }
        });




    }

    public void findChecklisttoRead(String username, String name) {
        File userFile = new File(getFilesDir(), "users/" + username + "/" + name + ".csv");

        if (userFile.exists()) {
            try {
                list = Checklist.readChecklist(this, userFile.getAbsolutePath());
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace(); // shows in Logcat
            }
        } else {
            Toast.makeText(this, "Checklist file not found: " + userFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        }
    }

    public void findChecklisttoWrite(String username, String name) {
        File userFile = new File(getFilesDir(), "users/" + username + "/" + name + ".csv");

        if (userFile.exists()) {
            try {
                writeChecklistToFile(userFile);
                Toast.makeText(this, "Checklist saved successfully!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to save checklist: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Checklist file not found: " + userFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        }
    }

    private void writeChecklistToFile(File file) throws Exception {
        FileWriter writer = new FileWriter(file, false); // false = overwrite

        for (Questions q : list.getQuestions()) {
            int checked = q.getCheck() ? 1 : 0;
            writer.write(checked + ", " + q.getQuestion() + "\n");
        }

        writer.close();
    }


    public void setCheckboxes() {
        for (int i = 0; i < list.getQuestions().size() && i < checkBoxIds.length; i++) {
            Questions question = list.getQuestions().get(i);
            CheckBox checkBox = findViewById(checkBoxIds[i]);

            if (checkBox != null) {
                checkBox.setText(question.getQuestion());
                checkBox.setChecked(question.getCheck());
            }
        }
    }

    public void updateChecklistFromCheckboxes() {
        for (int i = 0; i < list.getQuestions().size() && i < checkBoxIds.length; i++) {
            CheckBox checkBox = findViewById(checkBoxIds[i]);
            if (checkBox != null) {
                list.getQuestions().get(i).setCheck(checkBox.isChecked());
            }
        }
        Toast.makeText(this, "Checklist updated from checkboxes!", Toast.LENGTH_SHORT).show();
    }


    public void updateUserCategoryValue(String username, String category, int newValue) {
        File userFile = new File(getFilesDir(), "users/" + username + "/user.csv");

        if (!userFile.exists()) {
            Toast.makeText(this, "User file not found for " + username, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;

            BufferedReader reader = new BufferedReader(new FileReader(userFile));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(category + ",")) {
                    updatedLines.add(category + "," + newValue);
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
            reader.close();

            if (!found) {
                Toast.makeText(this, "Category '" + category + "' not found in user.csv", Toast.LENGTH_SHORT).show();
                return;
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, false));
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();

            Toast.makeText(this, "Updated '" + category + "' to " + newValue, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error updating user file: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }



    public void launchCreateDashboardActivity(String name) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("username",name);
        startActivity(intent);
    }

}