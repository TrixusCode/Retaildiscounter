package com.retail;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import com.retail.discounter.retaildisounter.model.*;


public class DiscountBase {

    protected BillingInfo billingInfo;
    protected Discount discount;

    @BeforeEach
    public void beforeEach() {
        billingInfo = new BillingInfo();
    }

    protected DiscountBase withGroceries(boolean has) {
        billingInfo.setContainingGroceries(has);
        return this;
    }

    protected DiscountBase withUserType(UserType userType) {
        User<String> user = new User<>();
        user.setType(userType);
        billingInfo.setUserInfo(user);
        return this;
    }

    protected DiscountBase withAmount(Double amount) {
        billingInfo.setAmount(amount);
        return this;
    }

    protected void assertCalculated(double total, double discounts, DiscountType type) {
        assertEquals(total, discount.getTotalBill(), 0.001);
        assertEquals(type, discount.getType());
        assertEquals(discounts, discount.getDiscount(), 0.001);
    }
    
}
