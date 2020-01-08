package com.agatsa.testsdknew.Models;

/**
 * Created by sanjiv on 1/12/19.
 */

public class PatientModel {
    int id = 0;
    String ptNo;
    String user_id;
    String ptName;
    String ptAddress;
    String ptContactNo;
    String ptEmail;
    String ptAge;
    String ptSex;
    String ptmaritalstatus;
    String ptnoofboys;
    String ptnoofgirls;
    String ptdrugallergies;
    String ptDob;
    String ptdiseases;
    String ptmedication;
    String ptmedicationmedicinename;
    String ptsmoking;
    String ptalcohol;

    // Constructor
    public PatientModel(){
        id = 0;
        ptNo = "";
        user_id = "";
        ptName ="";
        ptAddress = "";
        ptContactNo = "";
        ptEmail = "";
        ptAge = "";
        ptSex = "";
        ptmaritalstatus = "";
        ptDob ="";
        ptnoofboys = "";
        ptnoofgirls = "";
        ptdrugallergies = "";
        ptmedication = "";
        ptmedicationmedicinename = "";
        ptdiseases = "";
        ptsmoking = "";
        ptalcohol = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPtNo() {
        return ptNo;
    }

    public void setPtNo(String ptNo) {
        this.ptNo = ptNo;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public String getPtAddress() {
        return ptAddress;
    }

    public void setPtAddress(String ptAddress) {
        this.ptAddress = ptAddress;
    }

    public String getPtContactNo() {
        return ptContactNo;
    }

    public void setPtContactNo(String ptContactNo) {
        this.ptContactNo = ptContactNo;
    }

    public String getPtEmail() {
        return ptEmail;
    }

    public void setPtEmail(String ptEmail) {
        this.ptEmail = ptEmail;
    }

    public String getPtAge() {
        return ptAge;
    }

    public void setPtAge(String ptAge) {
        this.ptAge = ptAge;
    }



    public String getPtSex() {
        return ptSex;
    }

    public void setPtSex(String ptSex) {
        this.ptSex = ptSex;
    }

    public String getPtmaritalstatus() {
        return ptmaritalstatus;
    }

    public void setPtmaritalstatus(String ptmaritalstatus) {
        this.ptmaritalstatus = ptmaritalstatus;
    }

    public String getPtnoofboys() {
        return ptnoofboys;
    }

    public void setPtnoofboys(String ptnoofboys) {
        this.ptnoofboys = ptnoofboys;
    }

    public String getPtnoofgirls() {
        return ptnoofgirls;
    }

    public void setPtnoofgirls(String ptnoofgirls) {
        this.ptnoofgirls = ptnoofgirls;
    }

    public String getPtdrugallergies() {
        return ptdrugallergies;
    }

    public void setPtdrugallergies(String ptdrugallergies) {
        this.ptdrugallergies = ptdrugallergies;
    }

    public String getPtDob() {
        return ptDob;
    }

    public void setPtDob(String ptDob) {
        this.ptDob = ptDob;
    }

    public String getPtmedication() {
        return ptmedication;
    }

    public void setPtmedication(String ptmedication) {
        this.ptmedication = ptmedication;
    }

    public String getPtmedicationmedicinename() {
        return ptmedicationmedicinename;
    }

    public void setPtmedicationmedicinename(String ptmedicationmedicinename) {
        this.ptmedicationmedicinename = ptmedicationmedicinename;
    }

    public String getPtdiseases() {
        return ptdiseases;
    }

    public void setPtdiseases(String ptdiseases) {
        this.ptdiseases = ptdiseases;
    }

    public String getPtsmoking() {
        return ptsmoking;
    }

    public void setPtsmoking(String ptsmoking) {
        this.ptsmoking = ptsmoking;
    }

    public String getPtalcohol() {
        return ptalcohol;
    }

    public void setPtalcohol(String ptalcohol) {
        this.ptalcohol = ptalcohol;
    }
}
