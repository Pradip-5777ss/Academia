package com.pradipsahoo7722gmail.cms.FacultyDashboard.AcademicCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultyDashboard;
import com.pradipsahoo7722gmail.cms.R;

public class ViewAcademicCalendarToFaculty extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;

    FacultyViewAcademicCalendarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_academic_calendar_to_faculty);

        recyclerView = findViewById(R.id.facultyViewCalendarRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn = findViewById(R.id.faculty_calendar_backBtn);

        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicCalendar");

        FirebaseRecyclerOptions<FacultyViewAcademicCalendarModel> options =
                new FirebaseRecyclerOptions.Builder<FacultyViewAcademicCalendarModel>()
                        .setQuery(root, FacultyViewAcademicCalendarModel.class)
                        .build();

        adapter = new FacultyViewAcademicCalendarAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void facultyAcademicCalendarRecViewBackBtn(View view) {
        startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
        finish();
    }
}