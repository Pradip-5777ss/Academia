package com.pradipsahoo7722gmail.cms.StudentDashboard.Calendar;

public class StudentViewAcademicCalendarModel {

    String File_Name, File_Url;

    public StudentViewAcademicCalendarModel() {
    }

    public StudentViewAcademicCalendarModel(String file_Name, String file_Url) {
        File_Name = file_Name;
        File_Url = file_Url;
    }

    public String getFile_Name() {
        return File_Name;
    }

    public void setFile_Name(String file_Name) {
        File_Name = file_Name;
    }

    public String getFile_Url() {
        return File_Url;
    }

    public void setFile_Url(String file_Url) {
        File_Url = file_Url;
    }
}
