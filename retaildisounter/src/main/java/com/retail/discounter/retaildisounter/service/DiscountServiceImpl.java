package com.retail.discounter.retaildisounter.service;

import com.retail.discounter.retaildisounter.exception.IllegalPaymentInfoException;
import com.retail.discounter.retaildisounter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountMappingService mappingService;

    /**
     * Calculates a discount based on a BillingInfomation.
     *
     * Two types of discounts are calculated: percentage-based and voucher-based.
     * The percentage discount depends on the UserType and whether the paid items contains groceries.
     * The voucher discount reduce the total bill by $5 for every $100.
     *
     * @throws IllegalPaymentInfoException when there are null values or negative amount
     */
    @Override
    public Discount calculateDiscount(BillingInfo billingInfo) {
        checkForIllegalValues(billingInfo);
        Discount discount = new Discount();
        DiscountType discountType = checkDiscountType(billingInfo);
        Double amount = billingInfo.getAmount();

        Double percentageDiscount = calculatePercentageDiscount(discountType, amount);
        Double voucherDiscount = calculateAdditionalDiscount(amount);

        discount.setDiscount(percentageDiscount + voucherDiscount)
                .setType(discountType)
                .setTotalBill(amount);
        return discount;
    }

    private DiscountType checkDiscountType(BillingInfo billingInfo) {
        if (billingInfo.isContainingGroceries()) {
            return DiscountType.NONE;
        }
        return mappingService.getDiscountByUserType(billingInfo.getUserInfo().getType());
    }

    private Double calculatePercentageDiscount(DiscountType discountType, Double amount) {
        return mappingService.getFunctionByDiscount(discountType).apply(amount);
    }

    private Double calculateAdditionalDiscount(Double amount) {
        Double vouchersCount = Math.floor(amount / 100);
        return vouchersCount * 5;
    }

    private void checkForIllegalValues(BillingInfo billingInfo) {
        if (billingInfo == null || billingInfo.getUserInfo() == null) {
            throw new IllegalPaymentInfoException("PaymentInfo is null or has null values");
        } else if (billingInfo.getAmount() == null || billingInfo.getAmount() < 0.0) {
            throw new IllegalPaymentInfoException("Payment amount is null or is negative number");
        }
    }
}
