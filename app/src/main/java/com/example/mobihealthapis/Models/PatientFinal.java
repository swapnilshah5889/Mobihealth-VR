package com.example.mobihealthapis.Models;

import java.util.ArrayList;
import java.util.List;

public class PatientFinal {

    Patient.Data PatientDetails;

    Vitals.Data Vitals;
    List<Issues.Data> Final_Symptoms;
    List<Diagnosis.Data> Final_Diagnosis;
    List<DiagnosticTests.Data> Final_DiagnosticTests;
    List<Advice> Final_Advice;
    List<Medicine> Final_Medicines;

    String f_date = "";
    int[] f_time;

    public PatientFinal(Patient.Data patientDetails) {
        PatientDetails = patientDetails;
        f_date = "";
        f_time = new int[]{-1, -1, -1};
        Vitals = null;
        Final_Symptoms = new ArrayList<>();
        Final_Diagnosis = new ArrayList<>();
        Final_DiagnosticTests = new ArrayList<>();
        Final_Advice = new ArrayList<>();
        Final_Medicines = new ArrayList<>();
    }

    public Patient.Data getPatientDetails() {
        return PatientDetails;
    }

    public void setPatientDetails(Patient.Data patientDetails) {
        PatientDetails = patientDetails;
    }

    public com.example.mobihealthapis.Models.Vitals.Data getVitals() {
        return Vitals;
    }

    public void setVitals(com.example.mobihealthapis.Models.Vitals.Data vitals) {
        Vitals = vitals;
    }

    public List<Issues.Data> getFinal_Symptoms() {
        return Final_Symptoms;
    }

    public void setFinal_Symptoms(List<Issues.Data> final_Symptoms) {
        Final_Symptoms = final_Symptoms;
    }

    public List<Diagnosis.Data> getFinal_Diagnosis() {
        return Final_Diagnosis;
    }

    public void setFinal_Diagnosis(List<Diagnosis.Data> final_Diagnosis) {
        Final_Diagnosis = final_Diagnosis;
    }

    public List<DiagnosticTests.Data> getFinal_DiagnosticTests() {
        return Final_DiagnosticTests;
    }

    public void setFinal_DiagnosticTests(List<DiagnosticTests.Data> final_DiagnosticTests) {
        Final_DiagnosticTests = final_DiagnosticTests;
    }

    public List<Advice> getFinal_Advice() {
        return Final_Advice;
    }

    public void setFinal_Advice(List<Advice> final_Advice) {
        Final_Advice = final_Advice;
    }

    public List<Medicine> getFinal_Medicines() {
        return Final_Medicines;
    }

    public void setFinal_Medicines(List<Medicine> final_Medicines) {
        Final_Medicines = final_Medicines;
    }

    public String getF_date() {
        return f_date;
    }

    public void setF_date(String f_date) {
        this.f_date = f_date;
    }

    public int[] getF_time() {
        return f_time;
    }

    public void setF_time(int[] f_time) {
        this.f_time = f_time;
    }
}
