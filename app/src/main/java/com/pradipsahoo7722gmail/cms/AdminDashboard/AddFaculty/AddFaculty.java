package com.pradipsahoo7722gmail.cms.AdminDashboard.AddFaculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.hbb20.CountryCodePicker;
import com.pradipsahoo7722gmail.cms.AdminDashboard.AddStudent.AddStudent;
import com.pradipsahoo7722gmail.cms.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddFaculty extends AppCompatActivity {

    private TextInputLayout firstName, middleName, lastName, contact, aadharNo, facultyId, email, address, password, doj;
    Spinner facultyGenderSpinner, facultyDepartmentSpinner, facultyStreamSpinner, facultyStatusSpinner,
            facultyCasteSpinner, facultyCountrySpinner;
    private ImageButton datePicker;
    private EditText datePickerText;
    CountryCodePicker addFacultyCountryCodePicker;
    private CheckBox confirmCheck;
    private Button registerFacultyBtn;
    private Calendar cal;
    private int day;
    private int month;
    private int dyear;

    String selectedGender, selectedDepartment, selectedStream, selectedStatus, selectedCaste, selectedCountry;
    String first_Name, middle_Name, last_Name, phone_No, adhar_No, faculty_Id, email_id, faculty_Address,
            faculty_Password, dob, date_of_join, faculty_Gender, faculty_Department, faculty_Stream, faculty_Status,
            faculty_Caste, faculty_Nationality, userType;

    private final String[] genderList = new String[]{"Select Gender", "Male ", "Female", "Other"};
    private final String[] departmentList = new String[]{"Select Department", "Engineering ", "Pharmacy", "Law", "Hons.", "Management"};
    private final String[] streamList = new String[]{"Select Stream", "CSE", "ECE", "EE", "ME", "CE", "L.L.B", "M.A L.L.B"};
    private final String[] statusList = new String[]{"Select Status", "Permanent", "Part Time", "guest Lecturer"};
    private final String[] casteList = new String[]{"Select Caste", "General", "SC", "ST", "OBC", "Other"};
    private final String[] countryList = new String[]{"Select Country", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Austrian Empire", "Azerbaijan",
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        firstName = findViewById(R.id.addFaculty_FirstName);
        middleName = findViewById(R.id.addFaculty_MiddleName);
        lastName = findViewById(R.id.addFaculty_LastName);
        contact = findViewById(R.id.addFaculty_PhoneNum);
        aadharNo = findViewById(R.id.addFaculty_AadharNum);
        facultyId = findViewById(R.id.addFaculty_FacultyId);
        email = findViewById(R.id.addFaculty_EmailId);
        doj = findViewById(R.id.addFaculty_DOJ);
        address = findViewById(R.id.addFaculty_Address);
        password = findViewById(R.id.addFaculty_Password);
        facultyGenderSpinner = findViewById(R.id.faculty_Gender);
        facultyStatusSpinner = findViewById(R.id.addFacultyStatus_spinner);
        facultyDepartmentSpinner = findViewById(R.id.faculty_Department);
        facultyStreamSpinner = findViewById(R.id.faculty_Stream);
        facultyCasteSpinner = findViewById(R.id.addFacultyCaste_spinner);
        facultyCountrySpinner = findViewById(R.id.addFacultyNationality_spinner);
        datePicker = findViewById(R.id.addFacultyDateImageButton);
        datePickerText = findViewById(R.id.addFacultyDateEditText);
        addFacultyCountryCodePicker = findViewById(R.id.addFaculty_country_code_picker);
        confirmCheck = findViewById(R.id.confirm_Check);
        registerFacultyBtn = findViewById(R.id.addFaculty_Register_Btn);

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
        facultyGenderSpinner.setAdapter(spinnerGenderAdapter);

        //  item selector method for gender spinner
        facultyGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = (String) facultyGenderSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //  Department Spinner
        final List<String> department = new ArrayList<>(Arrays.asList(departmentList));

        final ArrayAdapter<String> spinnerDepartmentAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, department) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerDepartmentAdapter.setDropDownViewResource(R.layout.spinner_item);
        facultyDepartmentSpinner.setAdapter(spinnerDepartmentAdapter);

        facultyDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = (String) facultyDepartmentSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //  Stream Spinner
        final List<String> stream = new ArrayList<>(Arrays.asList(streamList));

        final ArrayAdapter<String> spinnerStreamAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, stream) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerStreamAdapter.setDropDownViewResource(R.layout.spinner_item);
        facultyStreamSpinner.setAdapter(spinnerStreamAdapter);

        facultyStreamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStream = (String) facultyStreamSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //  Status Spinner
        final List<String> status = new ArrayList<>(Arrays.asList(statusList));

        final ArrayAdapter<String> spinnerStatusAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, status) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerStatusAdapter.setDropDownViewResource(R.layout.spinner_item);
        facultyStatusSpinner.setAdapter(spinnerStatusAdapter);

        facultyStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = (String) facultyStatusSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //  Caste Spinner
        final List<String> caste = new ArrayList<>(Arrays.asList(casteList));

        final ArrayAdapter<String> spinnerCasteAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, caste) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerCasteAdapter.setDropDownViewResource(R.layout.spinner_item);
        facultyCasteSpinner.setAdapter(spinnerCasteAdapter);

        facultyCasteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCaste = (String) facultyCasteSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //  Nationality Spinner
        final List<String> nationality = new ArrayList<>(Arrays.asList(countryList));

        final ArrayAdapter<String> spinnerNationalityAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, nationality) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerNationalityAdapter.setDropDownViewResource(R.layout.spinner_item);
        facultyCountrySpinner.setAdapter(spinnerNationalityAdapter);

        facultyCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = (String) facultyCountrySpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        datePicker.setOnClickListener(view -> {
            //noinspection deprecation
            showDialog(0);
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, dateSetListener, dyear, month, day);
    }

    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker datePicker, int selectYear, int selectMonth, int selectDay) {
            datePickerText.setText(selectDay + " / " + (selectMonth + 1) + " / " + selectYear);
        }
    };

    public void facultyRegisterBtn(View view) {

        first_Name = firstName.getEditText().getText().toString();
        middle_Name = middleName.getEditText().getText().toString();
        last_Name = lastName.getEditText().getText().toString();
        phone_No = contact.getEditText().getText().toString();
        adhar_No = aadharNo.getEditText().getText().toString();
        faculty_Id = facultyId.getEditText().getText().toString();
        email_id = email.getEditText().getText().toString();
        faculty_Address = address.getEditText().getText().toString();
        faculty_Password = password.getEditText().getText().toString();
        date_of_join = doj.getEditText().getText().toString();
        dob = datePickerText.getText().toString();

        faculty_Gender = facultyGenderSpinner.getSelectedItem().toString();
        faculty_Department = facultyDepartmentSpinner.getSelectedItem().toString();
        faculty_Stream = facultyStreamSpinner.getSelectedItem().toString();
        faculty_Status = facultyStatusSpinner.getSelectedItem().toString();
        faculty_Caste = facultyCasteSpinner.getSelectedItem().toString();
        faculty_Nationality = facultyCountrySpinner.getSelectedItem().toString();

        if (!validateFirstName() | !validateLastName() | !validatePhoneNumber() | !validateAadhar()
                | !validateEmail() | !validateAddress() | !validatePassword()) {
            return;
        }

        if (!confirmCheck.isChecked()) {
            Toast.makeText(this, "Confirmation Error!!!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userType = "Faculty";
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser AdminId = FirebaseAuth.getInstance().getCurrentUser();
        String registerAdminID = AdminId.getUid();
        //right one
//        DatabaseReference root = database.getReference("Faculty");

        //testing one
        DatabaseReference root = database.getReference().child("AdminData");
        AddFacultyUserHelperClass addNewFaculty = new AddFacultyUserHelperClass(first_Name, middle_Name, last_Name, phone_No, adhar_No, faculty_Id, email_id, faculty_Address,
                faculty_Password, dob, date_of_join, faculty_Gender, faculty_Department, faculty_Stream, faculty_Status,
                faculty_Caste, faculty_Nationality, userType);

        //right one
//        mAuth.createUserWithEmailAndPassword(email_id, faculty_Password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            String uid = FirebaseAuth.getInstance().getUid();
//                            root.child(uid).setValue(addNewFaculty)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(AddFaculty.this, "Faculty Added successful", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(AddFaculty.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
        //end of right one
//        storeFacultyData();

        //testing one
        mAuth.createUserWithEmailAndPassword(email_id, faculty_Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = FirebaseAuth.getInstance().getUid();

                            root.child(registerAdminID).child("Faculty").child(Objects.requireNonNull(uid))
                                    .setValue(addNewFaculty)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AddFaculty.this, "successfully added", Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(AddFaculty.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

//    private void storeFacultyData() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference root = database.getReference("Faculty");
//
//        AddFacultyUserHelperClass addNewFaculty = new AddFacultyUserHelperClass(first_Name, middle_Name, last_Name, phone_No, adhar_No, faculty_Id, email_id, faculty_Address,
//                faculty_Password, dob, date_of_join, faculty_Gender, faculty_Department, faculty_Stream, faculty_Status,
//                faculty_Caste, faculty_Nationality, userType);
//
//        root.child(faculty_Id).setValue(addNewFaculty)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(AddFaculty.this, "Faculty added successful", Toast.LENGTH_SHORT).show();
//                        finish();
//                        startActivity(getIntent());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
//                        Toast.makeText(AddFaculty.this, "Error : " +e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }

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

        String val = contact.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,10}\\z";
        if (val.isEmpty()) {
            contact.setError("Enter valid phone number");
            return false;
        } else if (val.length() != 10) {
            contact.setError("Enter a valid phone number");
            return false;
        } else if (!val.matches(checkSpaces)) {
            contact.setError("No White spaces are allowed!");
            return false;
        } else {
            contact.setError(null);
            contact.setErrorEnabled(false);
            return true;
        }
    }

    // Aadhar number field validation
    private boolean validateAadhar() {
        String val = aadharNo.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,12}\\z";

        if (val.isEmpty()) {
            aadharNo.setError("Enter valid Aadhar number");
            return false;
        } else if (val.length() != 12) {
            aadharNo.setError("Enter a valid Aadhar number");
            return false;
        } else if (!val.matches(checkSpaces)) {
            aadharNo.setError("No White spaces are allowed!");
            return false;
        } else {
            aadharNo.setError(null);
            aadharNo.setErrorEnabled(false);
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