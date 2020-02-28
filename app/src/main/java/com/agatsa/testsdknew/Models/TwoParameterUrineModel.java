package com.agatsa.testsdknew.Models;

public class TwoParameterUrineModel {
    String pt_no;
    String row_id;
    String microcreat;
    String averagecolortest;
    String photouri;

    public TwoParameterUrineModel() {
        this.pt_no ="";
        this.row_id = "";
        this.microcreat = "";
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

    public String getMicrocreat() {
        return microcreat;
    }

    public void setMicrocreat(String microcreat) {
        this.microcreat = microcreat;
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
