package com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultySyllabus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultyDashboard;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.SelectUserStream;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.StudentViewSyllabus;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SelectFacultyStream extends AppCompatActivity {

    TextInputLayout username, userPassword;
    Button submitBtn;
    String getUserName, getUserPassword;
    public static String faculty_stream;

    FirebaseDatabase database;
    DatabaseReference root;
    String registeredUserID;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_faculty_stream);

        username = findViewById(R.id.Fac_userID);
        userPassword = findViewById(R.id.Fac_UserPassword);
        submitBtn = findViewById(R.id.Fac_viewSYL_doc);

        database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        registeredUserID = currentUser.getUid();
        root = database.getReference().child("AdminData");

    }

    public void facultyAcademicSyllabusRecViewBackBtn(View view) {
        startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
        finish();
    }

    public void submitFacDetails(View view) {
        getUserName = username.getEditText().getText().toString();
        getUserPassword = userPassword.getEditText().getText().toString();

        if (!validateEmail() | !validatePassword()) {
            return;
        }

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot adminData : snapshot.getChildren()) {
                    String adminID = adminData.getKey();

                    Query query = root.child(adminID).child("Faculty");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot userData : snapshot.getChildren()) {
                                String userKey = userData.getKey();

                                if (userKey.equals(registeredUserID)) {
                                    Query newQuery = root.child(adminID).child("Faculty").child(userKey);
                                    newQuery.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            if (snapshot.child("faculty_Id").getValue().toString().equals(getUserName)) {
                                                if (snapshot.child("faculty_Password").getValue().toString().equals(getUserPassword)) {
                                                    faculty_stream = snapshot.child("faculty_Stream").getValue().toString();
                                                    Intent intent = new Intent(getApplicationContext(), FacultyViewSyllabus.class);
                                                    intent.putExtra("stream", faculty_stream);
                                                    startActivity(intent);
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });

//        root.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if (snapshot.child("faculty_Id").getValue().toString().equals(getUserName)) {
//                    if (snapshot.child("faculty_Password").getValue().toString().equals(getUserPassword)) {
//                        student_stream = snapshot.child("faculty_Stream").getValue().toString();
//                        Intent intent = new Intent(getApplicationContext(), FacultyViewSyllabus.class);
//                        intent.putExtra("stream", student_stream);
//                        startActivity(intent);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//                Toast.makeText(SelectFacultyStream.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private boolean validateEmail() {
        String val = Objects.requireNonNull(username.getEditText()).getText().toString().trim();
//        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        }
//        else if (!val.matches(checkEmail)) {
//            username.setError("Invalid Email!");
//            return false;
//        }
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = Objects.requireNonNull(userPassword.getEditText()).getText().toString().trim();
        String checkPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        if (val.isEmpty()) {
            userPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            userPassword.setError("Password format is not correct!");
            Toast.makeText(getApplicationContext(), "Password must contain 1 capital letter, 1 small letter, 1 special character & 1 number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            userPassword.setError(null);
            userPassword.setErrorEnabled(false);
            return true;
        }
    }
}