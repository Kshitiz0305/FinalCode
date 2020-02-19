package com.agatsa.testsdknew.Models;

public class UricAcidModel {

    String pt_no;
    String row_id;
    String acid_level;
    String average_color;
    String photo_uri;

    public UricAcidModel() {
        this.pt_no = "";
        this.row_id = "";
        this.acid_level = "";
        this.average_color = "";
        this.photo_uri= "";
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

    public String getAcid_level() {
        return acid_level;
    }

    public void setAcid_level(String acid_level) {
        this.acid_level = acid_level;
    }

    public String getAverage_color() {
        return average_color;
    }

    public void setAverage_color(String average_color) {
        this.average_color = average_color;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }
}
