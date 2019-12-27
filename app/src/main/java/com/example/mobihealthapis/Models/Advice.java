package com.example.mobihealthapis.Models;

public class Advice {

    String id,advice_data;
    int counter;

    public Advice(String id, String advice_data, int counter) {
        this.id = id;
        this.advice_data = advice_data;
        this.counter = counter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvice_data() {
        return advice_data;
    }

    public void setAdvice_data(String advice_data) {
        this.advice_data = advice_data;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
