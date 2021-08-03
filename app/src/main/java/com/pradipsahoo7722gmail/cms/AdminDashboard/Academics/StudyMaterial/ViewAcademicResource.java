package com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.StudyMaterial;

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
import com.pradipsahoo7722gmail.cms.R;

public class ViewAcademicResource extends AppCompatActivity {

//    TextView textView;
//    ImageButton backBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    FirebaseUser AdminId;
    String registerAdminID;
    DatabaseReference root;

    ViewAcademicResourcesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_academic_resource);

        recyclerView = findViewById(R.id.viewResourceRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        textView = findViewById(R.id.acaRes);
//        backBtn = findViewById(R.id.acaResBackBtn);

        //testing one
//        database = FirebaseDatabase.getInstance();
//        AdminId = FirebaseAuth.getInstance().getCurrentUser();
//        registerAdminID = AdminId.getUid();
//        root = database.getReference().child("AdminData").child(registerAdminID).child("Academics").child("AcademicStudyMaterial");

        //right one
        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicResource");

        FirebaseRecyclerOptions<UploadAcademicResourcesHelperClass> options =
                new FirebaseRecyclerOptions.Builder<UploadAcademicResourcesHelperClass>()
                        .setQuery(root, UploadAcademicResourcesHelperClass.class)
                        .build();

        adapter = new ViewAcademicResourcesAdapter(options);
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


    public void academicResourceRecViewBackBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), UploadStudyMaterial.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}