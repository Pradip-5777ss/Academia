package com.pradipsahoo7722gmail.cms.FacultyDashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

public class FacultyUser extends AppCompatActivity {

    ImageView back;
    TextView firstname, middlename, lastname, d_o_b, phone, aadhar, gender, email, address, doj,
            stream, id, department, status, caste, nationality, password;

    FirebaseDatabase database;
    FirebaseUser currentUser;
    String registeredUserID;
    DatabaseReference root;

    String first_Name, middle_Name, last_Name, phone_No, adhar_No, faculty_Id, email_id,
            faculty_Address, faculty_Password, dob, date_of_join, faculty_Gender,
            faculty_Department, faculty_Stream, faculty_Status, faculty_Caste, faculty_Nationality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_user);

        firstname = findViewById(R.id.facultyFirstname);
        middlename = findViewById(R.id.facultyMiddlename);
        lastname = findViewById(R.id.facultyLastname);
        d_o_b = findViewById(R.id.facultyDateOfBirth);
        phone = findViewById(R.id.facultyPhone);
        doj = findViewById(R.id.facultyDOJ);
        aadhar = findViewById(R.id.facultyAadhar);
        gender = findViewById(R.id.facultyGender);
        email = findViewById(R.id.facultyEmail);
        address = findViewById(R.id.facultyAddress);
        id = findViewById(R.id.facultyID);
        department = findViewById(R.id.facultyDept);
        stream = findViewById(R.id.facultyStream);
        status = findViewById(R.id.facultyStatus);
        caste = findViewById(R.id.facultyCaste);
        nationality = findViewById(R.id.facultyCountry);
        password = findViewById(R.id.facultyPassword);

        database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        registeredUserID = currentUser.getUid();
        root = database.getReference().child("AdminData");

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
                                            String user_Type = snapshot.child("userType").getValue().toString();
                                            if (user_Type.equals("Faculty")) {
                                                first_Name = snapshot.child("first_Name").getValue().toString();
                                                firstname.setText(first_Name);
                                                middle_Name = snapshot.child("middle_Name").getValue().toString();
                                                middlename.setText(middle_Name);
                                                last_Name = snapshot.child("last_Name").getValue().toString();
                                                lastname.setText(last_Name);
                                                dob = snapshot.child("dob").getValue().toString();
                                                d_o_b.setText(dob);
                                                phone_No = snapshot.child("phone_No").getValue().toString();
                                                phone.setText(phone_No);
                                                adhar_No = snapshot.child("adhar_No").getValue().toString();
                                                aadhar.setText(adhar_No);
                                                faculty_Caste = snapshot.child("faculty_Caste").getValue().toString();
                                                caste.setText(faculty_Caste);
                                                faculty_Gender = snapshot.child("faculty_Gender").getValue().toString();
                                                gender.setText(faculty_Gender);
                                                email_id = snapshot.child("email_id").getValue().toString();
                                                email.setText(email_id);
                                                faculty_Address = snapshot.child("faculty_Address").getValue().toString();
                                                address.setText(faculty_Address);
                                                faculty_Id = snapshot.child("faculty_Id").getValue().toString();
                                                id.setText(faculty_Id);
                                                date_of_join = snapshot.child("date_of_join").getValue().toString();
                                                doj.setText(date_of_join);
                                                faculty_Stream = snapshot.child("faculty_Stream").getValue().toString();
                                                stream.setText(faculty_Stream);
                                                faculty_Status = snapshot.child("faculty_Status").getValue().toString();
                                                status.setText(faculty_Status);
                                                faculty_Department = snapshot.child("faculty_Department").getValue().toString();
                                                department.setText(faculty_Department);
                                                faculty_Nationality = snapshot.child("faculty_Nationality").getValue().toString();
                                                nationality.setText(faculty_Nationality);
                                                faculty_Password = snapshot.child("faculty_Password").getValue().toString();
                                                password.setText(faculty_Password);

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

    public void facultyProfileBackToPreviousPage(View view) {
        Intent intent = new Intent(getApplicationContext(), FacultyDashboard.class);
        startActivity(intent);
        finish();
    }
}