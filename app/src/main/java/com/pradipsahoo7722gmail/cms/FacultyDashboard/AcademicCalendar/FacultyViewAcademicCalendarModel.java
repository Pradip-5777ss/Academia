package com.pradipsahoo7722gmail.cms.FacultyDashboard.AcademicCalendar;

public class FacultyViewAcademicCalendarModel {

    String File_Name, File_Url;

    public FacultyViewAcademicCalendarModel() {
    }

    public FacultyViewAcademicCalendarModel(String file_Name, String file_Url) {
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
