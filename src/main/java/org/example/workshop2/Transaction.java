package org.example.workshop2;

public class Transaction {
    private double amount;
    private String type; // "deposit" o "withdrawal"
    private String date;

    public Transaction(double amount, String type, String date) {
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
