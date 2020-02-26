package com.agatsa.testsdknew.Models;

public class GlucoseModel {
    String pt_no;
    String  row_id;
    String ptGlucose;
    String pttimetaken;
    String pttesttype;
    String ptlatestmealtime;
    String ptmealtype;
    String timeinmilliseconds;
    String addeddate;
    String updateddate;


    public GlucoseModel() {
        pt_no = "";
        row_id="";
        ptGlucose ="";
        pttimetaken ="";
        pttesttype ="";
        ptlatestmealtime ="";
        ptmealtype ="";
        timeinmilliseconds ="";
        addeddate ="";
        updateddate ="";


    }

    public String getPt_no() {
        return pt_no;
    }

    public void setPt_no(String pt_no) {
        this.pt_no = pt_no;
    }

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
    }

    public String getPtGlucose() {
        return ptGlucose;
    }

    public void setPtGlucose(String ptGlucose) {
        this.ptGlucose = ptGlucose;
    }

    public String getPttimetaken() {
        return pttimetaken;
    }

    public void setPttimetaken(String pttimetaken) {
        this.pttimetaken = pttimetaken;
    }

    public String getPttesttype() {
        return pttesttype;
    }

    public void setPttesttype(String pttesttype) {
        this.pttesttype = pttesttype;
    }



    public String getPtmealtype() {
        return ptmealtype;
    }

    public void setPtmealtype(String ptmealtype) {
        this.ptmealtype = ptmealtype;
    }

    public String getPtlatestmealtime() {
        return ptlatestmealtime;
    }

    public void setPtlatestmealtime(String ptlatestmealtime) {
        this.ptlatestmealtime = ptlatestmealtime;
    }

    public String getTimeinmilliseconds() {
        return timeinmilliseconds;
    }

    public void setTimeinmilliseconds(String timeinmilliseconds) {
        this.timeinmilliseconds = timeinmilliseconds;
    }

    public String getAddeddate() {
        return addeddate;
    }

    public void setAddeddate(String addeddate) {
        this.addeddate = addeddate;
    }

    public String getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(String updateddate) {
        this.updateddate = updateddate;
    }
}
