package com.pradipsahoo7722gmail.cms.AdminDashboard.ViewFaculty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pradipsahoo7722gmail.cms.R;

public class ViewFaculty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new RecFragment()).commit();

    }

}