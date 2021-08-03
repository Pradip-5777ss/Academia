package com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultySyllabus;

public class FacultyViewSyllabusModel {

    String File_Name, File_Url, Stream;

    public FacultyViewSyllabusModel() {
    }

    public FacultyViewSyllabusModel(String file_Name, String file_Url, String stream) {
        File_Name = file_Name;
        File_Url = file_Url;
        Stream = stream;
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
}
