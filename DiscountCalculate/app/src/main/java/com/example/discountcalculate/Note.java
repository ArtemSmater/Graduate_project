package com.example.discountcalculate;

public class Note {
    private final String price;
    private final String discount;

    public Note(String price, String discount) {
        this.price = price;
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }
}
