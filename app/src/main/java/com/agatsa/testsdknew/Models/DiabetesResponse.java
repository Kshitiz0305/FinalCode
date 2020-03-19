package com.agatsa.testsdknew.Models;

public class DiabetesResponse {
    String patient_id;
    String diabities;
    String  created_on;
    String  modified_on;

    public DiabetesResponse(String patient_id, String diabities, String created_on, String modified_on) {
        this.patient_id = patient_id;
        this.diabities = diabities;
        this.created_on = created_on;
        this.modified_on = modified_on;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDiabities() {
        return diabities;
    }

    public void setDiabities(String diabities) {
        this.diabities = diabities;
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
