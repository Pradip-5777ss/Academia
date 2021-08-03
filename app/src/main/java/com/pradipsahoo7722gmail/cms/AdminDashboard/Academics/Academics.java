package com.pradipsahoo7722gmail.cms.AdminDashboard.Academics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicRoutine.UploadAcademicRoutine;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicSyllabus.UploadAcademicSyllabus;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.StudyMaterial.UploadStudyMaterial;
import com.pradipsahoo7722gmail.cms.AdminDashboard.AdminProfile;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicCalendar.UploadAcademicCalendar;

public class Academics extends AppCompatActivity {

    ImageView backButton;
    Button academicCalender, academicResources, academicRoutine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics);

        backButton = findViewById(R.id.academics_backBtn);
        academicCalender = findViewById(R.id.academic_calender);
        academicResources = findViewById(R.id.study_material);
        academicRoutine = findViewById(R.id.academic_routine);
    }

    public void academicsBackBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminProfile.class);
        startActivity(intent);
        finish();
    }

    public void academicCalender(View view) {
        Intent uploadCalender = new Intent(getApplicationContext(), UploadAcademicCalendar.class);
        startActivity(uploadCalender);
    }

    public void studyMaterial(View view) {
        Intent uploadResources = new Intent(getApplicationContext(), UploadStudyMaterial.class);
        startActivity(uploadResources);
    }

    public void academicRoutine(View view) {
        Intent uploadRoutine = new Intent(getApplicationContext(), UploadAcademicRoutine.class);
        startActivity(uploadRoutine);
    }

    public void academicSyllabus(View view) {
        Intent uploadSyllabus = new Intent(getApplicationContext(), UploadAcademicSyllabus.class);
        startActivity(uploadSyllabus);
    }
}