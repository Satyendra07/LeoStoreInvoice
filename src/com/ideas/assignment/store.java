package com.ideas.assignment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import static java.util.Objects.isNull;

public class Store {
    private Double basicTaxAmount = 0d;
    private Double importDutyTaxAmount = 0d;
    private final List<String> categoriesWithNoTax = new ArrayList<>();

    public void addBasicTaxAmountForStore(Double taxAmount) {
        this.basicTaxAmount = taxAmount;
    }

    public void addImportDutyTaxAmount(Double taxAmount) {
        this.importDutyTaxAmount = taxAmount;
    }

    public void addCategoriesWithNoTax(List<String> categoriesWithNoTax) {
        this.categoriesWithNoTax.addAll(categoriesWithNoTax);
    }

    private Double getRoundedValue(Double value) {
        return isNull(value) ? 0d : BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public String placeOrderAndGetReceipt(List<Item> itemList) {
        List<String> receipt = new LinkedList<>();
        List<Double> totalPrice = new ArrayList<>();
        List<Double> totalTax = new ArrayList<>();
        itemList.forEach(item -> {
            Double price = getRoundedValue(item.getTotalPrice());
            Double tax = getRoundedValue(item.getTax(this.categoriesWithNoTax, price, this.basicTaxAmount, this.importDutyTaxAmount));
            Double priceIncludingTax = price + tax;
            receipt.add(item.getReceipt(priceIncludingTax));
            totalPrice.add(priceIncludingTax);
            totalTax.add(tax);
        });
        receipt.add("Tax: " + getRoundedValue(totalTax.stream().mapToDouble(Double::doubleValue).sum()));
        receipt.add("Total: " + getRoundedValue(totalPrice.stream().mapToDouble(Double::doubleValue).sum()));
        return receipt.isEmpty() ? "No Products found" : String.join(",", receipt);
    }
}