package com.pradipsahoo7722gmail.cms.RegisterAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.pradipsahoo7722gmail.cms.LoginUser.LoginScreen;
import com.pradipsahoo7722gmail.cms.R;

public class SignupAdmin extends AppCompatActivity {

    /**
     * Define variables for the animation
     */
    private ImageView SignUpImage;
    private TextView SignUpText;
    private Button nextBtn;

    /**
     * Define variables for get the data from the TextFields
     */
    private TextInputLayout fullName, username, email, password;

    String full_Name, user_name, user_email, user_password;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin);

//        database = FirebaseDatabase.getInstance();

        /*
         *  for design the navigation bar
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                    WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        /*
         *   Hooks for animation
         */
        SignUpImage = findViewById(R.id.signUp_image);
        SignUpText = findViewById(R.id.signUp_title_text);
        nextBtn = findViewById(R.id.reg_next_btn);

        /*
         *  Hooks for getting data form the text fields
         */
        fullName = findViewById(R.id.regFullName);
        username = findViewById(R.id.regUserName);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
    }

    /**
     * @param view Next button OnClickListener method.
     *             First check the validation of all the TextInputFields. If all the inputs of the fields are valid
     *             then the next condition will be executed, Otherwise it returns the same condition until the field
     *             inputs are not valid.
     */
    public void callNextSecondSignUpScreen(View view) {

        /*
         *  Define String variables to get the data from the respective fields
         */
        full_Name = fullName.getEditText().getText().toString();
        user_name = username.getEditText().getText().toString();
        user_email = email.getEditText().getText().toString();
        user_password = password.getEditText().getText().toString();

        /*
         *  Check the validation of all the fields.
         */
        if (!validateFullName() | !validateUserName() | !validateEmail() | !validatePassword()) {
            return;
        }

        /*
         *  If validation succeeded then move to the next screen
         */
        Intent intent = new Intent(SignupAdmin.this, SignupAdminSecondScreen.class);

//        DatabaseReference root = database.getReference("Admin");
//        DatabaseReference dbRef = root.push();
//
//        String key = dbRef.getKey();
//        RegisterUserHelperClass addUser = new RegisterUserHelperClass(full_Name, user_name, user_email, user_password, key);
//
//        root.child(Objects.requireNonNull(key)).setValue(addUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
//                Toast.makeText(SignupAdmin.this, "Register Successfully", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull @NotNull Exception e) {
//                Toast.makeText(SignupAdmin.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


//        startActivity(intent);
//        uniqueKey = reference.push().getKey();
//        UserHelperClass addNewUser = new UserHelperClass(fullName, username, email, password, gender, date, phone, uniqueKey);
//
//        reference.child(uniqueKey).setValue(addNewUser);

//        /*
//         *  Pass all fields data to the next activity
//         */
        intent.putExtra("fullName", full_Name);
        intent.putExtra("username", user_name);
        intent.putExtra("email", user_email);
        intent.putExtra("password", user_password);

        //Add Transition
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(SignUpText, "transition_signUp_title_text");
        pairs[1] = new Pair<View, String>(SignUpImage, "transition_signUp_image");
        pairs[2] = new Pair<View, String>(nextBtn, "transition_next_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignupAdmin.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }


    /**
     * @return Validation method for Password TextField
     */
    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password format is not correct!");
            Toast.makeText(getApplicationContext(), "Password must contain 1 capital letter, 1 small letter, 1 special character & 1 number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (val.length() <= 8) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * @return Validation method for Email TextField
     */
    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * @return Validation method for UserName TextField
     */
    private boolean validateUserName() {
        String val = username.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large");
            return false;
        } else if (!val.matches(checkSpaces)) {
            username.setError("No white spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * @return Validation method for FullName TextField
     */
    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("Field cannot be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * If the user is already register then this method will come back to login page
     *
     * @param view
     */
    public void onLoginClick(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
        startActivity(intent);
        finish();
    }
}