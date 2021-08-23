package com.ideas.assignment;

import java.util.List;

public class Item {
    private final Integer quantity;
    private final String name;
    private final Double price;
    private final String category;
    private final boolean isImported;

    public Item(Integer quantity, String name, Double price, String category, boolean isImported) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.category = category;
        this.isImported = isImported;
    }

    public Double getTotalPrice() {
        if(checkIfItemOrdered()) {
            return this.quantity * this.price;
        }
        return 0d;
    }

    private boolean checkIfItemOrdered() {
        return this.quantity > 0;
    }

    private Double getTaxToApply(List<String> categoriesWithNoTax, Double importDutyTaxAmount, Double basicTaxAmount) {
        Double taxAmount = this.isImported ? importDutyTaxAmount : 0d;
        if(!categoriesWithNoTax.contains(this.category)) {
            taxAmount = taxAmount + basicTaxAmount;
        }
        return taxAmount;
    }

    public String getReceipt(Double totalPrice) {
        return this.quantity + " " + this.name + ": " + totalPrice;
    }

    public Double getTax(List<String> categoriesWithNoTax, Double totalPrice, Double basicTaxAmount, Double importDutyTaxAmount) {
        if(checkIfItemOrdered()) {
            Double taxAmount = getTaxToApply(categoriesWithNoTax, importDutyTaxAmount, basicTaxAmount);
            if(taxAmount > 0d) {
                return (taxAmount/100) * totalPrice;
            }
        }
        return 0d;
    }
}