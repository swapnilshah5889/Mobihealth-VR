package com.example.mobihealthapis.Models;

public class Medicine {


    String name , genericname, frequency,duration,afbf;
    double[] dailytimings;

    public Medicine(String name, String genericname, String frequency, String duration, String afbf, double[] dailytimings) {
        this.name = name;
        this.genericname = genericname;
        this.frequency = frequency;
        this.duration = duration;
        this.afbf = afbf;
        this.dailytimings = dailytimings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericname() {
        return genericname;
    }

    public void setGenericname(String genericname) {
        this.genericname = genericname;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAfbf() {
        return afbf;
    }

    public void setAfbf(String afbf) {
        this.afbf = afbf;
    }

    public double[] getDailytimings() {
        return dailytimings;
    }

    public void setDailytimings(double[] dailytimings) {
        this.dailytimings = dailytimings;
    }
}
