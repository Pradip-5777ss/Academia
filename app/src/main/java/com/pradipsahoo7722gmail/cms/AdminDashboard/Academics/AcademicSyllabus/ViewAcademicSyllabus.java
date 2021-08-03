package com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicSyllabus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.StudentViewSyllabusModel;

public class ViewAcademicSyllabus extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    FirebaseUser AdminId;
    String registerAdminID;
    DatabaseReference root;

    ViewAcademicSyllabusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_academic_syllabus);

        recyclerView = findViewById(R.id.viewAcademicSyllabusRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn = findViewById(R.id.viewSylBackBtn);

        //testing one
//        database = FirebaseDatabase.getInstance();
//        AdminId = FirebaseAuth.getInstance().getCurrentUser();
//        registerAdminID = AdminId.getUid();
//        root = database.getReference().child("AdminData").child(registerAdminID).child("Academics").child("AcademicSyllabus");

        //right one
        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicSyllabus");

        FirebaseRecyclerOptions<UploadAcademicSyllabusModel> options =
                new FirebaseRecyclerOptions.Builder<UploadAcademicSyllabusModel>()
                        .setQuery(root, UploadAcademicSyllabusModel.class)
                        .build();

        adapter = new ViewAcademicSyllabusAdapter(options);
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