package com.pradipsahoo7722gmail.cms.AdminDashboard.Notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicCalendar.UploadCalendarHelperText;
import com.pradipsahoo7722gmail.cms.R;

public class ViewNotice extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference root;

    ViewNoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);

        recyclerView = findViewById(R.id.viewNoticeRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        root = database.getReference("Notice");
        FirebaseRecyclerOptions<UploadNoticeModel> options =
                new FirebaseRecyclerOptions.Builder<UploadNoticeModel>()
                        .setQuery(root, UploadNoticeModel.class)
                        .build();

        adapter = new ViewNoticeAdapter(options);
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

    public void academicNoticeRecViewBackBtn(View view) {
        startActivity(new Intent(getApplicationContext(), UploadNotice.class));
        finish();
    }
}