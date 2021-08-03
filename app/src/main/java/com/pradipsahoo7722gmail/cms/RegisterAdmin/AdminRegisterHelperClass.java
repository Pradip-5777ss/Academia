package com.pradipsahoo7722gmail.cms.RegisterAdmin;

public class AdminRegisterHelperClass {

    String fullName, userName, email, password, gender, age, phoneNo, userType;

    public AdminRegisterHelperClass() {
    }

    public AdminRegisterHelperClass(String fullName, String userName, String email, String password,
                                    String gender, String age, String phoneNo, String userType) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.phoneNo = phoneNo;
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
