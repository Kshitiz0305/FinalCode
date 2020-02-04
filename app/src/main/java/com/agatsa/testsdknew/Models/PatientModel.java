package com.agatsa.testsdknew.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sanjiv on 1/12/19.
 */

public class PatientModel implements Parcelable {
    String id ;
    String ptNo;
    String user_id;
    String ptName;
    String ptAddress;
    String ptCity;
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
    String mUuid;

public PatientModel(){


}


    public PatientModel(Parcel in) {
        id = in.readString();
        ptNo = in.readString();
        user_id = in.readString();
        ptName = in.readString();
        ptAddress = in.readString();
        ptCity=in.readString();
        ptContactNo = in.readString();
        ptEmail = in.readString();
        ptAge = in.readString();
        ptSex = in.readString();
        ptmaritalstatus = in.readString();
        ptnoofboys = in.readString();
        ptnoofgirls = in.readString();
        ptdrugallergies = in.readString();
        ptDob = in.readString();
        ptdiseases = in.readString();
        ptmedication = in.readString();
        ptmedicationmedicinename = in.readString();
        ptsmoking = in.readString();
        ptalcohol = in.readString();
        mUuid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ptNo);
        dest.writeString(user_id);
        dest.writeString(ptName);
        dest.writeString(ptAddress);
        dest.writeString(ptCity);
        dest.writeString(ptContactNo);
        dest.writeString(ptEmail);
        dest.writeString(ptAge);
        dest.writeString(ptSex);
        dest.writeString(ptmaritalstatus);
        dest.writeString(ptnoofboys);
        dest.writeString(ptnoofgirls);
        dest.writeString(ptdrugallergies);
        dest.writeString(ptDob);
        dest.writeString(ptdiseases);
        dest.writeString(ptmedication);
        dest.writeString(ptmedicationmedicinename);
        dest.writeString(ptsmoking);
        dest.writeString(ptalcohol);
        dest.writeString(mUuid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PatientModel> CREATOR = new Creator<PatientModel>() {
        @Override
        public PatientModel createFromParcel(Parcel in) {
            return new PatientModel(in);
        }

        @Override
        public PatientModel[] newArray(int size) {
            return new PatientModel[size];
        }
    };

    public String getPtCity() {
        return ptCity;
    }

    public void setPtCity(String ptCity) {
        this.ptCity = ptCity;
    }

    public void setmUuid(String mUuid) {
        this.mUuid = mUuid;
    }

    public String getmUuid() {
        return mUuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
