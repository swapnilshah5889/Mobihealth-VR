package com.example.mobihealthapis.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class DataMap {

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
        @SerializedName("map")
        private String map;
        @SerializedName("data")
        private String data;

        public String getMap() {
            return map;
        }

        public void setMap(String map) {
            this.map = map;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
