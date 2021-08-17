package com.example.assignment3;

public class CountMonth {
    private String month;
    private String count;

    public CountMonth(){

    }

    public CountMonth( String count, String month) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
