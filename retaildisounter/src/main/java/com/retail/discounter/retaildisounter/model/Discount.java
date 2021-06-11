package com.retail.discounter.retaildisounter.model;

public class Discount {
    
private Double discount;
private Double totalBill;
private DiscountType type;

public DiscountType getType() {
    return type;
}

public Discount setType(DiscountType type) {
    this.type = type;
    return this;
}

public double getDiscount() {
    return discount;
}

public Discount setDiscount(double discount) {
    this.discount = discount;
    return this;
}

public double getTotalBill() {
    return totalBill;
}

public Discount setTotalBill(double totalBill) {
    this.totalBill = totalBill;
    return this;
}
}
