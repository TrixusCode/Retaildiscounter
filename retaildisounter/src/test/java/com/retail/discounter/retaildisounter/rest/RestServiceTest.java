package com.retail.discounter.retaildisounter.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.retail.DiscountBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import com.retail.discounter.retaildisounter.model.*;
import com.retail.discounter.RetaildisounterApplication;

import com.retail.discounter.retaildisounter.service.*;
import com.retail.discounter.retaildisounter.controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.retail.discounter.retaildisounter.exception.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        RestService.class,
        DiscountService.class,
        DiscountMappingService.class,
        RetaildisounterApplication.class 
    })
public class RestServiceTest extends DiscountBase{

    @Autowired
    private RestService restService;

    @Test
    public void Should_Calculate_Discount_For_Employee_Without_Groceries() {
        withGroceries(false);
        withAmount(150.45);
        withUserType(UserType.EMPLOYEE);
        ResponseEntity<Discount> discountInfoResponse = restService.calculateDiscount(billingInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discount = discountInfoResponse.getBody();
        assertCalculated(150.45, 50.135, DiscountType.EMPLOYEE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_Affiliate_Without_Groceries() {
        withGroceries(false);
        withAmount(280.0);
        withUserType(UserType.AFFILIATE);
        ResponseEntity<Discount> discountInfoResponse = restService.calculateDiscount(billingInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discount = discountInfoResponse.getBody();
        assertCalculated(280.0, 38.0, DiscountType.AFFILIATE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_LoyalUser_Without_Groceries() {
        withGroceries(false);
        withAmount(380.0);
        withUserType(UserType.LOYAL);
        ResponseEntity<Discount> discountInfoResponse = restService.calculateDiscount(billingInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discount = discountInfoResponse.getBody();
        assertCalculated(380.0, 34.0, DiscountType.LOYALTY_DISCOUNT);
    }

    @Test
    public void Should_NotCalculate_Discount_For_RegularUser_With_Groceries() {
        withGroceries(true);
        withAmount(380.0);
        withUserType(UserType.REGULAR);
        ResponseEntity<Discount> discountInfoResponse = restService.calculateDiscount(billingInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discount = discountInfoResponse.getBody();
        assertCalculated(380.0, 15.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_Employee_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.EMPLOYEE);
        ResponseEntity<Discount> discountInfoResponse = restService.calculateDiscount(billingInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discount = discountInfoResponse.getBody();
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_Affiliate_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.AFFILIATE);
        ResponseEntity<Discount> discountInfoResponse = restService.calculateDiscount(billingInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discount = discountInfoResponse.getBody();
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_LoyalUser_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.LOYAL);
        ResponseEntity<Discount> discountInfoResponse = restService.calculateDiscount(billingInfo);
        assertEquals(200, discountInfoResponse.getStatusCodeValue());
        discount = discountInfoResponse.getBody();
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_Throw_Exception_When_PaymentInfo_Is_Null() throws Exception {
        Assertions.assertThrows(IllegalPaymentInfoException.class, () ->
        {
            billingInfo = null;
        restService.calculateDiscount(billingInfo);
        });
    
    }

    @Test
    public void Should_Throw_Exception_When_UserInfo_Is_Null() throws Exception {

        Assertions.assertThrows(IllegalPaymentInfoException.class, () ->
        {
            billingInfo.setUserInfo(null);
            restService.calculateDiscount(billingInfo);
        });
   
    }

    @Test
    public void Should_Throw_Exception_When_Amount_In_Negative() throws Exception{
        Assertions.assertThrows(IllegalPaymentInfoException.class, () -> {
            billingInfo.setAmount(-134.14);
        restService.calculateDiscount(billingInfo);
        });
        
        
    }

    @Test
    public void Should_Throw_Exception_When_Amount_In_Null() throws Exception {
        Assertions.assertThrows(IllegalPaymentInfoException.class, () -> {
            billingInfo.setAmount(null);
            restService.calculateDiscount(billingInfo);
        });
    }
    
}
