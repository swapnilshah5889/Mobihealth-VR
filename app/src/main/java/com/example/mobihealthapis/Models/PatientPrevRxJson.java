package com.example.mobihealthapis.Models;

import com.google.gson.annotations.SerializedName;

public class PatientPrevRxJson {

    @SerializedName("data")
    private Data data;
    @SerializedName("total_records")
    private int total_records;
    @SerializedName("status")
    private boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
        @SerializedName("rx_datetime")
        private String rx_datetime;
        @SerializedName("rx_json")
        private String rx_json;
        @SerializedName("patient_type")
        private String patient_type;
        @SerializedName("patient_id")
        private String patient_id;
        @SerializedName("id")
        private int id;

        public String getRx_datetime() {
            return rx_datetime;
        }

        public void setRx_datetime(String rx_datetime) {
            this.rx_datetime = rx_datetime;
        }

        public String getRx_json() {
            return rx_json;
        }

        public void setRx_json(String rx_json) {
            this.rx_json = rx_json;
        }

        public String getPatient_type() {
            return patient_type;
        }

        public void setPatient_type(String patient_type) {
            this.patient_type = patient_type;
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
