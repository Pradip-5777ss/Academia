package com.pradipsahoo7722gmail.cms.AdminDashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.Academics;
import com.pradipsahoo7722gmail.cms.AdminDashboard.AddFaculty.AddFaculty;
import com.pradipsahoo7722gmail.cms.AdminDashboard.AddStudent.AddStudent;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Notice.UploadNotice;
import com.pradipsahoo7722gmail.cms.AdminDashboard.ViewFaculty.ViewFaculty;
import com.pradipsahoo7722gmail.cms.AdminDashboard.ViewStudent.ViewStudent;
import com.pradipsahoo7722gmail.cms.LoginUser.LoginScreen;
import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

public class AdminProfile extends AppCompatActivity {

    CardView addStudent, viewStudent, addFaculty, viewFaculty, academics, logOut;
    TextView userName, userEmail;

//    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        userName = findViewById(R.id.admin_name);
        userEmail = findViewById(R.id.email_view_admin_dashboard);

        addStudent = findViewById(R.id.addStudent_Cardview);
        viewStudent = findViewById(R.id.viewStudent_Cardview);
        addFaculty = findViewById(R.id.addFaculty_Cardview);
        viewFaculty = findViewById(R.id.viewFaculty_Cardview);
        academics = findViewById(R.id.academics_Cardview);

        logOut = findViewById(R.id.logoutBtn);

//        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String registeredUserID = currentUser.getUid();

        DatabaseReference root = database.getReference().child("Admin").child(registeredUserID);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String user_Type = snapshot.child("userType").getValue().toString();
                if (user_Type.equals("Admin")) {
                    String name = snapshot.child("fullName").getValue().toString();
                    userName.setText(name);
                    String email = snapshot.child("email").getValue().toString();
                    userEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }

    public void addStudentBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), AddStudent.class);
        startActivity(intent);
//        finish();
    }

    public void viewStudentBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewStudent.class);
        startActivity(intent);
//        finish();
    }

    public void addFacultyBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), AddFaculty.class);
        startActivity(intent);
//        finish();
    }

    public void viewFacultyBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewFaculty.class);
        startActivity(intent);
//        finish();
    }

    public void academicsBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), Academics.class);
        startActivity(intent);
//        finish();
    }

    public void logOutBtn(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AdminProfile.this, LoginScreen.class));
        finish();
    }

    public void uploadNotice(View view) {
        Intent intent = new Intent(getApplicationContext(), UploadNotice.class);
        startActivity(intent);
    }
}