package com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultyRoutine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultyDashboard;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Routine.ViewStudentRoutineAdapter;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Routine.ViewStudentRoutineModel;

public class FacultyViewRoutine extends AppCompatActivity {

    ImageButton back;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;

    FacultyViewRoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view_routine);

        back = findViewById(R.id.facultyView_routineBackBtn);

        recyclerView = findViewById(R.id.faculty_viewRoutineRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicRoutine");

        FirebaseRecyclerOptions<FacultyViewRoutineModel> options =
                new FirebaseRecyclerOptions.Builder<FacultyViewRoutineModel>()
                        .setQuery(root, FacultyViewRoutineModel.class)
                        .build();

        adapter = new FacultyViewRoutineAdapter(options);
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

    public void studentAcademicNoticeRecViewBackBtn(View view) {
        startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
    }
}