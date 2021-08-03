package com.pradipsahoo7722gmail.cms.StudentDashboard.StudentAttrndance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.SelectUserStream;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.StudentViewSyllabus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SelectUserSubject extends AppCompatActivity {

    TextInputLayout username, userPassword;
    String getUserName, getUserPassword;
    public static String student_stream;

    FirebaseDatabase database;
    DatabaseReference root;
    String registeredUserID;
    FirebaseUser currentUser;

//    Spinner subjectSpinner;
//    String student_subject;
//    String selectedSubject;
//    String[] subjectList = new String[]{"Select Semester", "Data Structure", "Algorithm", "DBMS", "Operating System", "Computer Architecture", "Automata Theory", "Compiler Design", "Machine Learning"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_subject);

        username = findViewById(R.id.Stu_userID);
        userPassword = findViewById(R.id.Stu_UserPassword);

        database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        registeredUserID = currentUser.getUid();
        root = database.getReference().child("AdminData");


//        //  subject spinner
//        final List<String> subject = new ArrayList<>(Arrays.asList(subjectList));
//        final ArrayAdapter<String> spinnerSubjectAdapter = new ArrayAdapter<String>(
//                this, R.layout.spinner_item, subject) {
//            @Override
//            public boolean isEnabled(int position) {
//                // Disable the first item from Spinner
//                // First item will be use for hint
//                return position != 0;
//            }
//
//            @Override
//            public View getDropDownView(int position, View convertView, @com.google.firebase.database.annotations.NotNull ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                } else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//
//        spinnerSubjectAdapter.setDropDownViewResource(R.layout.spinner_item);
//        subjectSpinner.setAdapter(spinnerSubjectAdapter);
//
//        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedSubject = (String) subjectSpinner.getSelectedItem();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
    }

    public void submitAttendanceDetails(View view) {

        getUserName = username.getEditText().getText().toString();
        getUserPassword = userPassword.getEditText().getText().toString();

//        student_subject = subjectSpinner.getSelectedItem().toString();

        if (!validateEmail() | !validatePassword()) {
            return;
        }

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot adminData : snapshot.getChildren()) {
                    String adminID = adminData.getKey();

                    Query query = root.child(adminID).child("Student");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot userData : snapshot.getChildren()) {
                                String userKey = userData.getKey();

                                if (userKey.equals(registeredUserID)) {
                                    Query newQuery = root.child(adminID).child("Student").child(userKey);
                                    newQuery.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            if (snapshot.child("roll_No").getValue().toString().equals(getUserName)) {
                                                if (snapshot.child("student_Password").getValue().toString().equals(getUserPassword)) {
                                                    student_stream = snapshot.child("student_Stream").getValue().toString();
                                                    Intent intent = new Intent(getApplicationContext(), StudentViewAttendance.class);
                                                    intent.putExtra("stream", student_stream);
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