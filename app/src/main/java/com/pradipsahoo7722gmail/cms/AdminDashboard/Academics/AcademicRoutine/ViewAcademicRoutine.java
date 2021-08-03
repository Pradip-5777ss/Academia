package com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicRoutine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.Academics;
import com.pradipsahoo7722gmail.cms.R;

public class ViewAcademicRoutine extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    FirebaseUser AdminId;
    String registerAdminID;
    DatabaseReference root;

    ViewRoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_academic_routine);

        recyclerView = findViewById(R.id.viewRoutineRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn = findViewById(R.id.viewRoutineBackBtn);

        //testing one
//        database = FirebaseDatabase.getInstance();
//        AdminId = FirebaseAuth.getInstance().getCurrentUser();
//        registerAdminID = AdminId.getUid();
//        root = database.getReference().child("AdminData").child(registerAdminID).child("Academics").child("AcademicRoutine");


        //right one
        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicRoutine");

        FirebaseRecyclerOptions<UploadRoutineHelperClass> options =
                new FirebaseRecyclerOptions.Builder<UploadRoutineHelperClass>()
                        .setQuery(root, UploadRoutineHelperClass.class)
                        .build();

        adapter = new ViewRoutineAdapter(options);
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


    public void academicRoutineRecViewBackBtn(View view) {
        startActivity(new Intent(getApplicationContext(), UploadAcademicRoutine.class));
    }
}