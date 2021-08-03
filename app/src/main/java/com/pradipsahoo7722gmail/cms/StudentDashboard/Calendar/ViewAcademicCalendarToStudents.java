package com.pradipsahoo7722gmail.cms.StudentDashboard.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.R;

public class ViewAcademicCalendarToStudents extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;

    ViewAcademicCalendarToStudentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_academic_calendar_to_students);

        recyclerView = findViewById(R.id.studentViewCalendarRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicCalendar");

        FirebaseRecyclerOptions<StudentViewAcademicCalendarModel> options =
                new FirebaseRecyclerOptions.Builder<StudentViewAcademicCalendarModel>()
                        .setQuery(root, StudentViewAcademicCalendarModel.class)
                        .build();

        adapter = new ViewAcademicCalendarToStudentsAdapter(options);
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
}