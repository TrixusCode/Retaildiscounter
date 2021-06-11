package com.retail.discounter.retaildisounter.service;
import com.retail.discounter.retaildisounter.model.Discount;
import com.retail.discounter.retaildisounter.model.BillingInfo;

public interface DiscountService {
    /**
     * Calculates a discount based on a BillingInfo.
     */
    Discount calculateDiscount(BillingInfo billingInfo);
}
