package com.retail.discounter.retaildisounter.service;
import com.retail.discounter.retaildisounter.model.DiscountType;
import com.retail.discounter.retaildisounter.model.UserType;
import java.util.function.Function;


public interface DiscountMappingService {
    
    DiscountType getDiscountByUserType(UserType userType);
    Function<Double, Double> getFunctionByDiscount(DiscountType discountType);
}
