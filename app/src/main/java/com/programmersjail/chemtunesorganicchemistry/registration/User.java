package com.programmersjail.chemtunesorganicchemistry.registration;

public class User {

    private int id;
    private String userName,phoneNumber,password,collageName,hscExamTime,accountType;

    public User(int id, String userName, String phoneNumber, String password,
                String collageName, String hscExamTime, String accountType) {
        this.id = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.collageName = collageName;
        this.hscExamTime = hscExamTime;
        this.accountType = accountType;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getCollageName() {
        return collageName;
    }

    public String getHscExamTime() {
        return hscExamTime;
    }

    public String getAccountType() {
        return accountType;
    }
}
