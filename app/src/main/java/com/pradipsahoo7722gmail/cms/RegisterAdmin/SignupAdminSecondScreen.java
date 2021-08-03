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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pradipsahoo7722gmail.cms.R;

import java.util.Calendar;

public class SignupAdminSecondScreen extends AppCompatActivity {

    /**
     * Define Variables for animation
     */
    ImageView SignUpImage, arrowBtn;
    TextView SignUpText;
    Button nextBtn;

    /**
     * Define Variables for get the data from the fields
     */
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin_second_screen);

        //  For define the style of the navigation bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        //  Hooks for animation
        arrowBtn = findViewById(R.id.arrow_btn1);
        SignUpImage = findViewById(R.id.signUp_image);
        SignUpText = findViewById(R.id.signUp_title_text);
        nextBtn = findViewById(R.id.reg_next_btn);

        //  Hooks for get the data from the field
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);
    }

    /**
     *
     * @param view
     * NEXT button OnClickListener method.
     *
     */
    public void callNextThirdSignUpScreen(View view) {

        /*
         *  Get all values passed from previous screen using Intent
         */
        String full_Name = getIntent().getStringExtra("fullName");
        String user_Name = getIntent().getStringExtra("username");
        String user_Email = getIntent().getStringExtra("email");
        String user_Password= getIntent().getStringExtra("password");

        /*
         *  Check the validation of the fields
         */
        if (!validateGender() | !validateAge()) {
            return;
        }

        /*
         *  Select gender from the radiobutton
         *  Make a String variable to store the gender of the user
         */
        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String user_gender = selectedGender.getText().toString();

        /*
         *  Select age from the calender
         */
        int day = datePicker.getDayOfMonth();
        int month = (datePicker.getMonth()+1);
        int year = datePicker.getYear();

        /*
         *  Make a String variable to store the age of the user
         */
        String user_age = day+"/"+month+"/"+year;

        /*
         *  Move to the next screen
         */
        Intent intent = new Intent(getApplicationContext(), SignupAdminThirdScreen.class);

        /*
         *  Pass all fields data to the next activity
         */
        intent.putExtra("fullName",full_Name);
        intent.putExtra("username",user_Name);
        intent.putExtra("email",user_Email);
        intent.putExtra("password",user_Password);
        intent.putExtra("gender",user_gender);
        intent.putExtra("age",user_age);

        //Add Transition
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(SignUpText, "transition_signUp_title_text");
        pairs[1] = new Pair<View, String>(SignUpImage, "transition_signUp_image");
        pairs[2] = new Pair<View, String>(nextBtn, "transition_next_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignupAdminSecondScreen.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    /**
     * @return Validation method for choose the gender
     */
    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return Validation method for choose the age
     */
    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;


        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void onLoginClickTwo(View view) {
        startActivity(new Intent(this, SignupAdmin.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}