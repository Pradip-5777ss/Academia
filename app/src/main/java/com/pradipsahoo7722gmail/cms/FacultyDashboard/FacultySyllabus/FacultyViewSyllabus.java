package com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultySyllabus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicSyllabus.UploadAcademicSyllabusModel;
import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FacultyViewSyllabus extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;
//    Query query;

    FacultyViewSyllabusAdapter adapter;

    String facultyStream;
    String adminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view_syllabus);

        recyclerView = findViewById(R.id.viewFacSyllabusRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        facultyStream = getIntent().getStringExtra("stream");

        backBtn = findViewById(R.id.viewStuSylBackBtn);
        database = FirebaseDatabase.getInstance();
        root = database.getReference().child("Academics").child("AcademicSyllabus");


//        root.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                for (DataSnapshot adminData : snapshot.getChildren()) {
//                    adminID = adminData.getKey();
//
////                    query = root.child(Objects.requireNonNull(adminID));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });


        Query query = root.orderByChild("stream").equalTo(facultyStream);
        FirebaseRecyclerOptions<FacultyViewSyllabusModel> options =
                new FirebaseRecyclerOptions.Builder<FacultyViewSyllabusModel>()
                        .setQuery(query, FacultyViewSyllabusModel.class)
                        .build();

        adapter = new FacultyViewSyllabusAdapter(options);
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

