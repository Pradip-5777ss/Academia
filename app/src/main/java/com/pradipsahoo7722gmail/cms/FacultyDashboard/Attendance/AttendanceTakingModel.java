package com.pradipsahoo7722gmail.cms.FacultyDashboard.Attendance;

public class AttendanceTakingModel {

    String File_Name, File_Url, Stream, subject, semester;

    public AttendanceTakingModel() {
    }

    public AttendanceTakingModel(String file_Name, String file_Url, String stream, String subject, String semester) {
        File_Name = file_Name;
        File_Url = file_Url;
        Stream = stream;
        this.subject = subject;
        this.semester = semester;
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

    public String getStream() {
        return Stream;
    }

    public void setStream(String stream) {
        Stream = stream;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
