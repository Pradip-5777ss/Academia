package com.pradipsahoo7722gmail.cms.SelectUserLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pradipsahoo7722gmail.cms.AdminDashboard.AdminProfile;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultyDashboard;
import com.pradipsahoo7722gmail.cms.InternetConnectivity.InternetConnection;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.RegisterAdmin.SignupAdmin;
import com.pradipsahoo7722gmail.cms.StudentDashboard.StudentDashboard;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.pradipsahoo7722gmail.cms.R.style.CustomDialogTheme;

public class StudentLoginPage extends AppCompatActivity {

    TextInputLayout student_Email, student_Password;
    Button studentLoginBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                    WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        student_Email = findViewById(R.id.student_Login_Email);
        student_Password = findViewById(R.id.student_Login_Password);
        studentLoginBtn = findViewById(R.id.student_LoginBtn);
        progressBar = findViewById(R.id.student_LoginProgressBar);
    }

    public void studentLoginSuccess(View view) {

        InternetConnection checkInternet = new InternetConnection();
        if (!checkInternet.isConnected(StudentLoginPage.this)) {
            showCustomDialog();
            return;
        }

        if (!validateEmail() | !validatePassword()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        String studentEmail = student_Email.getEditText().getText().toString();
        String studentPassword = student_Password.getEditText().getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference root = database.getReference().child("AdminData");


        //right one
//        mAuth.signInWithEmailAndPassword(studentEmail, studentPassword)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                            String RegisteredUserID = Objects.requireNonNull(currentUser).getUid();
//
//                            DatabaseReference root = database.getReference().child("Student").child(RegisteredUserID);
//
//                            root.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                                    String user_Type = snapshot.child("userType").getValue().toString();
//                                    if (user_Type.equals("Student")) {
//                                        Intent intent = new Intent(getApplicationContext(), StudentDashboard.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                        Toast.makeText(StudentLoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                    } else {
//                                        Toast.makeText(StudentLoginPage.this, "No such user exists!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                        student_Email.setError("Enter valid username");
//                                        student_Email.setErrorEnabled(true);
//                                        student_Email.getEditText().setText("");
//                                        student_Password.setError("Enter valid password");
//                                        student_Password.setErrorEnabled(true);
//                                        student_Password.getEditText().setText("");
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                                }
//                            });
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull @NotNull Exception e) {
//                        Toast.makeText(StudentLoginPage.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
//                        student_Email.setError("Enter valid username");
//                        student_Email.setErrorEnabled(true);
//                        student_Email.getEditText().setText("");
//                        student_Password.setError("Enter valid password");
//                        student_Password.setErrorEnabled(true);
//                        student_Password.getEditText().setText("");
//                    }
//                });
        //end of right one

        //testing one
        mAuth.signInWithEmailAndPassword(studentEmail, studentPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID = Objects.requireNonNull(currentUser).getUid();

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

                                                    if (userKey.equals(RegisteredUserID)) {

                                                        Query newQuery = root.child(adminID).child("Student").child(userKey);
                                                        newQuery.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                String user_type = snapshot.child("userType").getValue().toString();
                                                                if (user_type.equals("Student")) {
                                                                    Intent intent = new Intent(getApplicationContext(), StudentDashboard.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                    startActivity(intent);
                                                                    Toast.makeText(StudentLoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                }
                                                                else {
                                                                    Toast.makeText(StudentLoginPage.this, "No such user exists!", Toast.LENGTH_SHORT).show();
                                                                    progressBar.setVisibility(View.GONE);
                                                                    student_Email.setError("Enter valid username");
                                                                    student_Email.setErrorEnabled(true);
                                                                    student_Email.getEditText().setText("");
                                                                    student_Password.setError("Enter valid password");
                                                                    student_Password.setErrorEnabled(true);
                                                                    student_Password.getEditText().setText("");
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                                Toast.makeText(StudentLoginPage.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
//                                                    else {
//                                                        Toast.makeText(StudentLoginPage.this, "No such user exists!", Toast.LENGTH_SHORT).show();
//                                                        progressBar.setVisibility(View.GONE);
//                                                        student_Email.setError("Enter valid username");
//                                                        student_Email.setErrorEnabled(true);
//                                                        student_Email.getEditText().setText("");
//                                                        student_Password.setError("Enter valid password");
//                                                        student_Password.setErrorEnabled(true);
//                                                        student_Password.getEditText().setText("");
//                                                    }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                Toast.makeText(StudentLoginPage.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                    Toast.makeText(StudentLoginPage.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(StudentLoginPage.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        student_Email.setError("Enter valid username");
                        student_Email.setErrorEnabled(true);
                        student_Email.getEditText().setText("");
                        student_Password.setError("Enter valid password");
                        student_Password.setErrorEnabled(true);
                        student_Password.getEditText().setText("");
                    }
                });
    }


    private boolean validateEmail() {
        String val = Objects.requireNonNull(student_Email.getEditText()).getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            student_Email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            student_Email.setError("Invalid Email!");
            return false;
        } else {
            student_Email.setError(null);
            student_Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = Objects.requireNonNull(student_Password.getEditText()).getText().toString().trim();
        String checkPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        if (val.isEmpty()) {
            student_Password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            student_Password.setError("Password format is not correct!");
            Toast.makeText(getApplicationContext(), "Password must contain 1 capital letter, 1 small letter, 1 special character & 1 number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            student_Password.setError(null);
            student_Password.setErrorEnabled(false);
            return true;
        }
    }

    //Custom dialog for check internet
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentLoginPage.this, CustomDialogTheme);
        builder.setMessage("Connection Error! Please connect to the internet")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), SignupAdmin.class));
                    finish();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}