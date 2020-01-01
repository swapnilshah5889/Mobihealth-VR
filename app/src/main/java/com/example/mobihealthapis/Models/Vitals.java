package com.example.mobihealthapis.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Vitals {


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
        @SerializedName("BMI")
        private float BMI;
        @SerializedName("Weight")
        private double Weight;
        @SerializedName("Height")
        private int Height;
        @SerializedName("patient_id")
        private String patient_id;
        @SerializedName("id")
        private int id;
        @SerializedName("Head")
        private double Head;
        @SerializedName("Temperature")
        private int Temperature;

        public double getHead() {
            return Head;
        }

        public void setHead(double head) {
            Head = head;
        }

        public int getTemperature() {
            return Temperature;
        }

        public void setTemperature(int temperature) {
            Temperature = temperature;
        }

        public float getBMI() {
            return BMI;
        }

        public void setBMI(float BMI) {
            this.BMI = BMI;
        }

        public double getWeight() {
            return Weight;
        }

        public void setWeight(double Weight) {
            this.Weight = Weight;
        }

        public int getHeight() {
            return Height;
        }

        public void setHeight(int Height) {
            this.Height = Height;
        }

        public String getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(String patient_id) {
            this.patient_id = patient_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
