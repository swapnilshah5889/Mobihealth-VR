package com.example.mobihealthapis.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Med {


    @SerializedName("data")
    private List<Data> data;
    @SerializedName("total_records")
    private int total_records;
    @SerializedName("message")
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @SerializedName("med_json")
        private Med_json med_json;
        @SerializedName("medicine_company")
        private String medicine_company;
        @SerializedName("medicine_type")
        private String medicine_type;
        @SerializedName("medicine_generic_name")
        private String medicine_generic_name;
        @SerializedName("medicine_name")
        private String medicine_name;
        @SerializedName("Id")
        private int Id;

        public Med_json getMed_json() {
            return med_json;
        }

        public void setMed_json(Med_json med_json) {
            this.med_json = med_json;
        }

        public String getMedicine_company() {
            return medicine_company;
        }

        public void setMedicine_company(String medicine_company) {
            this.medicine_company = medicine_company;
        }

        public String getMedicine_type() {
            return medicine_type;
        }

        public void setMedicine_type(String medicine_type) {
            this.medicine_type = medicine_type;
        }

        public String getMedicine_generic_name() {
            return medicine_generic_name;
        }

        public void setMedicine_generic_name(String medicine_generic_name) {
            this.medicine_generic_name = medicine_generic_name;
        }

        public String getMedicine_name() {
            return medicine_name;
        }

        public void setMedicine_name(String medicine_name) {
            this.medicine_name = medicine_name;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }

    public static class Med_json {
        @SerializedName("hashtag_details")
        private Hashtag_details hashtag_details;
        @SerializedName("intensive_med_details")
        private boolean intensive_med_details;
        @SerializedName("extensive_med_details")
        private Extensive_med_details extensive_med_details;
        @SerializedName("is_it_extensive")
        private boolean is_it_extensive;
        @SerializedName("common_ins_details")
        private Common_ins_details common_ins_details;
        @SerializedName("common_med_details")
        private Common_med_details common_med_details;
        @SerializedName("uid")
        private int uid;

        public Hashtag_details getHashtag_details() {
            return hashtag_details;
        }

        public void setHashtag_details(Hashtag_details hashtag_details) {
            this.hashtag_details = hashtag_details;
        }

        public boolean getIntensive_med_details() {
            return intensive_med_details;
        }

        public void setIntensive_med_details(boolean intensive_med_details) {
            this.intensive_med_details = intensive_med_details;
        }

        public Extensive_med_details getExtensive_med_details() {
            return extensive_med_details;
        }

        public void setExtensive_med_details(Extensive_med_details extensive_med_details) {
            this.extensive_med_details = extensive_med_details;
        }

        public boolean getIs_it_extensive() {
            return is_it_extensive;
        }

        public void setIs_it_extensive(boolean is_it_extensive) {
            this.is_it_extensive = is_it_extensive;
        }

        public Common_ins_details getCommon_ins_details() {
            return common_ins_details;
        }

        public void setCommon_ins_details(Common_ins_details common_ins_details) {
            this.common_ins_details = common_ins_details;
        }

        public Common_med_details getCommon_med_details() {
            return common_med_details;
        }

        public void setCommon_med_details(Common_med_details common_med_details) {
            this.common_med_details = common_med_details;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }

    public static class Hashtag_details {
        @SerializedName("gu")
        private String gu;
        @SerializedName("hi")
        private String hi;
        @SerializedName("en")
        private String en;

        public String getGu() {
            return gu;
        }

        public void setGu(String gu) {
            this.gu = gu;
        }

        public String getHi() {
            return hi;
        }

        public void setHi(String hi) {
            this.hi = hi;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }

    public static class Extensive_med_details {
        @SerializedName("medicine")
        private List<Medicine> medicine;
        @SerializedName("common")
        private Common common;

        public List<Medicine> getMedicine() {
            return medicine;
        }

        public void setMedicine(List<Medicine> medicine) {
            this.medicine = medicine;
        }

        public Common getCommon() {
            return common;
        }

        public void setCommon(Common common) {
            this.common = common;
        }
    }

    public static class Medicine {
        @SerializedName("_freq_count")
        private int _freq_count;
        @SerializedName("_duration")
        private int _duration;
        @SerializedName("_ins_gu")
        private String _ins_gu;
        @SerializedName("_ins_hi")
        private String _ins_hi;
        @SerializedName("_ins_en")
        private String _ins_en;
        @SerializedName("_quant")
        private int _quant;
        @SerializedName("_timing_name")
        private String _timing_name;
        @SerializedName("_checkbox")
        private boolean _checkbox;

        public int get_freq_count() {
            return _freq_count;
        }

        public void set_freq_count(int _freq_count) {
            this._freq_count = _freq_count;
        }

        public int get_duration() {
            return _duration;
        }

        public void set_duration(int _duration) {
            this._duration = _duration;
        }

        public String get_ins_gu() {
            return _ins_gu;
        }

        public void set_ins_gu(String _ins_gu) {
            this._ins_gu = _ins_gu;
        }

        public String get_ins_hi() {
            return _ins_hi;
        }

        public void set_ins_hi(String _ins_hi) {
            this._ins_hi = _ins_hi;
        }

        public String get_ins_en() {
            return _ins_en;
        }

        public void set_ins_en(String _ins_en) {
            this._ins_en = _ins_en;
        }

        public int get_quant() {
            return _quant;
        }

        public void set_quant(int _quant) {
            this._quant = _quant;
        }

        public String get_timing_name() {
            return _timing_name;
        }

        public void set_timing_name(String _timing_name) {
            this._timing_name = _timing_name;
        }

        public boolean get_checkbox() {
            return _checkbox;
        }

        public void set_checkbox(boolean _checkbox) {
            this._checkbox = _checkbox;
        }
    }

    public static class Common {
        @SerializedName("duration_type")
        private String duration_type;
        @SerializedName("duration_digit")
        private int duration_digit;
        @SerializedName("food_time_check")
        private String food_time_check;
        @SerializedName("common_quant")
        private double common_quant;

        public String getDuration_type() {
            return duration_type;
        }

        public void setDuration_type(String duration_type) {
            this.duration_type = duration_type;
        }

        public int getDuration_digit() {
            return duration_digit;
        }

        public void setDuration_digit(int duration_digit) {
            this.duration_digit = duration_digit;
        }

        public String getFood_time_check() {
            return food_time_check;
        }

        public void setFood_time_check(String food_time_check) {
            this.food_time_check = food_time_check;
        }

        public double getCommon_quant() {
            return common_quant;
        }

        public void setCommon_quant(double common_quant) {
            this.common_quant = common_quant;
        }
    }

    public static class Common_ins_details {
        @SerializedName("gu")
        private String gu;
        @SerializedName("hi")
        private String hi;
        @SerializedName("en")
        private String en;

        public String getGu() {
            return gu;
        }

        public void setGu(String gu) {
            this.gu = gu;
        }

        public String getHi() {
            return hi;
        }

        public void setHi(String hi) {
            this.hi = hi;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }

    public static class Common_med_details {
        @SerializedName("new_med_type")
        private String new_med_type;
        @SerializedName("new_med_gen_name")
        private String new_med_gen_name;
        @SerializedName("new_med_name")
        private String new_med_name;

        public String getNew_med_type() {
            return new_med_type;
        }

        public void setNew_med_type(String new_med_type) {
            this.new_med_type = new_med_type;
        }

        public String getNew_med_gen_name() {
            return new_med_gen_name;
        }

        public void setNew_med_gen_name(String new_med_gen_name) {
            this.new_med_gen_name = new_med_gen_name;
        }

        public String getNew_med_name() {
            return new_med_name;
        }

        public void setNew_med_name(String new_med_name) {
            this.new_med_name = new_med_name;
        }
    }
}
