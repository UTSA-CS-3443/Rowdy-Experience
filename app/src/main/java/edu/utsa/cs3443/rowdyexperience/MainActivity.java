package edu.utsa.cs3443.rowdyexperience;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

import edu.utsa.cs3443.rowdyexperience.model.User;

public class MainActivity extends AppCompatActivity {


    private User mainUser;
    @SuppressWarnings("FieldCanBeLocal")
    private EditText edit_text_username;
    @SuppressWarnings("FieldCanBeLocal")
    private EditText edit_text_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        edit_text_username = findViewById(R.id.username);
        edit_text_password = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.login_button);
        Button createuserButton = findViewById(R.id.create_account);

        createuserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateUserActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edit_text_username.getText().toString().trim();
                String password = edit_text_password.getText().toString().trim();

                // Try to find the user
                findUser(username);

                if (mainUser != null) {
                    if (mainUser.getPassword().equals(password)) {
                        Toast.makeText(MainActivity.this, "Welcome, the pass matched " + mainUser.getFirstName() + "!", Toast.LENGTH_SHORT).show();
                        launchCreateDashboardActivity();

                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect password. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Username not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    public void launchCreateUserActivity() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }
    public void launchCreateDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("username",mainUser.getUserName());
        startActivity(intent);
    }

    public void findUser(String username) {
        // Build the correct path to the user's CSV file
        File userFile = new File(getFilesDir(), "users/" + username + "/user.csv");

        if (userFile.exists()) {
            mainUser = User.readUser(this, userFile.getAbsolutePath());
        } else {
            Toast.makeText(this, "User file not found for " + username, Toast.LENGTH_SHORT).show();
        }
    }


}