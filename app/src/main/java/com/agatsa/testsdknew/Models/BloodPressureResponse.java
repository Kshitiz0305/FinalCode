package com.agatsa.testsdknew.Models;

public class BloodPressureResponse {
    String patient_id;
    String systolic;
    String  diastolic;
    String  created_on;
    String  modified_on;

    public BloodPressureResponse(String patient_id, String systolic, String diastolic, String created_on, String modified_on) {
        this.patient_id = patient_id;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.created_on = created_on;
        this.modified_on = modified_on;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }
}
