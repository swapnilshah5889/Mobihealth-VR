package com.example.mobihealthapis.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class Issues {
    @SerializedName("data")
    private List<Data> data;
    @SerializedName("total_records")
    private int total_records;
    @SerializedName("status")
    private boolean status;

    public static class Data {
        @SerializedName("issues")
        private String issues;
        @SerializedName("id")
        private int id;

        public String getIssues() {
            return issues;
        }

        public void setIssues(String issues) {
            this.issues = issues;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
