package com.pradipsahoo7722gmail.cms.RegisterAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.LoginUser.LoginScreen;
import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

public class SignupAdminThirdScreen extends AppCompatActivity {

    /**
     * Define variables for animation
     */
    ImageView SignUpImage, backArrow;
    TextView SignUpText;
    Button register;
    CheckBox checkUser;

    /**
     * Define variables for get the data from the field
     */
//    CountryCodePicker countryCodePicker;
    TextInputLayout phone;


    String fullName, userName, email, password, gender, age, phoneNo, userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin_third_screen);


        //  For define the style of the navigation bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        //  Hooks for animation
        SignUpImage = findViewById(R.id.signUp_image);
        SignUpText = findViewById(R.id.signUp_title_text);
        backArrow = findViewById(R.id.arrow_btn);
        register = findViewById(R.id.reg_get_otp_btn);
        checkUser = findViewById(R.id.confirm);

        //  Hooks for get the data from the field
        phone = findViewById(R.id.regPhoneNo);
//        countryCodePicker = findViewById(R.id.country_code_picker);
    }

    /**
     * get otp button onclick method
     *
     * @param view
     */
    public void callNextVerifyOtpScreen(View view) {

        //Validate fields
        if (!validPhoneNumber()) {
            return;
        } //Validation succeeded and now move to next screen to verify phone number and save data

        //get all values passed from previous screens using Intent
        fullName = getIntent().getStringExtra("fullName");
        userName = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        gender = getIntent().getStringExtra("gender");
        age = getIntent().getStringExtra("age");


        //get the user entered phone number and concatenated with the countryCodePicker to make a valid phone number
        phoneNo = phone.getEditText().getText().toString().trim();
//        String _phoneNo = "+" + countryCodePicker.getFullNumber() + getUserEnteredPhoneNumber;

        if (!checkUser.isChecked()) {
            Toast.makeText(this, "Confirmation Error!!!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userType = "Admin";
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference root = database.getReference("Admin");

        AdminRegisterHelperClass addUser = new AdminRegisterHelperClass(fullName, userName, email, password, gender, age, phoneNo, userType);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String uid = FirebaseAuth.getInstance().getUid();
                            root.child(uid).setValue(addUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupAdminThirdScreen.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            phone.getEditText().setText("");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(SignupAdminThirdScreen.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
//
//        //Pass all fields to the next activity
//        intent.putExtra("fullName", _fullName);
//        intent.putExtra("username", _username);
//        intent.putExtra("email", _email);
//        intent.putExtra("password", _password);
//        intent.putExtra("gender", _gender);
//        intent.putExtra("age", _age);
//        intent.putExtra("phoneNo", _phoneNo);
//
//        //Add Transition
//        Pair[] pairs = new Pair[3];
//        pairs[0] = new Pair<View, String>(SignUpText, "transition_signUp_title_text");
//        pairs[1] = new Pair<View, String>(SignUpImage, "transition_signUp_image");
//        pairs[2] = new Pair<View, String>(getOtp, "transition_next_btn");
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignupAdminThirdScreen.this, pairs);
//            startActivity(intent, options.toBundle());
//        } else {
//            startActivity(intent);
    }

//}

//    private void storeUserData() {
//
//        DatabaseReference root = database.getReference("Admin");
////        DatabaseReference dbRef = root.push();
//
////        String key = dbRef.getKey();
//        AdminRegisterHelperClass addUser = new AdminRegisterHelperClass(fullName, userName, email, password, gender, age, phoneNo, userType);
//
//        root.child(userName).setValue(addUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
//                Toast.makeText(SignupAdminThirdScreen.this, "Register Successfully", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull @NotNull Exception e) {
//                Toast.makeText(SignupAdminThirdScreen.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    /**
     * This method check the user entered phone number is valid or not
     *
     * @return true/false
     */
    private boolean validPhoneNumber() {

        String val = phone.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            phone.setError("Enter valid phone number");
            return false;
        } else if (val.length() != 10) {
            phone.setError("Enter a valid phone number");
            return false;
        } else if (!val.matches(checkSpaces)) {
            phone.setError("No White spaces are allowed!");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }

    public void onLoginClickThree(View view) {
        startActivity(new Intent(this, SignupAdminSecondScreen.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}