package com.example.styleomega;

public class User {
    String email,fName,lName,password,phone,address;

    public User() {
    }

    //Register constructor
    public User(String email, String fName, String lName, String password, String phone) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.password = password;
        this.phone = phone;
        this.address="Undefined";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
