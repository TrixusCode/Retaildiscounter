package com.retail.discounter.retaildisounter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import com.retail.discounter.retaildisounter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.retail.DiscountBase;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        DiscountService.class,
        DiscountServiceImpl.class,
        DiscountMappingService.class,
        DiscountMappingServiceImpl.class
})

public class DiscountServiceTest extends DiscountBase {
    
    @Autowired
    private DiscountService discountService;

    @Test
    public void Should_Calculate_Discount_For_Employee_Without_Groceries() {
        withGroceries(false);
        withAmount(150.45);
        withUserType(UserType.EMPLOYEE);
        discount = discountService.calculateDiscount(billingInfo);
        assertCalculated(150.45, 50.135, DiscountType.EMPLOYEE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_Affiliate_Without_Groceries() {
        withGroceries(false);
        withAmount(280.0);
        withUserType(UserType.AFFILIATE);
        discount = discountService.calculateDiscount(billingInfo);
        assertCalculated(280.0, 38.0, DiscountType.AFFILIATE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_LoyalUser_Without_Groceries() {
        withGroceries(false);
        withAmount(380.0);
        withUserType(UserType.LOYAL);
        discount = discountService.calculateDiscount(billingInfo);
        assertCalculated(380.0, 34.0, DiscountType.LOYALTY_DISCOUNT);
    }

    @Test
    public void Should_NotCalculate_Discount_For_RegularUser_With_Groceries() {
        withGroceries(true);
        withAmount(380.0);
        withUserType(UserType.REGULAR);
        discount = discountService.calculateDiscount(billingInfo);
        assertCalculated(380.0, 15.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_Employee_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.EMPLOYEE);
        discount = discountService.calculateDiscount(billingInfo);
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_Affiliate_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.AFFILIATE);
        discount = discountService.calculateDiscount(billingInfo);
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_LoyalUser_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.LOYAL);
        discount = discountService.calculateDiscount(billingInfo);
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_Throw_Exception_When_PaymentInfo_Is_Null() throws Exception {
        Assertions.assertThrows(Exception.class, () -> {
            billingInfo = null;
            discountService.calculateDiscount(billingInfo);
        });
       
    }

    @Test
    public void Should_Throw_Exception_When_UserInfo_Is_Null() throws Exception {
        Assertions.assertThrows(Exception.class, () -> {
            billingInfo.setUserInfo(null);
            discountService.calculateDiscount(billingInfo);
        });
    }

    @Test
    public void Should_Throw_Exception_When_Amount_Is_Negative() throws Exception {

        Assertions.assertThrows(Exception.class, () -> {
            billingInfo.setAmount(-123.34);
            discountService.calculateDiscount(billingInfo);
        });
    }

    @Test
    public void Should_Throw_Exception_When_Amount_Is_Null() throws Exception{
        Assertions.assertThrows(Exception.class, () -> {
            billingInfo.setAmount(null);
            discountService.calculateDiscount(billingInfo);
        });
       
    }

}
