package com.pradipsahoo7722gmail.cms.SelectUserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pradipsahoo7722gmail.cms.LoginUser.LoginScreen;
import com.pradipsahoo7722gmail.cms.R;

public class SelectLoginProcess extends AppCompatActivity {

    ImageView loginLogo;
    TextView selectLoginText;
    Button adminLoginBtn, studentLoginBtn, facultyLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_process);

        loginLogo = findViewById(R.id.loginLogo);
        selectLoginText = findViewById(R.id.selectLoginText);
        adminLoginBtn = findViewById(R.id.admin_btn);
        studentLoginBtn = findViewById(R.id.student_Btn);
        facultyLoginBtn = findViewById(R.id.faculty_Btn);
    }

    public void adminLoginBtn(View view) {
        startActivity(new Intent(getApplicationContext(), LoginScreen.class));
    }

    public void studentLoginBtn(View view) {
        startActivity(new Intent(getApplicationContext(), StudentLoginPage.class));
    }

    public void facultyLoginBtn(View view) {
        startActivity(new Intent(getApplicationContext(), FacultyLoginPage.class));
    }
}