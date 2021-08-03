package com.pradipsahoo7722gmail.cms.LoginUser;

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
import com.google.firebase.database.ValueEventListener;
import com.pradipsahoo7722gmail.cms.AdminDashboard.AdminProfile;
import com.pradipsahoo7722gmail.cms.InternetConnectivity.InternetConnection;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.RegisterAdmin.SignupAdmin;
import com.pradipsahoo7722gmail.cms.SelectUserLogin.StudentLoginPage;

import org.jetbrains.annotations.NotNull;

import static com.pradipsahoo7722gmail.cms.R.style.CustomDialogTheme;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {

    private TextInputLayout user_login_email, user_login_password;
    Button login_btn;
    ProgressBar progressBar;
//    String keyValue;
//    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        /*
         *  Set the navigation bar style
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        /*
         *  Define all fields of the LoginScreen xml file by their view id
         */
        user_login_email = (TextInputLayout) findViewById(R.id.Login_Email);
        user_login_password = (TextInputLayout) findViewById(R.id.Login_Password);
        login_btn = (Button) findViewById(R.id.userLoginBtn);
        progressBar = (ProgressBar) findViewById(R.id.LoginProgressBar);

    }

    // Login button onclick method
    public void UserLoginSuccess(View view) {

        InternetConnection checkInternet = new InternetConnection();
        if (!checkInternet.isConnected(LoginScreen.this)) {
            showCustomDialog();
            return;
        }

        if (!validateEmail() | !validatePassword()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        String getEmail = user_login_email.getEditText().getText().toString();
        String getPassword = user_login_password.getEditText().getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        mAuth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID = Objects.requireNonNull(currentUser).getUid();

                            DatabaseReference root = database.getReference().child("Admin").child(RegisteredUserID);

                            root.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    String user_Type = snapshot.child("userType").getValue().toString();

                                    if (user_Type.equals("Admin")) {
                                        Intent intentAdmin = new Intent(getApplicationContext(), AdminProfile.class);
                                        intentAdmin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intentAdmin);
                                        Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(LoginScreen.this, "No such user exists!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        user_login_email.getEditText().setText("");
                                        user_login_email.setError("Invalid user name");
                                        user_login_email.setErrorEnabled(true);
                                        user_login_password.getEditText().setText("");
                                        user_login_password.setError("Invalid Password");
                                        user_login_password.setErrorEnabled(true);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginScreen.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        user_login_email.getEditText().setText("");
                        user_login_email.setError("Invalid user name");
                        user_login_email.setErrorEnabled(true);
                        user_login_password.getEditText().setText("");
                        user_login_password.setError("Invalid Password");
                        user_login_password.setErrorEnabled(true);
                    }
                });
    }


    //Custom dialog for check internet
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this, CustomDialogTheme);
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


    private boolean validateEmail() {
        String val = Objects.requireNonNull(user_login_email.getEditText()).getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            user_login_email.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(checkEmail)) {
            user_login_email.setError("Invalid Email!");
            return false;
        }
        else {
            user_login_email.setError(null);
            user_login_email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = Objects.requireNonNull(user_login_password.getEditText()).getText().toString().trim();
        String checkPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        if (val.isEmpty()) {
            user_login_password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            user_login_password.setError("Password format is not correct!");
            Toast.makeText(getApplicationContext(), "Password must contain 1 capital letter, 1 small letter, 1 special character & 1 number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            user_login_password.setError(null);
            user_login_password.setErrorEnabled(false);
            return true;
        }
    }

    public void onSignUpClick(View View) {
        startActivity(new Intent(this, SignupAdmin.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        finish();
    }
//
//    /**
//     * @param view Function to call the
//     *             forgot password screen
//     */
////    public void callForgotPassword(View view) {
////        startActivity(new Intent(LoginScreen.this, ForgotPassword.class));
//        finish();
//    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//        System.exit(0);
//    }
}