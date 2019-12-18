package com.example.mobihealthapis.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Vitals {



    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("total_records")
    private int total_records;
    @Expose
    @SerializedName("status")
    private boolean status;

    public static class Data {
        @Expose
        @SerializedName("added_on")
        private String added_on;
        @Expose
        @SerializedName("hp_id")
        private String hp_id;
        @Expose
        @SerializedName("added_by")
        private String added_by;
        @Expose
        @SerializedName("vd_point")
        private String vd_point;
        @Expose
        @SerializedName("op_cond")
        private String op_cond;
        @Expose
        @SerializedName("result_json")
        private String result_json;
        @Expose
        @SerializedName("cond_json")
        private String cond_json;
        @Expose
        @SerializedName("measured_on")
        private String measured_on;
        @Expose
        @SerializedName("vd_id")
        private String vd_id;
        @Expose
        @SerializedName("vital_id")
        private String vital_id;
        @Expose
        @SerializedName("patient_id")
        private String patient_id;
        @Expose
        @SerializedName("id")
        private int id;

        public String getAdded_on() {
            return added_on;
        }

        public void setAdded_on(String added_on) {
            this.added_on = added_on;
        }

        public String getHp_id() {
            return hp_id;
        }

        public void setHp_id(String hp_id) {
            this.hp_id = hp_id;
        }

        public String getAdded_by() {
            return added_by;
        }

        public void setAdded_by(String added_by) {
            this.added_by = added_by;
        }

        public String getVd_point() {
            return vd_point;
        }

        public void setVd_point(String vd_point) {
            this.vd_point = vd_point;
        }

        public String getOp_cond() {
            return op_cond;
        }

        public void setOp_cond(String op_cond) {
            this.op_cond = op_cond;
        }

        public String getResult_json() {
            return result_json;
        }

        public void setResult_json(String result_json) {
            this.result_json = result_json;
        }

        public String getCond_json() {
            return cond_json;
        }

        public void setCond_json(String cond_json) {
            this.cond_json = cond_json;
        }

        public String getMeasured_on() {
            return measured_on;
        }

        public void setMeasured_on(String measured_on) {
            this.measured_on = measured_on;
        }

        public String getVd_id() {
            return vd_id;
        }

        public void setVd_id(String vd_id) {
            this.vd_id = vd_id;
        }

        public String getVital_id() {
            return vital_id;
        }

        public void setVital_id(String vital_id) {
            this.vital_id = vital_id;
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
