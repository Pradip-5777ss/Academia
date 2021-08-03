package com.pradipsahoo7722gmail.cms.FacultyDashboard.AcademicNotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultyDashboard;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Calendar.StudentViewAcademicCalendarModel;

public class FacultyViewAcademicNotice extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;

    FacultyViewAcademicNoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view_academic_notice);

        recyclerView = findViewById(R.id.facultyViewNoticeRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        root = database.getReference("Notice");

        FirebaseRecyclerOptions<FacultyViewAcademicNoticeModel> options =
                new FirebaseRecyclerOptions.Builder<FacultyViewAcademicNoticeModel>()
                        .setQuery(root, FacultyViewAcademicNoticeModel.class)
                        .build();

        adapter = new FacultyViewAcademicNoticeAdapter(options);
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

    public void studentAcademicCalendarRecViewBackBtn(View view) {
        startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
        finish();
    }
}