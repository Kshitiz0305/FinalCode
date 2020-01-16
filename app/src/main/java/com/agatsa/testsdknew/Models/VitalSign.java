package com.agatsa.testsdknew.Models;

/**
 * Created by sanjiv on 1/12/19.
 */

public class VitalSign {
    String pt_no;
    String row_id;
    String weight;
    String height;
    String tempt;
    String pulse;
    String bps;
    String bpd;
    String sto2;
    String glucose;

    public VitalSign(){
        pt_no = "";
        row_id ="";
        weight ="";
        height ="";
        tempt ="";
        pulse ="";
        bps="";
        bpd ="";
        sto2 ="";
        glucose ="";
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTempt() {
        return tempt;
    }

    public void setTempt(String tempt) {
        this.tempt = tempt;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getBps() {
        return bps;
    }

    public void setBps(String bps) {
        this.bps = bps;
    }

    public String getBpd() {
        return bpd;
    }

    public void setBpd(String bpd) {
        this.bpd = bpd;
    }

    public String getSto2() {
        return sto2;
    }

    public void setSto2(String sto2) {
        this.sto2 = sto2;
    }

    public String getGlucose() {
        return glucose;
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }
}
