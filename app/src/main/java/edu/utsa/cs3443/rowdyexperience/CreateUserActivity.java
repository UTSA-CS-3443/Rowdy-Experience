package edu.utsa.cs3443.rowdyexperience;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.utsa.cs3443.rowdyexperience.model.User;

public class CreateUserActivity extends AppCompatActivity {

    EditText firstNameInput, lastNameInput, usernameInput, passwordInput, yearInput, majorInput;
    Button createProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user);

        firstNameInput = findViewById(R.id.first_name_input);
        lastNameInput = findViewById(R.id.last_name_input);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        yearInput = findViewById(R.id.class_year_input);
        majorInput = findViewById(R.id.major_input);
        createProfileBtn = findViewById(R.id.create_profile_button);

        createProfileBtn.setOnClickListener(v -> {
            try {
                String first = firstNameInput.getText().toString().trim();
                String last = lastNameInput.getText().toString().trim();
                String user = usernameInput.getText().toString().trim();
                String pass = passwordInput.getText().toString().trim();
                int year = Integer.parseInt(yearInput.getText().toString().trim());
                String major = majorInput.getText().toString().trim();

                File usersDir = new File(getFilesDir(), "users");
                if (!usersDir.exists()) usersDir.mkdir();

                File userFolder = new File(usersDir, user);
                if (userFolder.exists()) {
                    Toast.makeText(this, "Username already exists. Please choose another one.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!userFolder.mkdir()) {
                    Toast.makeText(this, "Failed to create user directory.", Toast.LENGTH_SHORT).show();
                    return;
                }

                User newUser = new User(first, last, user, year, major, pass);
                File userCSV = new File(userFolder, "user.csv");

                FileWriter writer = new FileWriter(userCSV);
                writer.append(newUser.getFirstName()).append(",")
                        .append(newUser.getLastName()).append(",")
                        .append(newUser.getUserName()).append(",")
                        .append(String.valueOf(newUser.getYear())).append(",")
                        .append(newUser.getMajor()).append(",")
                        .append(newUser.getPassword()).append("\n\n")
                        .append("food,3\n")
                        .append("nightlife,3\n")
                        .append("buildings,3\n")
                        .append("activities,3\n")
                        .append("sports,3\n");

                writer.flush();
                writer.close();

                createChecklistFile(userFolder, "food.csv");
                createChecklistFile(userFolder, "nightlife.csv");
                createChecklistFile(userFolder, "buildings.csv");
                createChecklistFile(userFolder, "activities.csv");
                createChecklistFile(userFolder, "sports.csv");

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("created_user", newUser.getUserName());
                startActivity(intent);
                finish();

            } catch (Exception e) {
                Toast.makeText(this, "Failed to create profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    private void createChecklistFile(File userFolder, String fileNameFromAssets) {
        try {
            InputStream inputStream = getAssets().open(fileNameFromAssets);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            File outFile = new File(userFolder, fileNameFromAssets);
            FileWriter writer = new FileWriter(outFile);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\n");
            }

            writer.flush();
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
