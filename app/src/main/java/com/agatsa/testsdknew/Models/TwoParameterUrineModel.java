package com.agatsa.testsdknew.Models;

public class TwoParameterUrineModel {
    String pt_no;
    String row_id;
    String micro;
    String creat;
    String averagecolortest;
    String photouri;

    public TwoParameterUrineModel() {
        this.pt_no ="";
        this.row_id = "";
        this.micro = "";
        this.creat = "";
        this.averagecolortest = "";
        this.photouri = "";
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

    public String getMicro() {
        return micro;
    }

    public void setMicro(String micro) {
        this.micro = micro;
    }

    public String getCreat() {
        return creat;
    }

    public void setCreat(String creat) {
        this.creat = creat;
    }

    public String getAveragecolortest() {
        return averagecolortest;
    }

    public void setAveragecolortest(String averagecolortest) {
        this.averagecolortest = averagecolortest;
    }

    public String getPhotouri() {
        return photouri;
    }

    public void setPhotouri(String photouri) {
        this.photouri = photouri;
    }
}
