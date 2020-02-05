package com.agatsa.testsdknew.Models;

public class UserDetailResponse {
    private int pk;

    private String first_name;

    private String last_name;

    private String email;

    private String mobile;

    private boolean is_active;

    private boolean is_staff;

    private boolean is_superuser;

    public UserDetailResponse(int pk, String first_name, String last_name, String email, String mobile, boolean is_active, boolean is_staff, boolean is_superuser) {
        this.pk = pk;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile = mobile;
        this.is_active = is_active;
        this.is_staff = is_staff;
        this.is_superuser = is_superuser;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_staff() {
        return is_staff;
    }

    public void setIs_staff(boolean is_staff) {
        this.is_staff = is_staff;
    }

    public boolean isIs_superuser() {
        return is_superuser;
    }

    public void setIs_superuser(boolean is_superuser) {
        this.is_superuser = is_superuser;
    }
}
