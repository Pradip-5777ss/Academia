package com.pradipsahoo7722gmail.cms.StudentDashboard.Notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Notice.UploadNoticeModel;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.StudentDashboard.StudentDashboard;

public class StudentViewNoticeRecView extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;

    StudentViewNoticeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_notice_rec_view);

        recyclerView = findViewById(R.id.student_viewNoticeRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        root = database.getReference("Notice");

        FirebaseRecyclerOptions<StudentViewNoticeModel> options =
                new FirebaseRecyclerOptions.Builder<StudentViewNoticeModel>()
                        .setQuery(root, StudentViewNoticeModel.class)
                        .build();

        adapter = new StudentViewNoticeAdapter(options);
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
        startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
        finish();
    }
}