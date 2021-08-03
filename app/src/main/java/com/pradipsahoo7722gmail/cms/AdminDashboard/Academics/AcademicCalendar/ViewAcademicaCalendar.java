package com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.R;

public class ViewAcademicaCalendar extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    FirebaseUser AdminId;
    String registerAdminID;
    DatabaseReference root;

    ViewCalendarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_academica_calendar);

        recyclerView = findViewById(R.id.viewCalendarRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //testing one
//        database = FirebaseDatabase.getInstance();
//        AdminId = FirebaseAuth.getInstance().getCurrentUser();
//        registerAdminID = AdminId.getUid();
//        root = database.getReference().child("AdminData").child(registerAdminID).child("Academics").child("AcademicCalendar");

        //right one
        database = FirebaseDatabase.getInstance();
        root = database.getReference("Academics").child("AcademicCalendar");

        FirebaseRecyclerOptions<UploadCalendarHelperText> options =
                new FirebaseRecyclerOptions.Builder<UploadCalendarHelperText>()
                        .setQuery(root, UploadCalendarHelperText.class)
                        .build();

        adapter = new ViewCalendarAdapter(options);
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

    public void academicCalendarRecViewBackBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), UploadAcademicCalendar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}