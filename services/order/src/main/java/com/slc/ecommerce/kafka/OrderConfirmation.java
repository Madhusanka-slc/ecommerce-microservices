package com.slc.ecommerce.kafka;

import com.slc.ecommerce.customer.CustomerResponse;
import com.slc.ecommerce.order.PaymentMethod;
import com.slc.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}