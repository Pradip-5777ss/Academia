package com.pradipsahoo7722gmail.cms.StudentDashboard.StudentAttrndance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.StudentViewSyllabusAdapter;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.StudentViewSyllabusModel;

public class StudentViewAttendance extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;
    StudenceAttendanceAdapter adapter;

    String studentStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_attendance);

        recyclerView = findViewById(R.id.studentViewAttendanceRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentStream = getIntent().getStringExtra("stream");

        backBtn = findViewById(R.id.viewStuSylBackBtn);
        database = FirebaseDatabase.getInstance();
        root = database.getReference().child("Attendance");

        Query query = root.orderByChild("stream").equalTo(studentStream);
        FirebaseRecyclerOptions<StudenceAttendanceModel> options =
                new FirebaseRecyclerOptions.Builder<StudenceAttendanceModel>()
                        .setQuery(query, StudenceAttendanceModel.class)
                        .build();

      adapter = new StudenceAttendanceAdapter(options);
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