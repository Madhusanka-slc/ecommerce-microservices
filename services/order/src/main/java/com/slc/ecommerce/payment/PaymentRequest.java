package com.slc.ecommerce.payment;

import com.slc.ecommerce.customer.CustomerResponse;
import com.slc.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}