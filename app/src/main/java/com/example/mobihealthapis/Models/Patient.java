package com.example.mobihealthapis.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Patient {


    @SerializedName("data")
    private List<Data> data;
    @SerializedName("total_records")
    private int total_records;
    @SerializedName("status")
    private boolean status;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @SerializedName("UserImage")
        private String UserImage;
        @SerializedName("LastAppointment")
        private String LastAppointment;
        @SerializedName("NextAppointment")
        private String NextAppointment;
        @SerializedName("ContactNumber")
        private String ContactNumber;
        @SerializedName("Lname")
        private String Lname;
        @SerializedName("Fname")
        private String Fname;
        @SerializedName("PatientId")
        private int PatientId;
        @SerializedName("IssuesList")
        private List<Issues.Data> IssuesList;
        @SerializedName("DiagnosisList")
        private List<Diagnosis.Data> DiagnosisList;




        public String getUserImage() {
            return UserImage;
        }

        public void setUserImage(String UserImage) {
            this.UserImage = UserImage;
        }

        public String getLastAppointment() {
            return LastAppointment;
        }

        public void setLastAppointment(String LastAppointment) {
            this.LastAppointment = LastAppointment;
        }

        public String getNextAppointment() {
            return NextAppointment;
        }

        public void setNextAppointment(String NextAppointment) {
            this.NextAppointment = NextAppointment;
        }

        public String getContactNumber() {
            return ContactNumber;
        }

        public void setContactNumber(String ContactNumber) {
            this.ContactNumber = ContactNumber;
        }

        public String getLname() {
            return Lname;
        }

        public void setLname(String Lname) {
            this.Lname = Lname;
        }

        public String getFname() {
            return Fname;
        }

        public void setFname(String Fname) {
            this.Fname = Fname;
        }

        public int getPatientId() {
            return PatientId;
        }

        public void setPatientId(int PatientId) {
            this.PatientId = PatientId;
        }
    }
}
