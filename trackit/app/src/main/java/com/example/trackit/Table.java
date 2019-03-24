package com.example.trackit;

public class Table {
    String timestamp, value;

    public Table() {
    }

    public Table(String timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Date : " + timestamp + "      " + "Value : " + value;
    }
}
