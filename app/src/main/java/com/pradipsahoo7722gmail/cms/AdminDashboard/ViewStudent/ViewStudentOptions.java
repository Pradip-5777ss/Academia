package com.pradipsahoo7722gmail.cms.AdminDashboard.ViewStudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewStudentOptions extends AppCompatActivity {

    TextView course, stream, year, semester;
    Spinner courseSpinner, streamSpinner, yearSpinner, semesterSpinner;
    Button showBtn;

    String selectedCourse, selectedStream, selectedYear, selectedSemester;

    public static String select_course, select_stream, select_Year, select_semester;

    String[] courseList = new String[]{"Select Course", "B.TECH", "Pharmacy", "Hons", "Management", "M.TECH", "Law"};
    String[] streamList = new String[]{"Select Stream", "CSE", "ECE", "EE", "ME", "CE", "L.L.B", "M.A L.L.B"};
    String[] yearList = new String[]{"Select Year", "First", "Second", "Third", "Fourth"};
    String[] semesterList = new String[]{"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_options);

        course = findViewById(R.id.viewStudent_course_text);
        stream = findViewById(R.id.viewStudent_stream_text);
        year = findViewById(R.id.viewStudent_year_text);
        semester = findViewById(R.id.viewStudent_semester_text);
        courseSpinner = findViewById(R.id.viewStudent_course_spinner);
        streamSpinner = findViewById(R.id.viewStudent_stream_spinner);
        yearSpinner = findViewById(R.id.viewStudent_year_spinner);
        semesterSpinner = findViewById(R.id.viewStudent_semester_spinner);
        showBtn = findViewById(R.id.showStudent);

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
    }

    public void showStudent(View view) {
        select_course = courseSpinner.getSelectedItem().toString();
        select_stream = streamSpinner.getSelectedItem().toString();
        select_Year = yearSpinner.getSelectedItem().toString();
        select_semester = semesterSpinner.getSelectedItem().toString();


        Intent intent = new Intent(getApplicationContext(), ViewStudent.class);
        intent.putExtra("Course", select_course);
        intent.putExtra("Stream", select_stream);
        intent.putExtra("Year", select_Year);
        intent.putExtra("Semester", select_semester);

        startActivity(intent);
        finish();
    }
}