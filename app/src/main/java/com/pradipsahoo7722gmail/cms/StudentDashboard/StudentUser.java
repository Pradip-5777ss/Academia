package com.pradipsahoo7722gmail.cms.StudentDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class StudentUser extends AppCompatActivity {

    ImageView back;
    TextView firstname, middlename, lastname, d_o_b, phone, aadhar, gender, email, address,
            regNo, roll, semester, year, stream, parentsName, parentPh, course, caste, nationality, password;

    FirebaseDatabase database;
    FirebaseUser currentUser;
    String registeredUserID;
    DatabaseReference root;

    String first_Name, middle_Name, last_Name, phone_No, adhar_No, registration_No, roll_No,
            email_id, parents_Name, parents_Contact, student_Address, student_Password, dob,
            student_Gender, student_Caste, student_Country, student_Course, student_Stream,
            student_Year, student_Semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_user);

        back = findViewById(R.id.studentUserBackBtn);
        firstname = findViewById(R.id.studentFirstname);
        middlename = findViewById(R.id.studentMiddlename);
        lastname = findViewById(R.id.studentLastname);
        d_o_b = findViewById(R.id.studentDateOfBirth);
        phone = findViewById(R.id.studentPhone);
        aadhar = findViewById(R.id.studentAadhar);
        gender = findViewById(R.id.studentGender);
        email = findViewById(R.id.studentEmail);
        address = findViewById(R.id.studentAddress);
        regNo = findViewById(R.id.studentRegNo);
        roll = findViewById(R.id.studentRollNo);
        semester = findViewById(R.id.studentSemester);
        year = findViewById(R.id.studentyear);
        stream = findViewById(R.id.studentStream);
        parentsName = findViewById(R.id.studentParentName);
        parentPh = findViewById(R.id.studentParentPhone);
        course = findViewById(R.id.studentCourse);
        caste = findViewById(R.id.studentCaste);
        nationality = findViewById(R.id.studentCountry);
        password = findViewById(R.id.studentPassword);

        database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        registeredUserID = currentUser.getUid();
        root = database.getReference().child("AdminData");

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
                                            String user_Type = snapshot.child("userType").getValue().toString();
                                            if (user_Type.equals("Student")) {
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
                                                student_Caste = snapshot.child("student_Caste").getValue().toString();
                                                caste.setText(student_Caste);
                                                student_Gender = snapshot.child("student_Gender").getValue().toString();
                                                gender.setText(student_Gender);
                                                email_id = snapshot.child("email_id").getValue().toString();
                                                email.setText(email_id);
                                                student_Address = snapshot.child("student_Address").getValue().toString();
                                                address.setText(student_Address);
                                                roll_No = snapshot.child("roll_No").getValue().toString();
                                                roll.setText(roll_No);
                                                registration_No = snapshot.child("registration_No").getValue().toString();
                                                regNo.setText(registration_No);
                                                student_Stream = snapshot.child("student_Stream").getValue().toString();
                                                stream.setText(student_Stream);
                                                student_Course = snapshot.child("student_Course").getValue().toString();
                                                course.setText(student_Course);
                                                student_Year = snapshot.child("student_Year").getValue().toString();
                                                year.setText(student_Year);
                                                student_Semester = snapshot.child("student_Semester").getValue().toString();
                                                semester.setText(student_Semester);
                                                student_Country = snapshot.child("student_Country").getValue().toString();
                                                nationality.setText(student_Country);
                                                parents_Name = snapshot.child("parents_Name").getValue().toString();
                                                parentsName.setText(parents_Name);
                                                parents_Contact = snapshot.child("parents_Contact").getValue().toString();
                                                parentPh.setText(parents_Contact);
                                                student_Password = snapshot.child("student_Password").getValue().toString();
                                                password.setText(student_Password);

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

    public void studentProfileBackToPreviousPage(View view) {
        Intent intent = new Intent(getApplicationContext(), StudentDashboard.class);
        startActivity(intent);
        finish();
    }
}