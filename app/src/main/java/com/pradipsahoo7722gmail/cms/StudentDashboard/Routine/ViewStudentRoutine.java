package com.pradipsahoo7722gmail.cms.StudentDashboard.Routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Notice.StudentViewNoticeAdapter;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Notice.StudentViewNoticeModel;

public class ViewStudentRoutine extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;

    ViewStudentRoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_routine);

        recyclerView = findViewById(R.id.student_viewRoutineRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicRoutine");

        FirebaseRecyclerOptions<ViewStudentRoutineModel> options =
                new FirebaseRecyclerOptions.Builder<ViewStudentRoutineModel>()
                        .setQuery(root, ViewStudentRoutineModel.class)
                        .build();

        adapter = new ViewStudentRoutineAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}