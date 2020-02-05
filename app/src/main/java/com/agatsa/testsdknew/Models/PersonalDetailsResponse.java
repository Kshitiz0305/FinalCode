package com.agatsa.testsdknew.Models;

public class PersonalDetailsResponse {
    private int pk;

    private String first_name;

    private String middle_name;

    private String last_name;

    private String email;

    private String mobile;
    private String address;
    private String district;
    private String nationality;

    private String dob;
    private String gender;
    private boolean marital_status;


    public PersonalDetailsResponse(int pk, String first_name, String middle_name, String last_name, String email, String mobile, String address, String district, String nationality, String dob, String gender, boolean marital_status) {
        this.pk = pk;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.district = district;
        this.nationality = nationality;
        this.dob = dob;
        this.gender = gender;
        this.marital_status = marital_status;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isMarital_status() {
        return marital_status;
    }

    public void setMarital_status(boolean marital_status) {
        this.marital_status = marital_status;
    }
}
