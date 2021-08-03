package com.pradipsahoo7722gmail.cms.AdminDashboard.ViewStudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.AdminDashboard.AdminProfile;
import com.pradipsahoo7722gmail.cms.R;

public class ViewStudent extends AppCompatActivity {

//    RecyclerView recyclerView;
//    ViewStudentRecyclerAdapter recyclerAdapter;
//
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference root = database.getReference().child("Student");

//    @Override
//    protected void onStart() {
//        super.onStart();
//        recyclerAdapter.startListening();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        getSupportFragmentManager().beginTransaction().replace(R.id.studentWrapper, new StudentListFragment()).commit();

//        recyclerView = findViewById(R.id.viewStudent_recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        FirebaseRecyclerOptions<ViewStudentModel> options =
//                new FirebaseRecyclerOptions.Builder<ViewStudentModel>()
//                        .setQuery(root, ViewStudentModel.class)
//                        .build();
//
//        recyclerAdapter = new ViewStudentRecyclerAdapter(options);
//        recyclerView.setAdapter(recyclerAdapter);
    }

//    public void viewStudentBackBtn(View view) {
//        startActivity(new Intent(getApplicationContext(), AdminProfile.class));
//        finish();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        recyclerAdapter.stopListening();
//    }


}