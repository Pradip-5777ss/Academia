package com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.StudyMaterial;

public class UploadAcademicResourcesHelperClass {

    String File_Name, File_Url;

    public UploadAcademicResourcesHelperClass() {
    }

    public UploadAcademicResourcesHelperClass(String file_Name, String file_Url) {
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
