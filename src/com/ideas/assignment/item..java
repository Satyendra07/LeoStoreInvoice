package com.ideas.assignment;

import java.util.List;

public class Item {
    private final Integer quantity;
    private final String name;
    private final Double price;
    private final String category;
    private final boolean isImportedItem;

    public Item(Integer quantity, String name, Double price, String category, boolean isImportedItem) {
        this.quantity = quantity;
        this.name = name;
        this.isImportedItem = isImportedItem;
        this.price = price;
        this.category = category;
    }

    public Double getTotalPrice() {
        if(this.quantity > 0) {
            return this.quantity * this.price;
        }
        return 0d;
    }

    public String getReceipt(Double totalPrice) {
        return this.quantity + " " + this.name + ": " + totalPrice;
    }

    public Double getTax(List<String> categoriesWithNoTax, Double totalPrice, Double basicTaxAmount, Double importDutyTaxAmount) {
        if(this.quantity > 0) {
            Double taxAmount = getAppliedTax(categoriesWithNoTax, importDutyTaxAmount, basicTaxAmount);
            if(taxAmount > 0d) {
                return (taxAmount/100) * totalPrice;
            }
        }
        return 0d;
    }

    private Double getAppliedTax(List<String> categoriesWithNoTax, Double importDutyTaxAmount, Double basicTaxAmount) {
        Double taxAmount = this.isImportedItem ? importDutyTaxAmount : 0d;
        if(!categoriesWithNoTax.contains(this.category)) {
            taxAmount = taxAmount + basicTaxAmount;
        }
        return taxAmount;
    }
}