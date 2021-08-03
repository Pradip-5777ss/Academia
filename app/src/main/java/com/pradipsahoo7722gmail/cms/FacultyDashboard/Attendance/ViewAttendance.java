package com.pradipsahoo7722gmail.cms.FacultyDashboard.Attendance;

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
import com.pradipsahoo7722gmail.cms.FacultyDashboard.AcademicCalendar.FacultyViewAcademicCalendarModel;
import com.pradipsahoo7722gmail.cms.R;

public class ViewAttendance extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;

    AttendanceTakingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        recyclerView = findViewById(R.id.facultyViewAttendanceRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        root = database.getReference().child("Attendance");


        FirebaseRecyclerOptions<AttendanceTakingModel> options =
                new FirebaseRecyclerOptions.Builder<AttendanceTakingModel>()
                        .setQuery(root, AttendanceTakingModel.class)
                        .build();

        adapter = new AttendanceTakingAdapter(options);
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

    public void facultyAttendanceRecViewBackBtn(View view) {
        startActivity(new Intent(getApplicationContext(), AttendanceTaking.class));
        finish();
    }
}