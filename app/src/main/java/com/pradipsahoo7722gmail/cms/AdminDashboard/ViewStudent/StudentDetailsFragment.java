package com.pradipsahoo7722gmail.cms.AdminDashboard.ViewStudent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pradipsahoo7722gmail.cms.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String first_Name, middle_Name, last_Name, phone_No, adhar_No, registration_No, roll_No,
            email_id, parents_Name, parents_Contact, student_Address, student_Password, dob,
            student_Gender, student_Caste, student_Country, student_Course, student_Stream,
            student_Year, student_Semester;

    public StudentDetailsFragment() {
        // Required empty public constructor
    }

    public StudentDetailsFragment(String first_Name, String middle_Name, String last_Name, String phone_No,
                                  String adhar_No, String registration_No, String roll_No, String email_id,
                                  String parents_Name, String parents_Contact, String student_Address,
                                  String student_Password, String dob, String student_Gender, String student_Caste,
                                  String student_Country, String student_Course, String student_Stream,
                                  String student_Year, String student_Semester) {
        this.first_Name = first_Name;
        this.middle_Name = middle_Name;
        this.last_Name = last_Name;
        this.phone_No = phone_No;
        this.adhar_No = adhar_No;
        this.registration_No = registration_No;
        this.roll_No = roll_No;
        this.email_id = email_id;
        this.parents_Name = parents_Name;
        this.parents_Contact = parents_Contact;
        this.student_Address = student_Address;
        this.student_Password = student_Password;
        this.dob = dob;
        this.student_Gender = student_Gender;
        this.student_Caste = student_Caste;
        this.student_Country = student_Country;
        this.student_Course = student_Course;
        this.student_Stream = student_Stream;
        this.student_Year = student_Year;
        this.student_Semester = student_Semester;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentDetailsFragment newInstance(String param1, String param2) {
        StudentDetailsFragment fragment = new StudentDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_details, container, false);

        TextView firstname = view.findViewById(R.id.viewStudent_Firstname);
        TextView middlename = view.findViewById(R.id.viewStudent_MiddleName);
        TextView lastname = view.findViewById(R.id.viewStudent_Lastname);
        TextView d_o_b = view.findViewById(R.id.viewStudent_Dob);
        TextView phone = view.findViewById(R.id.viewStudent_PhoneNo);
        TextView aadhar = view.findViewById(R.id.viewStudent_AadharNo);
        TextView gender = view.findViewById(R.id.viewStudent_Gender);
        TextView email = view.findViewById(R.id.viewStudent_Email);
        TextView address = view.findViewById(R.id.viewStudent_Address);
        TextView regNo = view.findViewById(R.id.viewStudent_RegNo);
        TextView roll = view.findViewById(R.id.viewStudent_Roll);
        TextView semester = view.findViewById(R.id.viewStudent_Semester);
        TextView year = view.findViewById(R.id.viewStudent_Year);
        TextView stream = view.findViewById(R.id.viewStudent_Stream);
        TextView parentsName = view.findViewById(R.id.viewStudent_ParentsName);
        TextView parentPh = view.findViewById(R.id.viewStudent_ParentsPh);
        TextView course = view.findViewById(R.id.viewStudent_Course);
        TextView caste = view.findViewById(R.id.viewStudent_Caste);
        TextView nationality = view.findViewById(R.id.viewStudent_Nationality);
        TextView password = view.findViewById(R.id.viewStudent_Password);

        firstname.setText(first_Name);
        middlename.setText(middle_Name);
        lastname.setText(last_Name);
        d_o_b.setText(dob);
        phone.setText(phone_No);
        aadhar.setText(adhar_No);
        gender.setText(student_Gender);
        email.setText(email_id);
        address.setText(student_Address);
        regNo.setText(registration_No);
        roll.setText(roll_No);
        semester.setText(student_Semester);
        year.setText(student_Year);
        stream.setText(student_Stream);
        parentsName.setText(parents_Name);
        parentPh.setText(parents_Contact);
        course.setText(student_Course);
        caste.setText(student_Caste);
        nationality.setText(student_Country);
        password.setText(student_Password);

        return view;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity)getContext();
        Objects.requireNonNull(activity).getSupportFragmentManager().beginTransaction().replace(R.id.studentWrapper,
                new StudentListFragment()).addToBackStack(null).commit();

    }
}