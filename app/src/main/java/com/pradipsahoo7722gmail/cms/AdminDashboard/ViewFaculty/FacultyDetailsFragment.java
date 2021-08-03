package com.pradipsahoo7722gmail.cms.AdminDashboard.ViewFaculty;

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
 * Use the {@link FacultyDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacultyDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String first_Name, middle_Name, last_Name, phone_No, adhar_No, faculty_Id, email_id,
            faculty_Address, faculty_Password, dob, date_of_join, faculty_Gender,
            faculty_Department, faculty_Stream, faculty_Status, faculty_Caste, faculty_Nationality;
    public FacultyDetailsFragment() {
        // Required empty public constructor
    }

    public FacultyDetailsFragment(String first_Name, String middle_Name, String last_Name, String phone_No,
                                  String adhar_No, String faculty_Id, String email_id, String faculty_Address,
                                  String faculty_Password, String dob, String date_of_join, String faculty_Gender,
                                  String faculty_Department, String faculty_Stream, String faculty_Status,
                                  String faculty_Caste, String faculty_Nationality) {

        this.first_Name = first_Name;
        this.middle_Name = middle_Name;
        this.last_Name = last_Name;
        this.phone_No = phone_No;
        this.adhar_No = adhar_No;
        this.faculty_Id = faculty_Id;
        this.email_id = email_id;
        this.faculty_Address = faculty_Address;
        this.faculty_Password = faculty_Password;
        this.dob = dob;
        this.date_of_join = date_of_join;
        this.faculty_Gender = faculty_Gender;
        this.faculty_Department = faculty_Department;
        this.faculty_Stream = faculty_Stream;
        this.faculty_Status = faculty_Status;
        this.faculty_Caste = faculty_Caste;
        this.faculty_Nationality = faculty_Nationality;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FacultyDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FacultyDetailsFragment newInstance(String param1, String param2) {
        FacultyDetailsFragment fragment = new FacultyDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_faculty_details, container, false);

        TextView firstname = view.findViewById(R.id.viewFaculty_Firstname);
        TextView middlename = view.findViewById(R.id.viewFaculty_MiddleName);
        TextView lastname = view.findViewById(R.id.viewFaculty_Lastname);
        TextView d_o_b = view.findViewById(R.id.viewFaculty_Dob);
        TextView phone = view.findViewById(R.id.viewFaculty_PhoneNo);
        TextView doj = view.findViewById(R.id.viewFaculty_Doj);
        TextView aadhar = view.findViewById(R.id.viewFaculty_AadharNo);
        TextView gender = view.findViewById(R.id.viewFaculty_Gender);
        TextView email = view.findViewById(R.id.viewFaculty_Email);
        TextView address = view.findViewById(R.id.viewFaculty_Address);
        TextView id = view.findViewById(R.id.viewFaculty_ID);
        TextView department = view.findViewById(R.id.viewFaculty_Department);
        TextView stream = view.findViewById(R.id.viewFaculty_Stream);
        TextView status = view.findViewById(R.id.viewFaculty_Status);
        TextView caste = view.findViewById(R.id.viewFaculty_Caste);
        TextView nationality = view.findViewById(R.id.viewFaculty_Nationality);
        TextView password = view.findViewById(R.id.viewFaculty_Password);

        firstname.setText(first_Name);
        middlename.setText(middle_Name);
        lastname.setText(last_Name);
        d_o_b.setText(dob);
        phone.setText(phone_No);
        doj.setText(date_of_join);
        aadhar.setText(adhar_No);
        gender.setText(faculty_Gender);
        email.setText(email_id);
        address.setText(faculty_Address);
        id.setText(faculty_Id);
        department.setText(faculty_Department);
        stream.setText(faculty_Stream);
        status.setText(faculty_Status);
        caste.setText(faculty_Caste);
        nationality.setText(faculty_Nationality);
        password.setText(faculty_Password);

        return view;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity)getContext();
        Objects.requireNonNull(activity).getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,
                new RecFragment()).addToBackStack(null).commit();
    }

}