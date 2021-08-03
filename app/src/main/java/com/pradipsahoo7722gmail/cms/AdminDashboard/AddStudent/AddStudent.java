package com.pradipsahoo7722gmail.cms.AdminDashboard.AddStudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.database.annotations.NotNull;
import com.hbb20.CountryCodePicker;
import com.pradipsahoo7722gmail.cms.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddStudent extends AppCompatActivity {

    private TextInputLayout firstName, middleName, lastName, phoneNumber, adharNo, registrationNo, rollNo, email,
            parentsName, parentsContact, address, password;
    Spinner genderSpinner, casteSpinner, countrySpinner, courseSpinner, streamSpinner, yearSpinner, semesterSpinner;
    ImageButton calender;
    EditText calenderText;
    CountryCodePicker countryCodePicker, parents_countryCodePicker;
    CheckBox confirmCheck;
    Button registerBtn;
    private Calendar cal;
    private int day;
    private int month;
    private int dyear;

    String first_Name, middle_Name, last_Name, phone_No, adhar_No, registration_No, roll_No,
            email_id, parents_Name, parents_Contact, student_Address, student_Password, dob,
            student_Gender, student_Caste, student_Country, student_Course, student_Stream,
            student_Year, student_Semester, userType, profile_Image;

    String selectedGender, selectedCaste, selectedCountry, selectedCourse, selectedStream, selectedYear, selectedSemester;


    String[] genderList = new String[]{"Select Gender", "Male ", "Female", "Other"};
    String[] casteList = new String[]{"Select Caste", "General", "SC", "ST", "OBC", "Other"};
    String[] countryList = new String[]{"Select Country", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Austrian Empire", "Azerbaijan",
            "Baden*", "Bahamas, The", "Bahrain", "Bangladesh", "Barbados", "Bavaria*", "Belarus", "Belgium", "Belize", "Benin (Dahomey)", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei",
            "Brunswick and Lüneburg", "Bulgaria", "Burkina Faso (Upper Volta)", "Burma", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Cayman Islands, The", "Central African Republic", "Central American Federation*",
            "Chad", "Chile", "China", "Colombia", "Comoros", "Congo Free State, The", "Costa Rica", "Cote d’Ivoire (Ivory Coast)", "Croatia", "Cuba", "Cyprus", "Czechia", "Czechoslovakia", "Democratic Republic of the Congo",
            "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Duchy of Parma, The*", "East Germany (German Democratic Republic)*", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini",
            "Ethiopia", "Federal Government of Germany (1848-49)*", "Fiji", "Finland", "France", "Gabon", "Gambia, The", "Georgia", "Germany", "Ghana", "Grand Duchy of Tuscany, The*", "Greece", "Grenada", "Guatemala", "Guinea",
            "Guinea-Bissau", "Guyana", "Haiti", "Hanover*", "Hanseatic Republics*", "Hawaii*", "Hesse*", "Holy See", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy",
            "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kingdom of Serbia/Yugoslavia*", "Kiribati", "Korea", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Lew Chew (Loochoo)*", "Liberia",
            "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mecklenburg-Schwerin*", "Mecklenburg-Strelitz*",
            "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Namibia", "Nassau*", "Nauru", "Nepal", "Netherlands, The", "New Zealand", "Nicaragua", "Niger", "Nigeria",
            "North German Confederation*", "North German Union*", "North Macedonia", "Norway", "Oldenburg*", "Oman", "Orange Free State*", "Pakistan", "Palau", "Panama", "Papal States*", "Papua New Guinea", "Paraguay", "Peru",
            "Philippines", "Piedmont-Sardinia*", "Poland", "Portugal", "Qatar", "Republic of Genoa*", "Republic of Korea (South Korea)", "Republic of the Congo", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia",
            "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Schaumburg-Lippe*", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
            "Solomon Islands, The", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Texas*", "Thailand", "Timor-Leste", "Togo",
            "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Two Sicilies*", "Uganda", "Ukraine", "Union of Soviet Socialist Republics*", "United Arab Emirates, The", "United Kingdom, The", "Uruguay",
            "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Württemberg*", "Yemen", "Zambia", "Zimbabwe"};
    String[] courseList = new String[]{"Select Course", "B.TECH", "Pharmacy", "Hons", "Management", "M.TECH", "Law"};
    String[] streamList = new String[]{"Select Stream", "CSE", "ECE", "EE", "ME", "CE", "L.L.B", "L.L.M"};
    String[] yearList = new String[]{"Select Year", "First", "Second", "Third", "Fourth"};
    String[] semesterList = new String[]{"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Hooks
        firstName = findViewById(R.id.first_name);
        middleName = findViewById(R.id.middle_name);
        lastName = findViewById(R.id.last_name);
        phoneNumber = findViewById(R.id.phoneNo);
        adharNo = findViewById(R.id.aadharNo);
        registrationNo = findViewById(R.id.registration_No);
        rollNo = findViewById(R.id.roll_No);
        email = findViewById(R.id.email_address);
        parentsName = findViewById(R.id.parents_Name);
        parents_countryCodePicker = findViewById(R.id.parentcountry_code_picker);
        parentsContact = findViewById(R.id.parents_contact);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        genderSpinner = findViewById(R.id.student_Gender);
        casteSpinner = findViewById(R.id.addStudentcaste_spinner);
        countrySpinner = findViewById(R.id.addStudentnationality_spinner);
        courseSpinner = findViewById(R.id.course_spinner);
        streamSpinner = findViewById(R.id.stream_spinner);
        yearSpinner = findViewById(R.id.year_spinner);
        semesterSpinner = findViewById(R.id.semester_spinner);
        calender = findViewById(R.id.DateImageButton);
        calenderText = findViewById(R.id.DateEditText);
        countryCodePicker = findViewById(R.id.addStudentcountry_code_picker);
        confirmCheck = findViewById(R.id.check_confirm);
        registerBtn = findViewById(R.id.register_student);


        // Gender Spinner
        final List<String> gender = new ArrayList<>(Arrays.asList(genderList));

        // Initializing an ArrayAdapter for genderSpinner
        final ArrayAdapter<String> spinnerGenderAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, gender) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerGenderAdapter.setDropDownViewResource(R.layout.spinner_item);
        genderSpinner.setAdapter(spinnerGenderAdapter);

        //  item selector method for gender spinner
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = (String) genderSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //  Caste Spinner
        final List<String> caste = new ArrayList<>(Arrays.asList(casteList));

        // Initializing an ArrayAdapter for casteSpinner
        final ArrayAdapter<String> spinnerCasteAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, caste) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerCasteAdapter.setDropDownViewResource(R.layout.spinner_item);
        casteSpinner.setAdapter(spinnerCasteAdapter);

        // Item selector method for caste spinner
        casteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCaste = (String) casteSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // Country Spinner
        final List<String> country = new ArrayList<>(Arrays.asList(countryList));

        // Initializing an ArrayAdapter for countrySpinner
        final ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, country) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_item);
        countrySpinner.setAdapter(spinnerCountryAdapter);

        //Item selector method for country spinner
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = (String) countrySpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Course Spinner
        final List<String> course = new ArrayList<>(Arrays.asList(courseList));

        final ArrayAdapter<String> spinnerCourseAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, course) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerCourseAdapter.setDropDownViewResource(R.layout.spinner_item);
        courseSpinner.setAdapter(spinnerCourseAdapter);

        //Item selector method for course spinner
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCourse = (String) courseSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*
         *  Stream Spinner
         */
        final List<String> stream = new ArrayList<>(Arrays.asList(streamList));
        final ArrayAdapter<String> spinnerStreamAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, stream) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerStreamAdapter.setDropDownViewResource(R.layout.spinner_item);
        streamSpinner.setAdapter(spinnerStreamAdapter);

        //  Item selector method for stream spinner
        streamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStream = (String) streamSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*
         *  Year Spinner
         */
        final List<String> year = new ArrayList<>(Arrays.asList(yearList));
        final ArrayAdapter<String> spinnerYearAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, year) {

            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerYearAdapter.setDropDownViewResource(R.layout.spinner_item);
        yearSpinner.setAdapter(spinnerYearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = (String) yearSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*
         *  Semester Spinner
         */
        final List<String> semester = new ArrayList<>(Arrays.asList(semesterList));
        final ArrayAdapter<String> spinnerSemesterAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, semester) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerSemesterAdapter.setDropDownViewResource(R.layout.spinner_item);
        semesterSpinner.setAdapter(spinnerSemesterAdapter);

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSemester = (String) semesterSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        calender.setOnClickListener(arg0 -> {
            //noinspection deprecation
            showDialog(0);
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker datePicker, int selectYear, int selectMonth, int selectDay) {
            calenderText.setText(selectDay + " / " + (selectMonth + 1) + " / " + selectYear);
        }
    };

    public void registerStudent(View view) {
        first_Name = firstName.getEditText().getText().toString();
        middle_Name = middleName.getEditText().getText().toString();
        last_Name = lastName.getEditText().getText().toString();
        phone_No = phoneNumber.getEditText().getText().toString();
        adhar_No = adharNo.getEditText().getText().toString();
        registration_No = registrationNo.getEditText().getText().toString();
        roll_No = rollNo.getEditText().getText().toString();
        email_id = email.getEditText().getText().toString();
        parents_Name = parentsName.getEditText().getText().toString();
        parents_Contact = parentsContact.getEditText().getText().toString();
        student_Address = address.getEditText().getText().toString();
        student_Password = password.getEditText().getText().toString();
        dob = calenderText.getText().toString();

        student_Gender = genderSpinner.getSelectedItem().toString();
        student_Caste = casteSpinner.getSelectedItem().toString();
        student_Country = countrySpinner.getSelectedItem().toString();
        student_Course = courseSpinner.getSelectedItem().toString();
        student_Stream = streamSpinner.getSelectedItem().toString();
        student_Year = yearSpinner.getSelectedItem().toString();
        student_Semester = semesterSpinner.getSelectedItem().toString();


        if (!validateFirstName() | !validateLastName() | !validatePhoneNumber() | !validateAadhar()
                | !validateRegistrationNo() | !validateRollNo() | !validateEmail() | !validateParentsName()
                | !validateParentsPhoneNumber() | !validateAddress() | !validatePassword()) {
            return;
        }

        if (!confirmCheck.isChecked()) {
            Toast.makeText(this, "Confirmation Error!!!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userType = "Student";
        }


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser AdminId = FirebaseAuth.getInstance().getCurrentUser();
        String registerAdminId = AdminId.getUid();
        //right one
//        DatabaseReference root = database.getReference("Student");

        //testing puspose
        DatabaseReference root = database.getReference().child("AdminData");

        AddStudentUserHelperClass addNewStudent = new AddStudentUserHelperClass(first_Name, middle_Name, last_Name,
                phone_No, adhar_No, registration_No, roll_No, email_id, parents_Name, parents_Contact, student_Address,
                student_Password, dob, student_Gender, student_Caste, student_Country, student_Course, student_Stream,
                student_Year, student_Semester, userType, profile_Image);

        //right one
//        mAuth.createUserWithEmailAndPassword(email_id, student_Password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            String uid = FirebaseAuth.getInstance().getUid();
//                            root.child(Objects.requireNonNull(uid)).setValue(addNewStudent)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(AddStudent.this, "Student Added successful", Toast.LENGTH_SHORT).show();
//                                            finish();
//                                            startActivity(getIntent());
//                                        }
//                                    });
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
//                        Toast.makeText(AddStudent.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
        //end of right one

        //testing one
        mAuth.createUserWithEmailAndPassword(email_id, student_Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = FirebaseAuth.getInstance().getUid();
                            root.child(registerAdminId).child("Student").child(Objects.requireNonNull(uid))
                                    .setValue(addNewStudent)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AddStudent.this, "successfully added", Toast.LENGTH_SHORT).show();
                                            refresh();
//                                            finish();
//                                            startActivity(getIntent());
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(AddStudent.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//    })

    }

    private void refresh() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    // First name field validation
    private boolean validateFirstName() {
        String val = firstName.getEditText().getText().toString();
        if (val.isEmpty()) {
            firstName.setError("Field cannot be empty");
            return false;
        } else {
            firstName.setError(null);
            firstName.setErrorEnabled(false);
            return true;
        }
    }

    // Last name field validation
    private boolean validateLastName() {
        String val = lastName.getEditText().getText().toString();
        if (val.isEmpty()) {
            lastName.setError("Field cannot be empty");
            return false;
        } else {
            lastName.setError(null);
            lastName.setErrorEnabled(false);
            return true;
        }
    }

    // Phone number field validation
    private boolean validatePhoneNumber() {

        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,10}\\z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (val.length() != 10) {
            phoneNumber.setError("Enter a valid phone number");
            return false;
        } else if (!val.matches(checkSpaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    // Aadhar number field validation
    private boolean validateAadhar() {
        String val = adharNo.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,12}\\z";

        if (val.isEmpty()) {
            adharNo.setError("Enter valid Aadhar number");
            return false;
        } else if (val.length() != 12) {
            adharNo.setError("Enter a valid Aadhar number");
            return false;
        } else if (!val.matches(checkSpaces)) {
            adharNo.setError("No White spaces are allowed!");
            return false;
        } else {
            adharNo.setError(null);
            adharNo.setErrorEnabled(false);
            return true;
        }
    }

    // Registration number field validation
    private boolean validateRegistrationNo() {
        String val = registrationNo.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            registrationNo.setError("Enter a valid Registration number");
            return false;
        } else if (!val.matches(checkSpaces)) {
            registrationNo.setError("No White spaces are allowed!");
            return false;
        } else {
            registrationNo.setError(null);
            registrationNo.setErrorEnabled(false);
            return true;
        }
    }

    // Roll number field validation
    private boolean validateRollNo() {
        String val = rollNo.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            rollNo.setError("Enter a valid Registration number");
            return false;
        } else if (!val.matches(checkSpaces)) {
            rollNo.setError("No White spaces are allowed!");
            return false;
        } else {
            rollNo.setError(null);
            rollNo.setErrorEnabled(false);
            return true;
        }
    }

    // Email-id field validation
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

    // Parents name field validation
    private boolean validateParentsName() {
        String val = parentsName.getEditText().getText().toString();

        if (val.isEmpty()) {
            parentsName.setError("Field cannot be empty");
            return false;
        } else {
            parentsName.setError(null);
            parentsName.setErrorEnabled(false);
            return true;
        }
    }

    // Parents contact field validation
    private boolean validateParentsPhoneNumber() {

        String val = parentsContact.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,10}\\z";
        if (val.isEmpty()) {
            parentsContact.setError("Enter valid phone number");
            return false;
        } else if (val.length() != 10) {
            parentsContact.setError("Enter a valid phone number");
            return false;
        } else if (!val.matches(checkSpaces)) {
            parentsContact.setError("No White spaces are allowed!");
            return false;
        } else {
            parentsContact.setError(null);
            parentsContact.setErrorEnabled(false);
            return true;
        }
    }

    // Address field validation
    private boolean validateAddress() {
        String val = address.getEditText().getText().toString();

        if (val.isEmpty()) {
            address.setError("Field cannot be empty");
            return false;
        } else {
            address.setError(null);
            address.setErrorEnabled(false);
            return true;
        }
    }

    // Password field validation
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


}