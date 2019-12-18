package com.example.mobihealthapis.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class DiagnosticTests {


    @SerializedName("data")
    private List<Data> data;
    @SerializedName("total_records")
    private int total_records;
    @SerializedName("status")
    private boolean status;

    public static class Data {
        @SerializedName("test_name")
        private String test_name;
        @SerializedName("test_id")
        private int test_id;

        public String getTest_name() {
            return test_name;
        }

        public void setTest_name(String test_name) {
            this.test_name = test_name;
        }

        public int getTest_id() {
            return test_id;
        }

        public void setTest_id(int test_id) {
            this.test_id = test_id;
        }
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
