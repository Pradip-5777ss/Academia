package com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus;

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

import static com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.SelectUserStream.student_stream;

public class StudentViewSyllabus extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;
    StudentViewSyllabusAdapter adapter;

    String studentStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_syllabus);

        recyclerView = findViewById(R.id.viewSyllabusRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentStream = getIntent().getStringExtra("stream");


        backBtn = findViewById(R.id.viewStuSylBackBtn);
        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicSyllabus");

        Query query = root.orderByChild("stream").equalTo(studentStream);
        FirebaseRecyclerOptions<StudentViewSyllabusModel> options =
                new FirebaseRecyclerOptions.Builder<StudentViewSyllabusModel>()
                        .setQuery(query, StudentViewSyllabusModel.class)
                        .build();

        adapter = new StudentViewSyllabusAdapter(options);
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